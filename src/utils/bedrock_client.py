import os
from threading import Lock

from langchain_aws import ChatBedrock, BedrockEmbeddings
from botocore.config import Config
from dotenv import load_dotenv

from utils.config_loader import load_config
from utils.settings import INTERFACE_PROFILE_ARN


class BedrockClient:
    """
    Factory + Singleton manager for Bedrock LLMs and Embeddings.
    """

    _instance = None 
    _lock = Lock() # threadsafe singleton

    def __new__(cls, *args, **kwargs):
        if not cls._instance:
            with cls._lock:
                if not cls._instance:
                    cls._instance = super().__new__(cls)

        return cls._instance
    
    def __init__(self):
        if not hasattr(self, "_embedding_cache"):
            self._embedding_cache = {}
        if not hasattr(self, "_llm_cache"):
            self._llm_cache = {}
        # prevent re-initialization 
        if getattr(self, "_initialized", False):
            return
        
        load_dotenv()
        self.INTERFACE_PROFILE_ARN = INTERFACE_PROFILE_ARN
        self._validate_env()
        self.config = load_config()


    def _validate_env(self):
        """
        Validate necessary environment varible
        """
        required_vars = ['AWS_BEARER_TOKEN_BEDROCK']

        self.api_keys = {key: os.getenv(key) for key in required_vars}
        missing = [k for k, v in self.api_keys.items() if not v]

        if missing:
            raise EnvironmentError(f"Missing environment variables: {missing}")
        
        print("Environment variables validated")

    # ----------------------------------------------------
    # FACTORY METHODS
    # ----------------------------------------------------

    def get_embeddings(self, provider: str = "amazon"):
        """
        Factory method to get/cached embeddings.
        """
        if provider in self._embedding_cache:
            return self._embedding_cache[provider]
        
        try:
            print(f"Loading embedding model [{provider}]...")
            model_cfg = self.config['embedding_model'][provider]

            embeddings = BedrockEmbeddings(
                model_id=model_cfg['model_id'],
                region_name=model_cfg['region_name'] 
            )
        
            self._embedding_cache[provider] = embeddings
            return embeddings
    
        except Exception as e:
            raise RuntimeError("Error loading embedding model") from e

    def get_llm(self, provider: str = "anthropic"):
        """
        Factory method to get/cached Bedrock LLM.
        """
        if provider in self._llm_cache:
            return self._llm_cache[provider]

        try:
            print(f"Loading Bedrock LLM [{provider}]...")
            llm_cfg = self.config['llm'][provider]

            bedrock_client = Config(
                read_timeout = 300,
                connect_timeout = 60,
                retries = {
                    "max_attempts": 5,
                    "mode": "standard"
                }
            )         

            llm = ChatBedrock(
                model=self.INTERFACE_PROFILE_ARN,
                provider=llm_cfg['provider'],
                region=llm_cfg['region_name'],
                config=bedrock_client
            )  

            self._llm_cache[provider] = llm 
            return llm 
        
        except Exception as e:
            raise RuntimeError("Error loading bedrock client") from e





