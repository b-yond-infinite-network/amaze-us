import os
os.environ["TIME_WIN"] = "15 minutes"

import pytest
import datetime
from pyspark.sql import SparkSession, Row
from ..app import _process_retweets, _process_tweets, _process_users


spark = SparkSession \
    .builder \
    .getOrCreate()
        
        
sample_input = [
    Row(id='1570247656294699266', author_id='381720541', is_retweet=False, place='Montreal, Quebec', timestamp=datetime.datetime(2022, 9, 15, 3, 6, 25)),
    Row(id='1570247656898699266', author_id='381720540', is_retweet=False, place='Toronto, Ontario', timestamp=datetime.datetime(2022, 9, 15, 3, 10, 28)),
    Row(id='1570247656898699267', author_id='381720540', is_retweet=False, place='Toronto, Ontario', timestamp=datetime.datetime(2022, 9, 15, 3, 13, 29)),
    Row(id='1570247671100870656', author_id='19293065', is_retweet=False, place='Prince George, British Columbia', timestamp=datetime.datetime(2022, 9, 15, 3, 20, 33)),
    Row(id='1570247692026257409', author_id='1535727042398134272', is_retweet=False, place='Halifax, Nova Scotia', timestamp=datetime.datetime(2022, 9, 15, 3, 22, 38)),
    Row(id='1570247693389426690', author_id='28117439', is_retweet=True, place='Toronto, Ontario', timestamp=datetime.datetime(2022, 9, 15, 3, 25, 38)),
    Row(id='1570247691510353920', author_id='746099559374327808', is_retweet=False, place='Chilliwack, British Columbia', timestamp=datetime.datetime(2022, 9, 15, 3, 25, 38)),
    Row(id='1570247700796567556', author_id='16107875', is_retweet=True, place='Calgary, Alberta', timestamp=datetime.datetime(2022, 9, 15, 3, 30, 40)),
    Row(id='1570247703552225280', author_id='1124865657257041920', is_retweet=True, place='Winnipeg, Manitoba', timestamp=datetime.datetime(2022, 9, 15, 3, 31, 40)),
    Row(id='1570247706781847554', author_id='20010095', is_retweet=True, place='Winnipeg, Manitoba', timestamp=datetime.datetime(2022, 9, 15, 3, 31, 41)),
    Row(id='1570247704453734400', author_id='1427271432103415808', is_retweet=True, place='LaSalle, Ontario', timestamp=datetime.datetime(2022, 9, 15, 3, 33, 41)),
    Row(id='1570247716659429378', author_id='61232512', is_retweet=True, place='Toronto, Ontario', timestamp=datetime.datetime(2022, 9, 15, 3, 44, 44)),
    Row(id='1570247735978369025', author_id='19293065', is_retweet=False, place='Prince George, British Columbia', timestamp=datetime.datetime(2022, 9, 15, 3, 44, 48))
    ]


def test_process_tweets():
    sample_output = [
        Row(place='Montreal, Quebec', count=1, time=datetime.datetime(2022, 9, 15, 3, 0)),
        Row(place='Toronto, Ontario', count=2, time=datetime.datetime(2022, 9, 15, 3, 0)),
        Row(place='Halifax, Nova Scotia', count=1, time=datetime.datetime(2022, 9, 15, 3, 15)),
        Row(place='Prince George, British Columbia', count=1, time=datetime.datetime(2022, 9, 15, 3, 15)),
        Row(place='Toronto, Ontario', count=1, time=datetime.datetime(2022, 9, 15, 3, 15)),
        Row(place='Calgary, Alberta', count=1, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='Winnipeg, Manitoba', count=2, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='Chilliwack, British Columbia', count=1, time=datetime.datetime(2022, 9, 15, 3, 15)),
        Row(place='Prince George, British Columbia', count=1, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='LaSalle, Ontario', count=1, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='Toronto, Ontario', count=1, time=datetime.datetime(2022, 9, 15, 3, 30))
        ]

    df_in = spark.createDataFrame(sample_input)
    df_out = spark.createDataFrame(sample_output)
    
    df_proc = _process_tweets(df_in)
    
    assert df_proc.collect() == df_out.collect()
    
    
def test_process_users():
    sample_output = [
        Row(place='Montreal, Quebec', count=1, time=datetime.datetime(2022, 9, 15, 3, 0)),
        Row(place='Toronto, Ontario', count=2, time=datetime.datetime(2022, 9, 15, 3, 0)),
        Row(place='Halifax, Nova Scotia', count=1, time=datetime.datetime(2022, 9, 15, 3, 15)),
        Row(place='Prince George, British Columbia', count=1, time=datetime.datetime(2022, 9, 15, 3, 15)),
        Row(place='Toronto, Ontario', count=1, time=datetime.datetime(2022, 9, 15, 3, 15)),
        Row(place='Calgary, Alberta', count=1, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='Winnipeg, Manitoba', count=2, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='Chilliwack, British Columbia', count=1, time=datetime.datetime(2022, 9, 15, 3, 15)),
        Row(place='Prince George, British Columbia', count=1, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='LaSalle, Ontario', count=1, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='Toronto, Ontario', count=1, time=datetime.datetime(2022, 9, 15, 3, 30))
        ]
    
    df_in = spark.createDataFrame(sample_input)
    df_out = spark.createDataFrame(sample_output)
    
    df_proc = _process_users(df_in)
    
    assert df_proc.collect() == df_out.collect()
    

def test_process_retweets():
    sample_output = [
        Row(place='Toronto, Ontario', count=1, time=datetime.datetime(2022, 9, 15, 3, 15)),
        Row(place='Calgary, Alberta', count=1, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='Winnipeg, Manitoba', count=2, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='LaSalle, Ontario', count=1, time=datetime.datetime(2022, 9, 15, 3, 30)),
        Row(place='Toronto, Ontario', count=1, time=datetime.datetime(2022, 9, 15, 3, 30))
        ]
    
    df_in = spark.createDataFrame(sample_input)
    df_out = spark.createDataFrame(sample_output)
    
    df_proc = _process_retweets(df_in)
    
    assert df_proc.collect() == df_out.collect()