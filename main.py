from rag.retrievers.semantic import SemanticRetriever
from rag.engine import HybridEngine
from rag.code_generator import CodeGenerator
import os


def main():
    query = "Generate the warranty validation and claim history logic"

    print("--- Initializing Semantic Retrieval Engine ---")

    semantic_retriever = SemanticRetriever()

    engine = HybridEngine(
        retriever=semantic_retriever,
        retrieve_k=10,
        final_k=5
    )

    bundle = engine.retrieve(query)

    generator = CodeGenerator()
    java_code = generator.generate_java(query, bundle)

    os.makedirs("artifacts", exist_ok=True)
    with open("artifacts/semantic_output.java", "w") as f:
        f.write(java_code)

    print("✅ Java code generated successfully")


if __name__ == "__main__":
    main()
