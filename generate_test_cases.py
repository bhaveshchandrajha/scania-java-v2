#!/usr/bin/env python3
"""
Generate JUnit 5 integration tests for a warranty claim Spring Boot application
based on the Warranty Claim Processing workflow specification.

USAGE:
  export ANTHROPIC_API_KEY=sk-ant-...
  python3 generate_test_cases.py warranty_demo
  python3 generate_test_cases.py warranty_demo --model claude-sonnet-4-5

Called automatically by migrate_to_pure_java.py after successful migration.
"""

import argparse
import json
import os
import re
import sys
from pathlib import Path
from textwrap import dedent

import anthropic

ROOT_DIR = Path(__file__).resolve().parent
WORKFLOW_SPEC = ROOT_DIR / "docs" / "WARRANTY_CLAIM_WORKFLOW.md"


def get_java_files_summary(app_dir: Path) -> str:
    """Collect a summary of generated Java files for the prompt."""
    java_root = app_dir / "src" / "main" / "java"
    if not java_root.exists():
        return "No src/main/java found."

    summaries = []
    for java_file in sorted(java_root.rglob("*.java")):
        rel = java_file.relative_to(java_root)
        try:
            content = java_file.read_text(encoding="utf-8", errors="replace")
            # Include first 80 lines for controllers/services; first 40 for others
            lines = content.splitlines()
            max_lines = 80 if "web" in str(rel) or "service" in str(rel) else 40
            preview = "\n".join(lines[:max_lines])
            if len(lines) > max_lines:
                preview += "\n// ... (truncated)"
            summaries.append(f"--- {rel} ---\n{preview}")
        except Exception as e:
            summaries.append(f"--- {rel} ---\n(Error reading: {e})")

    return "\n\n".join(summaries)


def build_test_prompt(app_dir: Path) -> str:
    """Build the prompt for test case generation."""
    workflow_spec = WORKFLOW_SPEC.read_text(encoding="utf-8") if WORKFLOW_SPEC.exists() else ""
    java_summary = get_java_files_summary(app_dir)

    return dedent(f"""
You are a Java expert. Generate JUnit 5 integration tests for a Spring Boot warranty claim application.

## Workflow Specification (MUST cover all phases)

{workflow_spec}

## Generated Application Code (summary)

{java_summary}

## Requirements

1. Use @SpringBootTest, @AutoConfigureMockMvc, @ActiveProfiles("test")
2. Use MockMvc for REST API testing
3. Cover all three phases:
   - Phase 1: Claim Creation - workorder validation, date rule (≤19 days), duplicate prevention
   - Phase 2: Failure Assignment - mandatory failure, max 9 failures, required fields
   - Phase 3: Transmission - at least one failure, WP_SC01, status 10 SENT
4. Use @Nested classes for organization: ClaimCreation, FailureAssignment, Transmission
5. Use @DisplayName for readable test names
6. Include negative tests: invalid workorder, date rule violation, duplicate claim, missing failure
7. Match the actual API endpoints and DTOs from the generated code
8. Use H2 in-memory for tests (application-test.properties if needed)

## Output Format

Return ONLY valid Java code. Use the same markers as migrate_to_pure_java.py:
- `// FILE: path/to/TestClass.java` before each file
- No markdown code fences
- Complete, compilable test classes

Generate integration tests that validate the full warranty claim lifecycle.
""").strip()


def parse_multi_file_output(text: str) -> list[tuple[str, str]]:
    """Parse LLM output into (relative_path, content) tuples."""
    files = []
    pattern = re.compile(r"//\s*FILE:\s*(.+?\.java)\s*", re.IGNORECASE)
    current_path = None
    current_content = []

    for line in text.splitlines():
        match = pattern.match(line.strip())
        if match:
            if current_path and current_content:
                content = "\n".join(current_content).strip()
                if content:
                    files.append((current_path, content))
            current_path = match.group(1).strip()
            current_content = []
        elif current_path:
            current_content.append(line)

    if current_path and current_content:
        content = "\n".join(current_content).strip()
        if content:
            files.append((current_path, content))

    return files


def write_test_files(app_dir: Path, files: list[tuple[str, str]]) -> list[str]:
    """Write test files to src/test/java. Returns list of written paths."""
    test_root = app_dir / "src" / "test" / "java"
    test_root.mkdir(parents=True, exist_ok=True)
    written = []

    for rel_path, content in files:
        # Normalize path separators
        rel_path = rel_path.replace("\\", "/")
        if not rel_path.endswith(".java"):
            rel_path += ".java"
        out_path = test_root / rel_path
        out_path.parent.mkdir(parents=True, exist_ok=True)
        out_path.write_text(content, encoding="utf-8")
        written.append(str(out_path.relative_to(app_dir)))

    return written


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Generate JUnit 5 integration tests for warranty claim based on workflow spec",
    )
    parser.add_argument(
        "app_dir",
        type=Path,
        help="Spring Boot application directory (e.g. warranty_demo).",
    )
    parser.add_argument(
        "--model",
        default="claude-sonnet-4-5",
        help="Anthropic model (default: claude-sonnet-4-5)",
    )
    parser.add_argument(
        "--skip",
        action="store_true",
        help="Skip generation (no-op, for testing)",
    )
    args = parser.parse_args()

    app_dir = args.app_dir.resolve()
    if not app_dir.is_dir():
        print(f"Error: App directory not found: {app_dir}", file=sys.stderr)
        return 1

    if args.skip:
        print("// Skipping test generation (--skip)", file=sys.stderr)
        return 0

    api_key = os.environ.get("ANTHROPIC_API_KEY")
    if not api_key:
        print("Error: ANTHROPIC_API_KEY not set in environment.", file=sys.stderr)
        return 1

    if not WORKFLOW_SPEC.exists():
        print(f"Warning: Workflow spec not found: {WORKFLOW_SPEC}", file=sys.stderr)

    print("// Generating test cases from workflow spec...", file=sys.stderr)
    prompt = build_test_prompt(app_dir)

    client = anthropic.Anthropic(api_key=api_key)
    try:
        response = client.messages.create(
            model=args.model,
            max_tokens=16000,
            temperature=0.2,
            messages=[{"role": "user", "content": prompt}],
            timeout=300.0,
        )
    except Exception as e:
        print(f"// Error calling Anthropic: {e}", file=sys.stderr)
        return 1

    text = ""
    for block in response.content:
        if getattr(block, "type", None) == "text":
            text += block.text

    files = parse_multi_file_output(text)
    if not files:
        print("// No test files found in LLM output. Raw output:", file=sys.stderr)
        print(text[:500], file=sys.stderr)
        return 1

    written = write_test_files(app_dir, files)
    print(f"// ✅ Generated {len(written)} test file(s):", file=sys.stderr)
    for p in written:
        print(f"//    {p}", file=sys.stderr)
    return 0


if __name__ == "__main__":
    sys.exit(main())
