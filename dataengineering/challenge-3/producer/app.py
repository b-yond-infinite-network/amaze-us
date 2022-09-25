from json.encoder import INFINITY
from tweet_streamer import Tweet_Stream_KAFKA
from multiprocessing import Process
import time
import os
time.sleep(30)

# + to do : add as env vars to container
bearer_token1 = 'AAAAAAAAAAAAAAAAAAAAALTngwEAAAAAjtlsLFwHeLr9aY3xrO12K%2BAHdSE%3DX40HKkZY8zIIADxxOdHFQh7xK1YtFCCRnWZySFrGYbeJQKFLF4'
country_codes= 'CA'
kafka_topic = 'evilnet-tweet-info'
kafka_brokers = ['172.18.0.5:9092'] # broker kafka-1 will be used to send to kafka
                                 # kafka-2 will be used to stream using spark
is_test=os.environ.get('IS_TEST')
is_test=int(is_test)
print(is_test)



tweets_stream= Tweet_Stream_KAFKA(bearer_token=bearer_token1,kafka_brokers=kafka_brokers,kafka_topic=kafka_topic,is_test=is_test,country_codes=country_codes)
tweets_stream.delete_all_rules()
tweets_stream.set_rules()
tweets_stream.publish_tweet()

print('streaming exited please restart the container')
time.sleep(INFINITY)


