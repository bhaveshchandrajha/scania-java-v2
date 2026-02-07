import os 
from dotenv import load_dotenv

load_dotenv()

PINECONE_API_KEY = os.getenv('PINECONE_API_KEY')
AWS_BEARER_TOKEN_BEDROCK = os.getenv('AWS_BEARER_TOKEN_BEDROCK')
INTERFACE_PROFILE_ARN = os.getenv('INTERFACE_PROFILE_ARN')
NEPTUNE_ENDPOINT = os.getenv('NEPTUNE_ENDPOINT')