from langchain_aws import ChatBedrock
from langchain_aws import BedrockEmbeddings
from botocore.config import Config

from utils.config_loader import load_config
from dotenv import load_dotenv

load_dotenv()

print("Loading bedrock client...")
region_name = "us-east-1"
provider_name = "anthropic"

bedrock_config = Config(
    read_timeout=300,      # increase read timeout (default ~60 secs)
    connect_timeout=60,    # connection timeout
    retries={
        "max_attempts": 5,
        "mode": "standard"
    }
)

llm = ChatBedrock(
                model="arn:aws:bedrock:us-east-1:675691260279:inference-profile/us.anthropic.claude-opus-4-5-20251101-v1:0",
                provider=provider_name,
                region=region_name,
                config=bedrock_config
            )

response = llm.invoke("Hi")

print("RESULT")
print(response)

