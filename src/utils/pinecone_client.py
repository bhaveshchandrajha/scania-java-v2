import os 
from typing import Optional, List, Dict
from threading import Lock

from pinecone import Pinecone, ServerlessSpec
from langchain_pinecone import PineconeVectorStore

from utils.bedrock_client import BedrockClient
from utils.config_loader import load_config
from utils.settings import PINECONE_API_KEY


os.environ['PINECONE_API_KEY'] = PINECONE_API_KEY 


class PineconeClient:
    """
    Singleton Pinecone client with Bedrock embeddings.
    """

    _instance: Optional["PineconeClient"] = None 
    _lock = Lock()

    def __new__(cls):
        if not cls._instance:
            with cls._lock:
                if not cls._instance:
                    cls._instance = super().__new__(cls)
        return cls._instance 
    
    def __init__(self):
        if getattr(self, "_initialized", False):
            return 
        
        self.config = load_config() 

        # Pinecone region should be explicitly configured
        self.PINECONE_REGION = self.config['pinecone_db']['region']

        self.pc = Pinecone(api_key=PINECONE_API_KEY)
        print("Pinecone client initialized.")

        bedrock_client = BedrockClient()
        self.embeddings = bedrock_client.get_embeddings()

        print("Embedding model initialized.")

        # Cache index objects 
        self._index_cache = {}

        self._initialized = True 

    # --------------------------------------------
    # INDEX MANAGEMENT 
    # --------------------------------------------

    def ensure_index(self, index_name: str):
        existing = self.pc.list_indexes().names() 

        if index_name in existing:
            desc = self.pc.describe_index(index_name)
            self.embedding_dim = desc.dimension 
            print(f"Index exists. Using embedding dimension: {self.embedding_dim}")
            return 
        
        # Compute embedding dimension ONCE 
        test_vec = self.embeddings.embed_query("dimension test")
        self.embedding_dim = len(test_vec)

        print(f"Creating Pinecone index '{index_name}'...")

        self.pc.create_index(
            name=index_name,
            dimension=self.embedding_dim,
            metric="cosine",
            spec=ServerlessSpec(
                cloud="aws",
                region=self.PINECONE_REGION
            )
        )

        print(f"Index '{index_name}' created successfully.")

    def get_index(self, index_name: str):
        if index_name not in self._index_cache:
            self.ensure_index(index_name)
            self._index_cache[index_name] = self.pc.Index(index_name)
        return self._index_cache[index_name]
    
    # ----------------------------------------------------------
    # DATA OPERATIONS
    # ----------------------------------------------------------

    def store_chunks(
            self,
            chunks: List[str],
            index_name: str,
            metadata: Optional[List[Dict]] = None 
    ):
        if not chunks:
            print("No chunks provided. skipping storage.")
            return 
        
        try:
            self.ensure_index(index_name)
            print(f"Storing {len(chunks)} chunks into '{index_name}'...")

            PineconeVectorStore.from_texts(
                texts=chunks,
                index_name=index_name,
                embedding=self.embeddings,
                metadatas=metadata
            )

            print(f"Successfully stored {len(chunks)} chunks in '{index_name}'")

        except Exception as e:
            print(f"Error storing chunks in '{index_name}': {e}")
            raise 

    def retrieve_context(
            self,
            query: str,
            index_name: str,
            k: int =3,
    ) -> List[Dict]:
        try:
            self.ensure_index(index_name)

            vectorstore = PineconeVectorStore.from_existing_index(
                index_name=index_name,
                embedding=self.embeddings
            )

            print(f"Searching top {k} documents for query: {query}")

            docs = vectorstore.similarity_search(query, k=k)

            return [
                {"content": doc.page_content, "metadata": doc.metadata}
                for doc in docs
            ]
        except Exception as e:
            print(f"Error retrieving context from '{index_name}': {e}")
            raise 


def get_pinecone_client() -> PineconeClient:
    return PineconeClient()