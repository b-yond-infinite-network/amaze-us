from tweet_streamer import Tweet_Stream_KAFKA
from multiprocessing import Process
import time

time.sleep(30)


bearer_token1 = 'AAAAAAAAAAAAAAAAAAAAALTngwEAAAAAjtlsLFwHeLr9aY3xrO12K%2BAHdSE%3DX40HKkZY8zIIADxxOdHFQh7xK1YtFCCRnWZySFrGYbeJQKFLF4'
bearer_token2 = 'AAAAAAAAAAAAAAAAAAAAALyFcQEAAAAA19QjyfhfeyGUuaM8s2B3WhtVBCU%3DPf16K6ppXBzulnOZ5EnPBo5kGeVN9WAY2WhhEff5eaPbHx8RYd'
country_codes= 'CA'
kafka_topic1 = 'evilnet-tweet-info'
kafka_topic2 = 'evilnet-retweet-info'
kafka_brokers = {'kafka-1:9092','kafka-2:9092'}
#intialise retweet streaming
tweets_stream= Tweet_Stream_KAFKA(bearer_token=bearer_token1,process=1,kafka_brokers=kafka_brokers,kafka_topic=kafka_topic1,country_codes=country_codes,only_tweets_or_retweets=True , retweets=False)
tweets_stream.delete_all_rules()
tweets_stream.set_rules()



#intialise retweet streaming
retweets_stream= Tweet_Stream_KAFKA(bearer_token=bearer_token2,process=2,kafka_brokers=kafka_brokers,kafka_topic=kafka_topic2,country_codes=country_codes,only_tweets_or_retweets=True , retweets=True)
retweets_stream.delete_all_rules()
retweets_stream.set_rules()




if __name__ == "__main__":
    p1 = Process(target = tweets_stream.publish_tweet) #start producing to topic evilnet-tweet-info  tweets only 
    p2 = Process(target = retweets_stream.publish_tweet) # start producing to topic evilnet-retweet-info kafka retweets only
    p1.start()                                           # this will enable parralelisim 
    p2.start()                                           # reduce processing done on the producer and consumer 
    p1.join()                                            # the number of total tweets can be added before sending to a db
    p2.join()