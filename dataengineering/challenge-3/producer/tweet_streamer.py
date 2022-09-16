import requests
import json
from kafka import KafkaProducer
import pickle

class TweetStream:
   
    """"
    Encapsulate the process of setting filters, and getting a tweeter V2 api stream 
    specifically for EVILNET

    It is extended by Tweet_Stream_KAFKA to 

    It's based on the process provided by twitter in.
    https://github.com/twitterdev/Twitter-API-v2-sample-code/blob/main/Filtered-Stream/filtered_stream.py

    For detailed knowledge about filters refer to:
    https://developer.twitter.com/en/docs/twitter-api/fields

    For getting a rule using GUI refer to:
    https://developer.twitter.com/apitools/api?endpoint=%2F2%2Ftweets%2Fsearch%2Fstream%2Frules&method=post

    For detailed knowledge about Filtered stream refer to:
    https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream/integrate/build-a-rule

    For detailed knowledge about filters refer to:
    https://developer.twitter.com/en/docs/twitter-api/fields


    to add args 
    """
    def __init__(self , bearer_token , process, country_codes='CA', only_tweets_or_retweets=True , retweets=False):
        # to extend and add filters 
        self.bearer_token = bearer_token
        self.country_codes = country_codes
        self.only_tweets_or_retweets = only_tweets_or_retweets
        self.retweets = retweets
        self.process = process

    def bearer_oauth(self,request):
        
        """"
        This function takes the instance of request and adjust it's header
        """      
        request.headers["Authorization"] = f"Bearer {self.bearer_token}"
        request.headers["User-Agent"] = "v2FilteredStreamPython"
        
        return request

    def get_rules(self):

        """"
        Will get twitter rules for a single auth 

        """ 

        response = requests.get(
            "https://api.twitter.com/2/tweets/search/stream/rules",
          auth=self.bearer_oauth)
        if response.status_code != 200:
            raise Exception(
                "Cannot get rules (HTTP {}): {}".format(response.status_code, response.text)
            )

        return response.json()


    def delete_all_rules(self ):

        """"
        Will get twitter rules for a single auth app and delete them
        """ 
        rules= self.get_rules()
        if rules is None or "data" not in rules:
            return None

        ids = list(map(lambda rule: rule["id"], rules["data"]))
        payload = {"delete": {"ids": ids}} 
        response = requests.post(
            "https://api.twitter.com/2/tweets/search/stream/rules",
            auth=self.bearer_oauth,
            json=payload
        )
        if response.status_code != 200:
            raise Exception(
                "Cannot delete rules (HTTP {}): {}".format(
                    response.status_code, response.text
                )
            )
        
        return(json.dumps(response.json()))

    def set_rules(self):

        """"
        Sets rules 
        """ 
        if (self.only_tweets_or_retweets and self.retweets ):
            filterstring="is:retweet"

        elif (self.only_tweets_or_retweets and not self.retweets):
            filterstring="-is:retweet"
        
        else:
            filterstring=""


        countries_list = self.country_codes.split(',')

        for item in countries_list:
            filterstring += " place_country:{}".format(item)

    
        sample_rules = [
             {"value": filterstring },
             ]

        payload = {"add": sample_rules}
        response = requests.post(
           "https://api.twitter.com/2/tweets/search/stream/rules",
           auth=self.bearer_oauth,
           json=payload,
        )
        if response.status_code != 201:
           raise Exception(
               "Cannot add rules (HTTP {}): {}".format(response.status_code, response.text)
           )
        print(json.dumps(response.json()))


    def get_stream(self):

        """"
        request a stream, establish an open connection, twitter will send never ending data
        in cases of diconection the app should reconnect.

        How to consume streaming data
        https://developer.twitter.com/en/docs/tutorials/consuming-streaming-data

        """ 

        filters = { 
                    "expansions": "author_id,geo.place_id",
                    "tweet.fields": "created_at,referenced_tweets",
                    "place.fields": "place_type,name",
                    }


        response = requests.get(
            "https://api.twitter.com/2/tweets/search/stream",
            auth=self.bearer_oauth,
            stream=True, params=filters)
        print(response.status_code)
        
        if response.status_code != 200:
            raise Exception(
                "Cannot get stream (HTTP {}): {}".format(
                    response.status_code, response.text
                )
            )
        
        return response

class Tweet_Stream_KAFKA(TweetStream):

    """"
    Extends TweetStream to encapsulate the process of producing to twitter.

    to add args 
    """ 
    def __init__(self , bearer_token , process, kafka_brokers, kafka_topic, country_codes='CA', only_tweets_or_retweets=True , retweets=False):
        super().__init__( bearer_token , process,country_codes='CA', only_tweets_or_retweets=True , retweets=False)
        self.kafka_brokers = kafka_brokers
        self.kafka_topic = kafka_topic

    def producer_instance(self):
        

         producer = KafkaProducer(bootstrap_servers=self.kafka_brokers,value_serializer=lambda x: json.dumps(x).encode('utf-8'))

         return producer
 
 

    def publish_tweet(self):

        """"

        Publishes tweets to kafka
        """ 

        
        producer=self.producer_instance()

        for line in self.get_stream().iter_lines():
            if line:
                object = json.loads(line)
                
                if (object["includes"]["places"][0]["place_type"]) == 'city':
                    tweet= {
                        "id" :  object["data"]["id"],
                        "author_id" : object["data"]["author_id"],
                        "full_location" : object["includes"]["places"][0]["full_name"],
                        "created_at" : object["data"]["created_at"]
                    }
                    print(self.process)
                    print(self.kafka_topic)
                    producer.send(topic=self.kafka_topic, value=tweet)
                    
            
        
