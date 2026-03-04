#!/usr/bin/env python3
"""
Fix Java compilation errors in an existing application by sending the compiler
error log and current file contents to the LLM and applying the corrected code.

USAGE:
  export ANTHROPIC_API_KEY=sk-ant-...
  python3 fix_compile_errors.py <project_dir> <build_log_file> [--model claude-sonnet-4-5]

This is intended to be called automatically from the global-context UI server
after a failed Maven build.
"""

import json
import os
import re
import sys
from pathlib import Path
from typing import Dict, List, Tuple, Set


ROOT = Path(__file__).resolve().parent
if str(ROOT) not in sys.path:
    sys.path.insert(0, str(ROOT))

from fix_logic_gaps import parse_fixed_files  # reuse robust parser for LLM responses


def extract_error_blocks(build_log: str) -> str:
    """
    Extract the most relevant error section(s) from a Maven compiler log.
    Keeps lines between 'COMPILATION ERROR' and the next blank block,
    plus any trailing '[ERROR] ...' lines.
    """
    lines = build_log.splitlines()
    in_block = False
    collected: List[str] = []
    for line in lines:
        if "COMPILATION ERROR" in line:
            in_block = True
            collected.append(line)
            continue
        if in_block:
            collected.append(line)
    # If we didn't detect the marker, fall back to truncating the whole log
    if not collected:
        collected = lines
    # Limit to a reasonable number of characters for the prompt
    text = "\n".join(collected)
    return text[-20000:]


def extract_error_files(build_log: str, project_dir: Path) -> List[Path]:
    """
    From the Maven build log, extract absolute or relative .java file paths
    referenced in error messages and resolve them under project_dir.
    """
    files: List[Path] = []
    seen: Set[Path] = set()
    # Patterns like:
    # [ERROR] /path/to/File.java:[line,col] ...
    # or possibly without the [ERROR] prefix once truncated
    pattern = re.compile(r"(?:\[\s*ERROR\s*\]\s*)?(?P<path>/[^\s:]+\.java)")
    for line in build_log.splitlines():
        m = pattern.search(line)
        if not m:
            continue
        raw_path = m.group("path").strip()
        p = Path(raw_path)
        if not p.is_absolute():
            p = project_dir / p
        try:
            p_resolved = p.resolve()
            # Ensure it's under the project dir
            p_resolved.relative_to(project_dir.resolve())
        except Exception:
            continue
        if p_resolved in seen:
            continue
        seen.add(p_resolved)
        files.append(p_resolved)
    return files


def extract_related_type_files(build_log: str, project_dir: Path) -> List[Path]:
    """
    From error lines like:
      location: variable claimRepository of type com.scania.warranty.repository.ClaimRepository
      location: class com.scania.warranty.service.ClaimCreationService
    infer related types and resolve their source files under src/main/java.
    """
    type_pattern = re.compile(
        r"location:\s+(?:variable|class)\s+[A-Za-z0-9_]+\s+of\s+type\s+([A-Za-z0-9_.]+)"
    )
    class_pattern = re.compile(
        r"location:\s+class\s+([A-Za-z0-9_.]+)"
    )
    fqns: Set[str] = set()
    for line in build_log.splitlines():
        m1 = type_pattern.search(line)
        if m1:
            fqns.add(m1.group(1).strip())
        m2 = class_pattern.search(line)
        if m2:
            fqns.add(m2.group(1).strip())

    src_root = project_dir / "src" / "main" / "java"
    paths: List[Path] = []
    seen_paths: Set[Path] = set()
    for fqn in fqns:
        rel = Path(*fqn.split("."))  # com.scania.warranty.X -> com/scania/warranty/X
        candidate = src_root / f"{rel}.java"
        if candidate.exists():
            p_resolved = candidate.resolve()
            if p_resolved not in seen_paths:
                seen_paths.add(p_resolved)
                paths.append(p_resolved)
    return paths


