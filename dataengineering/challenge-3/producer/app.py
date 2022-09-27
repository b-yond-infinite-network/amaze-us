import imp
from queue import Queue
from threading import Thread,Event
from tweet_streamer import Tweet_Stream_KAFKA
from queue_file import check_brokers_and_switch_condition,queue_to_file,file_to_queue
from health_check import block_until_all_brokers_are_connected
import os

#token should be set as secret\datasources
twitter_token = 'AAAAAAAAAAAAAAAAAAAAALyFcQEAAAAA19QjyfhfeyGUuaM8s2B3WhtVBCU%3DPf16K6ppXBzulnOZ5EnPBo5kGeVN9WAY2WhhEff5eaPbHx8RYd'

country_codes = str(os.environ.get('COUNTRY_CODES'))
kafka_topic = str(os.environ.get('KAFKA_TOPIC'))
kafka_brokers_csv = str(os.environ.get('KAFKA_BROKERS_CSV'))
is_test = int(str(os.environ.get('IS_TEST')))
kafka_brokers_list = [broker  for broker in str(kafka_brokers_csv).split(',')]

prod_queue : Queue = Queue()
all_brokers_are_down = Event()
one_broker_is_up = Event()
one_broker_is_up.set()

block_until_all_brokers_are_connected(kafka_brokers_list)

tweets_stream= Tweet_Stream_KAFKA(bearer_token=twitter_token,
                                  kafka_brokers=kafka_brokers_list,
                                  kafka_topic=kafka_topic,
                                  is_test=0,
                                  country_codes=country_codes)
tweets_stream.delete_all_rules()
tweets_stream.set_rules()


streamer_thread = Thread(target=tweets_stream.get_tweets, args=(prod_queue,))
check_brokers_and_switch_thread= Thread(target=check_brokers_and_switch_condition, args=(kafka_brokers_list,one_broker_is_up,all_brokers_are_down,))
kafka_produce_thread = Thread(target=tweets_stream.kafka_produce, args=(prod_queue, one_broker_is_up,))
queue_to_file_thread = Thread(target=queue_to_file, args=(prod_queue, all_brokers_are_down,))
file_to_queue_thread = Thread(target=file_to_queue, args=(prod_queue, one_broker_is_up,))



streamer_thread.start()
check_brokers_and_switch_thread.start()
queue_to_file_thread.start()
kafka_produce_thread.start()
file_to_queue_thread.start()

streamer_thread.join()
check_brokers_and_switch_thread.join()
queue_to_file_thread.join()
kafka_produce_thread.join()
file_to_queue_thread.join()
