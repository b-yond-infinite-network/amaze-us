import requests
import json
from kafka import KafkaProducer
from queue import Empty,Queue
from setup_logging import logger


class TweetStream:
   
    """"
    Encapsulate the process of setting filters, and getting a tweeter V2 api stream 
    specifically for EVILNET, will allow to filter between tweets and retweets if needed and will allow to set countrt_codes as a csv string exp 'CA' or 'CA,US'
    + to do: add filters for entreprise usage

    It is extended by Tweet_Stream_KAFKA to to encapsulate producing to kafka
    + to do: modify to become async between getting data and producing to kafka, or async between multiple streams.

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


    + to do: add args 
    """
    def __init__(self, bearer_token : str , country_codes : str):
        # to extend and add filters 
        self.bearer_token = bearer_token
        self.country_codes = country_codes
 

    def bearer_oauth(self, request):
        
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

        logger.info("Cannot get rules (HTTP {}): {}".format(response.status_code, response.text))
        return response.json()

    def delete_all_rules(self ):

        """"
        Will get twitter rules for a single auth app and delete them
        """ 
        rules = self.get_rules()
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

        logger.info("Cannot delete rules (HTTP {}): {}".format(response.status_code, response.text))

    def set_rules(self):

        """"
        Sets rules 
        """ 
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

        logger.info(json.dumps(response.json()))

    def return_params(self) -> dict:
        """
        return filters
        Can be adjusted to load filters from a file 
        """

        filters = { 
                "expansions": "author_id,geo.place_id",
                "tweet.fields": "created_at,referenced_tweets",
                "place.fields": "place_type,name",
                }

        return filters

    def stream(self):

        """"
        request a stream, establish an open connection, twitter will send never ending data
        in cases of diconection the app should reconnect.
        How to consume streaming data
        https://developer.twitter.com/en/docs/tutorials/consuming-streaming-data

        """ 
        return  requests.get(
                "https://api.twitter.com/2/tweets/search/stream",
                auth=self.bearer_oauth,
                stream=True, params=self.return_params())

    def get_stream(self):
        response = self.stream()
        if response.status_code != 200:
            raise Exception(
                "Cannot get stream (HTTP {}): {}".format(
                    response.status_code, response.text
                    )
                )
        for response_line in response.iter_lines():
            if response_line:
                json_response = json.loads(response_line)
                yield json_response

        
class Tweet_Stream_KAFKA(TweetStream):

    """"
    Extends TweetStream to encapsulate the process of producing to twitter.
    To operate using two threads, one for getting data from twitter, and another for producing to kafka
    Can be used in testing mode, this will allow the pipline to fetch data from the local file tweets.json
    """ 

    def __init__(self , bearer_token : str, kafka_brokers : list[str], kafka_topic : str,is_test : int, country_codes : str):
        super().__init__( bearer_token ,country_codes)
        self.kafka_brokers = kafka_brokers
        self.kafka_topic = kafka_topic
        self.is_test = is_test
    
    def producer_instance(self):
        
        try:
            producer = KafkaProducer(bootstrap_servers=self.kafka_brokers,value_serializer=lambda x: json.dumps(x).encode('utf-8'))
        except Exception as msg :
            logger.error('The following exception was raised when connecting to Kafka: {}'.format(msg))

        return producer
 

    def scheme_tweets(self, object : dict) -> dict:
    
        """
        Performs transformation on raw tweets.
        """

        tweet= {

            "id": object["data"]["id"],
            "author_id": object["data"]["author_id"],
            "timestamp": object["data"]["created_at"],
            "is_retweet": "referenced_tweets" in object["data"],
            "city" : object["includes"]["places"][0]["full_name"]
        }

        return tweet
 

    def get_tweets(self, queue : Queue) -> None:
        
        """
        This function will add tweets to a queue.
        If is_test == 1 it will get data from tweets.json using test_tweet() function
        if is_tes == 0 it will stream data from twitter using the get_stream() function
        """
        logger.debug('Streaming from twitter')

        if self.is_test == 1: 
            tweets=self.test_tweet()
        elif self.is_test == 0:
            tweets=self.get_stream()

        for item in tweets:
            try:
                queue.put(item, block=True, timeout=1)
            except Exception as msg:
                logger.warn(msg)
        
    def kafka_produce(self, queue : Queue, one_broker_is_up) -> None:
        
        kafka_topic=self.kafka_topic
        producer=self.producer_instance()
        logger.debug('Producing to kafka')
        
        while True:
            while one_broker_is_up.wait():
                try:
                    raw = queue.get(timeout=15)
                    if (raw["includes"]["places"][0]["place_type"]) == 'city':
                        producer.send(kafka_topic, self.scheme_tweets(raw))
                except Empty:
                    logger.info('There are no tweets in the queue.')
                

    def test_tweet(self):

        """
        This function will get tweets from tweets.json and yield it for testing purposes.
        """

        file_content = [json.loads(line) for line in open('tweets.json' , 'r' , encoding='utf-8')]

        for response_line in file_content:
            if response_line:
                json_response = json.loads(response_line)
                yield json_response