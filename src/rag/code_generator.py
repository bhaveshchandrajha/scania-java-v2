import json
from rag.retrievers.models import ContextBundle
from utils.bedrock_client import BedrockClient
from prompt.prompt_library import SAMPLE_CODE_GENERATION_SYSTEM_PROMPT
from utils.config_loader import load_config

config = load_config()
MAX_TOKEN_LIMIT = config["llm"]["anthropic"]["max_output_tokens"]


class CodeGenerator:
    def __init__(self):
        self.bedrock_client = BedrockClient()
        self.llm = self.bedrock_client.get_llm() 

    # -------------------------------------------------------------
    # SEMANTIC CONTEXT FORMATTER
    # -------------------------------------------------------------
    def _format_context_for_llm(self, bundle: ContextBundle) -> str:
        semantic_blocks = []

        docs = sorted(bundle.documents, key=lambda d: d.score, reverse=True)

        for doc in docs:
            block = [f"### LOGIC BLOCK (ID: {doc.id})"]
            block.append(f"**Relevance:** {doc.score:.2f}")
            block.append(f"**Summary:** {doc.content}")

            ast = doc.metadata.get("text", [])
            if isinstance(ast, str):
                try:
                    ast = json.loads(ast)
                except Exception:
                    ast = []

            if isinstance(ast, list) and ast:
                block.append("**Execution Flow:**")
                for step in ast[:15]:
                    meta = step.get("metadata", {})
                    block.append(
                        f"- [{meta.get('type', 'OP')}] {meta.get('summary', 'Execute')}"
                    )

            semantic_blocks.append("\n".join(block))

        return (
            "=== CORE BUSINESS LOGIC (Semantic Retrieval) ===\n\n"
            + "\n\n".join(semantic_blocks)
        )

    def generate_java(self, query: str, bundle: ContextBundle) -> str:
        enriched_context = self._format_context_for_llm(bundle)

        user_prompt = f"""
    🚨 MIGRATION REQUEST

    User Query:
    {query}

    ━━━━━━━━━━━━━━━
    📌 Semantic Retrieval Context
    {enriched_context}

    ━━━━━━━━━━━━━━━
    🛠️ FINAL INSTRUCTIONS
    Generate a **production-grade Spring Boot Service class**.

    Rules:
    - Preserve business logic
    - Convert RPG-style flow to clean Java methods
    - Use dependency injection
    - Ensure compilable Java code
    """

        final_prompt = f"""
    <system>
    {SAMPLE_CODE_GENERATION_SYSTEM_PROMPT}
    </system>

    <user>
    {user_prompt}
    </user>
    """

        try:
            response = self.llm.invoke(final_prompt, max_tokens=MAX_TOKEN_LIMIT)

            # ✅ SAFE NORMALIZATION (IMPORTANT)
            if isinstance(response, str):
                return response
            if hasattr(response, "content"):
                return response.content
            return str(response)

        except Exception as e:
            return f"// Generation failed: {str(e)}"
