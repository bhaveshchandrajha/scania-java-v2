from typing import List, Dict, Optional

from utils.pinecone_client import get_pinecone_client
from rag.retrievers.semantic import SemanticRetriever


class ContextRetriever:
    """
    Responsible only for retrieving relevant context 
    from Pinecone vector store.
    """

    def __init__(self):
        self.semantic_retriever = SemanticRetriever()

    def retrieve(
            self,
            query: str,
            top_k: int =3
    ) -> List[str]:
        """
        Retrieve relavent context from Pinecone.
        """

        if not query:
            raise ValueError("Query cannot be empty") 
        
        docs = self.semantic_retriever.search(query, top_k)

        return [doc.content for doc in docs]
    
    def retrieve_as_text(
            self,
            query: str,
            top_k: int = 3,
            separator: str = "\n\n"
    ) -> str:
        """
        Retrieve context and return as a single formatted string.
        """

        contents = self.retrieve(query, top_k)
        return separator.join(contents)
