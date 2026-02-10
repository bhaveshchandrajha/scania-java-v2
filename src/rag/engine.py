from typing import List
from sentence_transformers import CrossEncoder

from rag.retrievers.models import ContextBundle, RetrievedDoc
from rag.retrievers.base import BaseRetriever


class HybridEngine:
    """
    Single-retriever + Cross-Encoder reranking engine.

    Flow:
    SemanticRetriever → Re-rank → ContextBundle
    """

    def __init__(
        self,
        retriever: BaseRetriever,
        reranker_model: str = "cross-encoder/ms-marco-MiniLM-L-6-v2",
        retrieve_k: int = 10,
        final_k: int = 5,
    ):
        self.retriever = retriever
        self.retrieve_k = retrieve_k
        self.final_k = final_k

        # Cross-Encoder reranker
        self.reranker = CrossEncoder(reranker_model)

    # ---------------------------------------------------------
    # PUBLIC API
    # ---------------------------------------------------------
    def retrieve(self, query: str) -> ContextBundle:
        if not query:
            raise ValueError("Query cannot be empty")

        # 1. RETRIEVE (Semantic only)
        docs = self.retriever.search(query, top_k=self.retrieve_k)

        if not docs:
            return ContextBundle(query=query, documents=[])

        # 2. RE-RANK
        ranked_docs = self._rerank(query, docs)

        # 3. STORE IN CONTEXT BUNDLE
        return ContextBundle(
            query=query,
            documents=ranked_docs[: self.final_k]
        )

    # ---------------------------------------------------------
    # INTERNALS
    # ---------------------------------------------------------
    def _rerank(self, query: str, docs: List[RetrievedDoc]) -> List[RetrievedDoc]:
        """
        Cross-encoder reranking with score normalization.
        """
        pairs = [[query, doc.content] for doc in docs]

        raw_scores = self.reranker.predict(pairs)
        normalized_scores = self._normalize_scores(raw_scores)

        for doc, score in zip(docs, normalized_scores):
            doc.score = float(score)

        return sorted(docs, key=lambda d: d.score, reverse=True)


    @staticmethod
    def _normalize_scores(scores) -> List[float]:
        """
        Min-max normalization → [0.0, 1.0]
        Works with NumPy arrays from CrossEncoder.
        """
        if scores is None or len(scores) == 0:
            return []

        # Ensure Python floats
        scores = scores.tolist() if hasattr(scores, "tolist") else list(scores)

        min_s = min(scores)
        max_s = max(scores)

        if min_s == max_s:
            return [1.0] * len(scores)

        return [(s - min_s) / (max_s - min_s) for s in scores]
