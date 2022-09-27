import time
from cassandra.cluster import Cluster,NoHostAvailable
from cassandra.auth import PlainTextAuthProvider


def block_until_cassandra_ok(username, password, key_space, host, port):

    cassandra_auth_provider = PlainTextAuthProvider(username=username, password=password)
    not_connected = True
    while not_connected:
        try:
            cluster = Cluster(contact_points=[host], port=port, auth_provider = cassandra_auth_provider)
            _ = cluster.connect(key_space)
        except Exception as msg:
            print('Cassandra is not ready, check the below error, will reconnect in 10 seconds')
            print(msg)
            time.sleep(10)
        else:
            print('DB connection was succesfull')
            not_connected = False

    cluster.shutdown()