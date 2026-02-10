import os

from rag.retrievers.semantic import SemanticRetriever
from rag.engine import HybridEngine
from rag.code_generator import CodeGenerator


class CodeGenerationPipeline:

    def __init__(self):
        self.query = "Generate the warranty validation and claim history logic"

        print("--- Initializing Semantic Retrieval Engine ---")
        semantic_retriever = SemanticRetriever()
        self.generator = CodeGenerator()

        self.engine = HybridEngine(
        retriever=semantic_retriever,
        retrieve_k=10,
        final_k=5
        )

    def run(self):
        bundle = self.engine.retrieve(self.query)
        java_code = self.generator.generate_java(self.query, bundle)

        os.makedirs("artifacts", exist_ok=True)
        with open("artifacts/semantic_output.java", "w") as f:
            f.write(java_code)

        print("✅ Java code generated successfully")
