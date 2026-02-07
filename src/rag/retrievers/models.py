from pydantic import BaseModel, Field 
from typing import Dict, Any, List 
import json 


class RetrievedDoc(BaseModel):
    id: str
    content: str
    source: str
    score: float = 0.0
    rank: int = 0
    metadata: Dict[str, Any] = Field(default_factory=dict)


class ContextBundle(BaseModel):
    query: str 
    documents: List[RetrievedDoc]

    def to_json(self) -> str:
        """
        Formats the bundle as a clean JSON string
        """
        return json.dumps(self.model_dump(), indent=2, default=str)