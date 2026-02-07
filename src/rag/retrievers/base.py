from abc import ABC, abstractmethod
from typing import List
from pydantic import BaseModel 
import json
from rag.retrievers.models import RetrievedDoc


class BaseRetriever(ABC):
    """
    Abstract base class for all retrievers.
    """

    @abstractmethod
    def search(self, query: str, top_k: int = 5) -> List[RetrievedDoc]:
        pass