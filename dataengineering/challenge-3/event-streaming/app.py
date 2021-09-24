import socket
import sys
import requests
import requests_oauthlib
import traceback
import json, os
from time import sleep
from json import dumps
from kafka import KafkaProducer

# Major Canadian cities (Calgary, Vancouver, Toronto, Montreal)
TORONTO_BBOX = '-79.573975,43.586359,-79.208679,43.730422'
CALGARY_BBOX = '-114.220047,50.914723,-113.903503,51.175899'
OTTAWA_BBOX = '-75.722923,45.356609,-75.609627,45.436526'
HALIFAX_BBOX = '-63.651609,44.616500,-63.526468,44.675550'
MONTREAL_BBOX = '-79.573975,43.586359,-73.420944,45.667325'
VANCOUVER_BBOX = '-123.274498,49.190455,-122.980614,49.340112'
CANADIAN_CITIES = {
    TORONTO_BBOX, 
    CALGARY_BBOX, 
    OTTAWA_BBOX, 
    HALIFAX_BBOX, 
    MONTREAL_BBOX, 
    VANCOUVER_BBOX
    }
TWITTER_ENDPOINT_URL = 'https://stream.twitter.com/1.1/statuses/filter.json'

CONSUMER_KEY = os.getenv('CONSUMER_KEY')
CONSUMER_SECRET = os.getenv('CONSUMER_SECRET')
ACCESS_TOKEN = os.getenv('ACCESS_TOKEN')
ACCESS_SECRET = os.getenv('ACCESS_SECRET')
KAFKA_BROKERS = os.getenv('KAFKA_BROKERS')
KAFKA_TOPIC = os.getenv('KAFKA_TOPIC')
print(KAFKA_BROKERS)

def process_tweets(http_resp):
    producer = KafkaProducer(
        bootstrap_servers=[KAFKA_BROKERS],
        value_serializer=lambda x: dumps(x).encode('utf-8')
    )
    for line in http_resp.iter_lines():
        try:
            tweet = json.loads(line.decode('utf-8'))
            if tweet != None:
                if 'place' in tweet.keys() and tweet['place'] != None and tweet['place']['name'] != None:
                    tweet_payload = {
                        'id': tweet['id'],
                        'user': tweet['user']['id'],
                        'time': tweet['created_at'],
                        'location': tweet['place']['name'],
                        'is_retweet': 'retweeted_status' in tweet.keys()
                    }
                    #print(tweet_payload)
                    producer.send(KAFKA_TOPIC, value=tweet_payload)
        except:
            traceback.print_exc()
            e = sys.exc_info()[0]
            print("Error: %s" % e)


def get_twitter_stream():
    twitter_auth = requests_oauthlib.OAuth1(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_SECRET)

    params = {
        'locations': ','.join(CANADIAN_CITIES),
        'track': '#'
    }
    response = requests.get(TWITTER_ENDPOINT_URL, params=params, auth=twitter_auth, stream=True)
    #print(response.status_code)
    return response

def main():
    print("Twitter stream consumer started")
    sleep(5)
    resp = get_twitter_stream()
    process_tweets(resp)

if __name__ == "__main__":
    main()