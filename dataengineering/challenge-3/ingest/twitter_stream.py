import requests
import time
import socket
from confluent_kafka import Producer
from argparse import ArgumentParser, ArgumentTypeError
from os import path, makedirs, getcwd, listdir, environ
from json import loads as js_loads
from json import dumps as js_dumps
from datetime import datetime

# To set your enviornment variables in your terminal run the following line:
# export 'BEARER_TOKEN'='<your_bearer_token>'
BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAAS%2BPQEAAAAAhPwGY3U2B2fd6E%2F9KIoIb5dclgU%3DmB1i2n28bFu1MOUzUvvAkiUy8dGh6XHoeRsUnFOxKs42qhntjZ"
API_SECRET_KEY = "KwzXPDORWsn2zWahGfsWiVK12GrKaElpIjBdFUvmO2aKrwD21W"
API_KEY = "HHWubKrTHqpA2s6s6hGNczVxe"
OUTPUT_PATH = path.join(getcwd(), "tw_data")
KAFKA_BROKER = "by-kafka-broker:9092"
KAFKA_TOPIC = "stream-tweets"


def get_kafka_producer(context):
    conf = {
        'bootstrap.servers': context['kafka_broker'],
        'client.id': context['client_id']
    }

    return Producer(conf)


def produce_msg(context, key, msgs):
    producer = context['kafka_producer']
    topic = context['kafka_topic']
    producer.produce(topic, key=key, value=msgs)


def get_file_name(output_path):
    return path.join(output_path,
                     "{}.json".format(datetime.now().strftime("%Y%m%d%H%M")))


def create_bearer_headers(bearer_token):
    headers = {"Authorization": "Bearer {}".format(bearer_token)}
    return headers


def get_rules(headers, bearer_token):
    response = requests.get(
        "https://api.twitter.com/2/tweets/search/stream/rules",
        headers=headers)
    if response.status_code != 200:
        raise Exception("Cannot get rules (HTTP {}): {}".format(
            response.status_code, response.text))
    print(js_dumps(response.json()))
    return response.json()


def delete_all_rules(headers, bearer_token, rules):
    if rules is None or "data" not in rules:
        return None

    ids = list(map(lambda rule: rule["id"], rules["data"]))
    payload = {"delete": {"ids": ids}}
    response = requests.post(
        "https://api.twitter.com/2/tweets/search/stream/rules",
        headers=headers,
        json=payload)
    if response.status_code != 200:
        raise Exception("Cannot delete rules (HTTP {}): {}".format(
            response.status_code, response.text))
    print(js_dumps(response.json()))


def set_rules(headers, bearer_token):
    sample_rules = [{
        "value": "covid vaccine -is:retweet",
        "tag": "pandemic: covid and vaccine"
    }]
    payload = {"add": sample_rules}
    response = requests.post(
        "https://api.twitter.com/2/tweets/search/stream/rules",
        headers=headers,
        json=payload,
    )
    if response.status_code != 201:
        raise Exception("Cannot add rules (HTTP {}): {}".format(
            response.status_code, response.text))
    print(js_dumps(response.json()))


def dump_streaming(response, context):
    """Dump to File and Publish to Kafka. 
    TODO: Split publish from file dumper.
    """
    tracking = time.time()
    file_name = get_file_name(context['output_path'])
    json_handler = open(file_name, 'w')
    for response_line in response.iter_lines():
        if time.time() - 300 > tracking:
            json_handler.close()
            print(datetime.now().strftime("%Y%m%d%H%M"))
            file_name = get_file_name(context['output_path'])
            json_handler = open(file_name, 'w')
            tracking = time.time()
        if response_line:
            tweet_mgs = response_line.decode("utf-8")
            if context['publish_to_kafka']:
                produce_msg(context, key="tweets", msgs=tweet_mgs)
            json_handler.write(tweet_mgs + "\n")
    json_handler.close()


def get_stream(headers, params, bearer_token):
    response = requests.get("https://api.twitter.com/2/tweets/search/stream",
                            headers=headers,
                            stream=True,
                            params=params)
    print(response.status_code)
    if response.status_code != 200:
        raise Exception("Cannot get stream (HTTP {}): {}".format(
            response.status_code, response.text))
    return response


def main(context):
    """Entry Function."""
    bearer_token = context['bearer_token']
    headers = create_bearer_headers(bearer_token)
    rules = get_rules(headers, bearer_token)
    delete_all_rules(headers, bearer_token, rules)
    set_rules(headers, bearer_token)
    response = get_stream(headers, context['params'], bearer_token)
    if context['publish_to_kafka']:
        context['kafka_producer'] = get_kafka_producer(context)
    dump_streaming(response, context)


if __name__ == "__main__":
    # Initiate the parser
    parser = ArgumentParser()
    # Add long and short argument
    parser.add_argument("-k",
                        "--to_kafka",
                        help="publish tweet text to kafka",
                        required=False,
                        default=False,
                        action='store_true')
    parser.add_argument("-o",
                        "--output_path",
                        help="Path to store the data",
                        required=False,
                        default=OUTPUT_PATH)

    args = parser.parse_args()
    _context = {
        "bearer_token": environ.get("BEARER_TOKEN", BEARER_TOKEN),
        "output_path": args.output_path,
        "publish_to_kafka": args.to_kafka,
        "kafka_broker": KAFKA_BROKER,
        "kafka_topic": KAFKA_TOPIC,
        "kafka_producer": None,
        "client_id": socket.gethostname(),
        "params": {
            'place.fields':
            'contained_within,country,country_code,full_name,geo,id,name,place_type',
            'tweet.fields':
            'conversation_id,created_at,geo,lang,public_metrics,referenced_tweets,reply_settings,source,text,withheld',
            'expansions': 'geo.place_id'
        }
    }
    if not path.exists(_context['output_path']):
        makedirs(_context['output_path'])
    main(_context)