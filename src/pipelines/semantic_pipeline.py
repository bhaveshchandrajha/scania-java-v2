import os
from rag.retrievers.semantic import SemanticRetriever
from rag.engine import HybridEngine
from utils.config_loader import load_config


config = load_config()
DATA_DIR = config['data']['data_dir']
print(DATA_DIR)


def run_semantic_retrieval_pipeline(query: str):
    """
    Semantic-only retrieval pipeline:
    SemanticRetriever → Re-rank → ContextBundle
    """

    # Ensure Data Directory exists (kept for consistency/logging)
    if not os.path.exists(DATA_DIR):
        raise FileNotFoundError(f"Data directory not found at: {DATA_DIR}")

    print("--- Initializing Semantic Retrieval Engine ---")

    # 1. Initialize Semantic Retriever
    semantic = SemanticRetriever()

    # 2. Initialize HybridEngine (rerank only)
    engine = HybridEngine(
        retriever=semantic,
        retrieve_k=10,   # broad recall
        final_k=5        # tight context window
    )

    # 3. Query
    print(f"\n--- Processing Query: '{query}' ---\n")

    # 4. Retrieve + Rerank
    context_bundle = engine.retrieve(query)

    # 5. Save output (optional / debugging)
    output_filename = "output_context.json"
    with open(output_filename, "w", encoding="utf-8") as f:
        f.write(context_bundle.to_json())

    print(f"✅ Context bundle written to: {os.path.abspath(output_filename)}")

    # 6. Return bundle for downstream (LLM / API / UI)
    return context_bundle
