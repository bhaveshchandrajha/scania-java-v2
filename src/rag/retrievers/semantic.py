from typing import List
import json 

from rag.retrievers.base import BaseRetriever
from rag.retrievers.models import RetrievedDoc

from utils.config_loader import load_config
from utils.pinecone_client import get_pinecone_client
from utils.bedrock_client import BedrockClient


class SemanticRetriever(BaseRetriever):
    """
    Semantic retriever using Pinecone + Bedrock embeddings.
    """

    def __init__(self):
        config = load_config()
        self.index_name = config['pinecone_db']['index_name']

        # Pinecone client (Singleton)
        self.pc = get_pinecone_client() 

        # Lazily fetch index (ensure existence)
        self.index = self.pc.get_index(self.index_name)

        # Bedrock embeddings 
        bedrock_client = BedrockClient()
        self.embedding_model = bedrock_client.get_embeddings()

    def _get_embedding(self, text: str) -> List[float]:
        """
        Generate embedding using Amazon Bedrock.
        """
        embedding = self.embedding_model.embed_query(text)
        if not embedding:
            raise RuntimeError("Empty embedding returned from Bedrock")
        return embedding
    
    def search(self, query: str, top_k: int = 5) -> List[RetrievedDoc]:
        """
        Perform semantic vector search + metadata formatting.
        """
        try:
            query_vector = self._get_embedding(query)

            results = self.index.query(
                vector=query_vector,
                top_k=top_k,
                include_metadata=True
            )

            docs: List[RetrievedDoc] = []

            for match in results.matches:
                meta = match.metadata or {} 

                # Fix nested JSON text array stored as string
                if "text" in meta and isinstance(meta['text'], str):
                    try:
                        meta['text'] = json.loads(meta["text"])
                    except json.JSONDecodeError:
                        pass 

                node_type = meta.get("type", "Unknown")
                summary = meta.get("summary", "")

                docs.append(
                    RetrievedDoc(
                        id=match.id,
                        content=f"[{node_type}] {summary}",
                        source="semantic",
                        score=match.score or 0.0,
                        metadata=meta
                    )
                )

            return docs 
        
        except Exception as e:
            print(f"[ERROR] Semantic Search Failed: {e}")
            raise

