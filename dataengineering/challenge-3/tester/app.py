import pandas as pd
from pandas.testing import assert_frame_equal
from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider
from cassandra.query import dict_factory
import time

time.sleep(200)


auth_provider = PlainTextAuthProvider(username="cassandra", password="cassandra")
cluster = Cluster(contact_points=['172.18.0.9'], port=9042, auth_provider=auth_provider)

session = cluster.connect('evilnet')
session.row_factory = dict_factory

tweets_query = "SELECT * FROM {}.{};".format('evilnet', 'tweets')
retweets_query = "SELECT * FROM {}.{};".format('evilnet', 'retweets')
uniqueusers_query = "SELECT * FROM {}.{};".format('evilnet', 'uniqueusers')

tweets = pd.DataFrame()
retweets = pd.DataFrame()
uniqueusers = pd.DataFrame()

for row in session.execute(tweets_query):
    tweets =  pd.concat([tweets,pd.DataFrame(row, index=[0])])


for row in session.execute(retweets_query):
    retweets = pd.concat([retweets,pd.DataFrame(row, index=[0])])


for row in session.execute(uniqueusers_query):
    uniqueusers = pd.concat([uniqueusers,pd.DataFrame(row, index=[0])])


tweets = tweets.reset_index(drop=True)
retweets = retweets.reset_index(drop=True)
uniqueusers = uniqueusers.reset_index(drop=True)

tweets['date']=tweets['date'].astype(str)
retweets['date']=retweets['date'].astype(str)
uniqueusers['date']=uniqueusers['date'].astype(str)

tweets_anchor = pd.read_csv('./tweets.csv',index_col=None)
retweets_anchor = pd.read_csv('./retweets.csv',index_col=None)
uniqueusers_anchor = pd.read_csv('./uniqueusers.csv',index_col=None)


print('starting test')
print('mistmaches will appear below, if none than the schema is validated ')
print(' mismatches for tweets, ignore if none')
assert_frame_equal(tweets_anchor,tweets,check_dtype=False)
print(' mismatches for retweets, ignore if none')
assert_frame_equal(retweets_anchor,retweets,check_dtype=False)
print(' mismatches for uniqueusers, ignore if none')
assert_frame_equal(uniqueusers_anchor,uniqueusers,check_dtype=False)
print('ending test')