def build_fix_prompt(error_snippet: str, files_with_content: List[Tuple[str, str]]) -> str:
    """
    Build the prompt that asks the LLM to fix compilation errors only.
    """
    files_section = "\n\n".join(
        f"### === {rel} ===\n```java\n{content}\n```"
        for rel, content in files_with_content
    )
    return f"""You are fixing Java compilation errors in an existing Spring Boot application.

## Task

The Maven compiler plugin produced the following errors. Your job is to fix ONLY
the compilation issues (missing methods, type mismatches, incorrect filenames,
etc.) in the provided Java files. Do NOT refactor unrelated code or change
the overall architecture. Preserve existing semantics as much as possible.

## Compiler errors

```
{error_snippet}
```

## Current Java files to correct

These are the current versions of the files mentioned in the errors. Update
them to resolve the compilation problems.

{files_section}

## Important guidelines

- Prefer to add missing repository methods, getters/setters, or simple type
  conversions rather than changing service/controller logic flow.
- When adding Spring Data repository methods, infer the method signatures from
  the call sites and domain model (e.g. return types like Optional<...> or List<...>).
- For numeric vs BigDecimal mismatches, prefer using BigDecimal.valueOf(...)
  or adjusting field types consistently, choosing BigDecimal for monetary amounts.
- Do not introduce new external dependencies.

## Response format

Return the FULL corrected Java file(s) in the following format:

### === <path/relative/to/project/src/main/java/...>.java ===
```java
<full corrected content>
```

Return ALL files you changed, even if the change is small."""


def main() -> None:
    import argparse

    parser = argparse.ArgumentParser(description="Fix Java compilation errors using LLM")
    parser.add_argument("project_dir", type=Path, help="Project directory containing pom.xml (e.g. warranty_demo)")
    parser.add_argument("build_log_file", type=Path, help="Path to a text file with Maven build output")
    parser.add_argument("--model", default="claude-sonnet-4-5", help="Anthropic model")
    parser.add_argument("--max-tokens", type=int, default=32000)
    args = parser.parse_args()

    api_key = os.environ.get("ANTHROPIC_API_KEY")
    if not api_key:
        print("ANTHROPIC_API_KEY env var is not set", file=sys.stderr)
        sys.exit(1)

    project_dir = args.project_dir.resolve()
    if not project_dir.exists():
        print(f"Project dir not found: {project_dir}", file=sys.stderr)
        sys.exit(2)

    if not args.build_log_file.exists():
        print(f"Build log file not found: {args.build_log_file}", file=sys.stderr)
        sys.exit(3)

    build_log = args.build_log_file.read_text(encoding="utf-8", errors="ignore")
    error_snippet = extract_error_blocks(build_log)
    error_files = extract_error_files(build_log, project_dir)
    related_type_files = extract_related_type_files(build_log, project_dir)

    all_files_set: Set[Path] = set(error_files) | set(related_type_files)
    all_files: List[Path] = list(all_files_set)

    if not all_files:
        print("No error-related Java or related type files found in build log.", file=sys.stderr)
        print(json.dumps({"success": False, "error": "No Java files found in build log.", "files_fixed": []}))
        sys.exit(4)

    # Collect file contents relative to project_dir
    files_with_content: List[Tuple[str, str]] = []
    for path in all_files:
        try:
            rel = path.resolve().relative_to(project_dir.resolve())
            rel_str = str(rel).replace("\\", "/")
            content = path.read_text(encoding="utf-8", errors="ignore")
            files_with_content.append((rel_str, content))
        except Exception:
            continue

    if not files_with_content:
        print("Could not read any Java file contents for errors.", file=sys.stderr)
        print(json.dumps({"success": False, "error": "Could not read Java file contents.", "files_fixed": []}))
        sys.exit(5)

    prompt = build_fix_prompt(error_snippet, files_with_content)
    print(f"Calling LLM to fix {len(files_with_content)} file(s) based on compilation errors...", file=sys.stderr)

    import anthropic

    client = anthropic.Anthropic(api_key=api_key)
    response = client.messages.create(
        model=args.model,
        max_tokens=args.max_tokens,
        temperature=0.2,
        messages=[{"role": "user", "content": prompt}],
        timeout=600.0,
    )
    text = ""
    for block in response.content:
        if getattr(block, "type", None) == "text":
            text += block.text

    fixed = parse_fixed_files(text)
    if not fixed:
        print("LLM did not return any parsed files. Check response format.", file=sys.stderr)
        print(json.dumps({"success": False, "error": "LLM did not return any parsed files.", "files_fixed": []}))
        sys.exit(6)

    written: List[str] = []
    for rel_path, content in fixed.items():
        rel_path = rel_path.replace("\\", "/")
        target = project_dir / rel_path
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(content, encoding="utf-8")
        written.append(rel_path)
        print(f"Wrote {rel_path}", file=sys.stderr)

    out = {
        "success": True,
        "files_fixed": written,
        "message": f"Fixed {len(written)} file(s) based on compilation errors. Re-run Maven build to confirm.",
    }
    print(json.dumps(out))


if __name__ == "__main__":
    main()

