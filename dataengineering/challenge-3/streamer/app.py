import os
import json
import logging
import requests
import requests_oauthlib

from kafka import KafkaProducer

logger = logging.getLogger('streamer')
logger.setLevel(logging.INFO) # one selection here used for aavaa-app
logger.propagate = False  # don't propagate (in case of multiple imports)
formatter = logging.Formatter('%(asctime)s | %(message)s')

file_handler = logging.FileHandler("streamer.log", "w+")
file_handler.setLevel(logging.INFO)
file_handler.setFormatter(formatter)
logger.addHandler(file_handler)

# To set your environment variables in your terminal run the following line:
# export 'BEARER_TOKEN'='<your_bearer_token>'
# os.environ["KAFKA_BROKER"] = "localhost:9093"
# os.environ["KAFKA_TOPIC"] = "tweets"
os.environ["BEARER_TOKEN"] = "AAAAAAAAAAAAAAAAAAAAAJO7ggEAAAAATMt5DRF97qbVhlq%2FNqIE0JSkVpo%3D9KlpF0SsY1qkHC60cw8W2JAX6F0gKPfjCejddu06qVaPfNW0eG"

bearer_token = os.environ.get("BEARER_TOKEN")
kafka_topic = os.environ.get("KAFKA_TOPIC")
kafka_broker = os.environ.get("KAFKA_BROKER")


def publish_tweet(producer, response):
    for response_line in response.iter_lines():
        if response_line:
            json_response = json.loads(response_line)
            
            # print(json.dumps(json_response, indent=4, sort_keys=True))
            # retweet is either `retweeted`, `quoted` or `replied_to`
            place_type = json_response["includes"]["places"][0]["place_type"]
            if place_type == "city":
                tweet_info = {
                    "author_id": json_response["data"]["author_id"],
                    "id": json_response["data"]["id"],
                    "created_at": json_response["data"]["created_at"],
                    "is_retweet": "referenced_tweets" in json_response["data"],
                    "place": json_response["includes"]["places"][0]["full_name"]
                }
                logger.debug(json.dumps(tweet_info, indent=4, sort_keys=True))
            
                publish_message(producer_instance=producer, value=tweet_info)
            
    
def publish_message(producer_instance, value):
    try:
        producer_instance.send(topic=kafka_topic, value=value)
        # producer_instance.flush()
        logger.debug('Message published successfully.')
    except Exception as ex:
        logger.warning('Exception in publishing message')
        logger.warning(str(ex))


def connect_kafka_producer():
    _producer = None
    try:
        _producer = KafkaProducer(bootstrap_servers=[kafka_broker],
                                  api_version=(0,10,2),
                                  value_serializer=lambda x: json.dumps(x).encode('utf-8'))
    except Exception as ex:
        logger.warning('Exception while connecting Kafka')
        logger.warning(str(ex))
    finally:
        return _producer
    

def bearer_oauth(r):
    """
    Method required by bearer token authentication.
    """
    r.headers["Authorization"] = f"Bearer {bearer_token}"
    r.headers["User-Agent"] = "v2FilteredStreamPython"
    return r

def get_rules():
    response = requests.get(
        "https://api.twitter.com/2/tweets/search/stream/rules", auth=bearer_oauth
    )
    if response.status_code != 200:
        raise Exception(
            "Cannot get rules (HTTP {}): {}".format(response.status_code, response.text)
        )
    logger.info(json.dumps(response.json()))
    return response.json()


def delete_all_rules(rules):
    if rules is None or "data" not in rules:
        return None

    ids = list(map(lambda rule: rule["id"], rules["data"]))
    payload = {"delete": {"ids": ids}}
    response = requests.post(
        "https://api.twitter.com/2/tweets/search/stream/rules",
        auth=bearer_oauth,
        json=payload
    )
    if response.status_code != 200:
        raise Exception(
            "Cannot delete rules (HTTP {}): {}".format(
                response.status_code, response.text
            )
        )
    logger.info(json.dumps(response.json()))


def set_rules(delete):
    # You can adjust the rules if needed
    sample_rules = [
        {"value": "place_country:CA"},
    ]
    payload = {"add": sample_rules}
    response = requests.post(
        "https://api.twitter.com/2/tweets/search/stream/rules",
        auth=bearer_oauth,
        json=payload,
    )
    if response.status_code != 201:
        raise Exception(
            "Cannot add rules (HTTP {}): {}".format(response.status_code, response.text)
        )
    logger.info(json.dumps(response.json()))
    return


def get_stream(set):
    
    params = {
        "tweet.fields": "created_at,referenced_tweets",
        "place.fields": "place_type",
        "expansions": "author_id,geo.place_id",
    }
    response = requests.get(
        "https://api.twitter.com/2/tweets/search/stream",
        auth=bearer_oauth,
        params=params,
        stream=True,
    )
    logger.info(response.status_code)
    if response.status_code != 200:
        raise Exception(
            "Cannot get stream (HTTP {}): {}".format(
                response.status_code, response.text
            )
        )
    
    return response


def main():
    producer = connect_kafka_producer()
    rules = get_rules()
    delete = delete_all_rules(rules)
    set = set_rules(delete)
    response = get_stream(set)
    publish_tweet(producer, response)


if __name__ == "__main__":
    main()