import pandas as pd
from pandas.testing import assert_frame_equal
from cassandra.cluster import Cluster,NoHostAvailable
from cassandra.auth import PlainTextAuthProvider
from cassandra.query import dict_factory
import time


def get_different_rows(from_anchor, from_db):

    merged_df = from_anchor.merge(from_db, indicator=True, how='outer')
    changed_rows_df = merged_df[merged_df['_merge'] == 'right_only']
    
    return changed_rows_df.drop('_merge', axis=1)


def do_test_on(table_name,session):

    query = "SELECT * FROM {}.{};".format('evilnet', table_name)
    from_db = pd.DataFrame()

    for row in session.execute(query):
        from_db  = pd.concat([from_db,pd.DataFrame(row, index=[0])])

    from_db = from_db.reset_index(drop=True)
    from_db['date'] = from_db['date'].astype(str)
    from_anchor = pd.read_csv('./{}.csv'.format(table_name),index_col=None)

    try:
        assert_frame_equal(from_anchor,from_db,check_dtype=False,check_categorical=True)
    except AssertionError as msg:
        print('There is a difference between anchor data and pipline data for {} \n'.format(table_name))
        print(msg)
        if get_different_rows(from_anchor,from_db).empty == False:
            print('\n Different Rows in anchor data')
            print(get_different_rows(from_anchor,from_db))
            print('\n Different Rows in pipline data')
            print(get_different_rows(from_db,from_anchor))
    else:
        print('\n Anchor data is similar to pipline data for {}'.format(table_name))





auth_provider = PlainTextAuthProvider(username="cassandra", password="cassandra")
b = True


while b:
    try:
        cluster = Cluster(contact_points=['172.18.0.9'], port=9042, auth_provider=auth_provider)
        session = cluster.connect('evilnet')
    except NoHostAvailable as msg:
        print('Cassandra host is down, check the below error, will reconnect in 10 seconds')
        print(msg)
        time.sleep(10)
    else:
        print('DB connection was succesfull')
        b = False


session.row_factory = dict_factory

do_test_on('tweets',session)
do_test_on('retweets',session)
do_test_on('uniqueusers',session)


