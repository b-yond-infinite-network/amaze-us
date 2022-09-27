from kafka import KafkaAdminClient
from kafka.admin import NewTopic
from kafka.errors import NoBrokersAvailable, UnrecognizedBrokerVersion , NodeNotReadyError,RequestTimedOutError
from time import sleep
from setup_logging import logger

def test_connection_until_success(bootstrap_server : str) -> bool:

    """
    Takes 1 kafka broker address:port as input, will keep trying to connect.
    The criterea of the broker availabily, is that KafkaAdminClient can connect to it, and validate the creation of a topic

    """
    topic_list = [NewTopic(name="test_conn_topic", num_partitions=1, replication_factor=1)]
    connected_yet = False

    while not connected_yet:

        try:
            testclient=KafkaAdminClient(bootstrap_servers=bootstrap_server, client_id='test')
        except (UnrecognizedBrokerVersion) as msg:
            logger.warn('{} has been raised, not all broker services are up yet in : {}'.format(msg,bootstrap_server))
            sleep(1)
        except (NoBrokersAvailable) as msg:
            logger.warn('{} has been raised, could not connect to : {} '.format(msg,bootstrap_server))
            sleep(1)
        except (NodeNotReadyError) as msg:
            logger.warn('{} has been raised, The following node is not Ready yet : {} '.format(msg,bootstrap_server))
            sleep(1)
        else:
            try:
                _=testclient.create_topics(topic_list,validate_only=True,timeout_ms=100) 

            except Exception as msg:
                logger.warn('Known error {}'.format(msg))
                sleep(1)

            else:
                connected_yet = True
                testclient.close()
                logger.info('Connected to {} '.format(bootstrap_server))

    return connected_yet


def test_connection_once(bootstrap_server : str) -> bool:

        
    """
    Takes 1 kafka broker address:port as input, will try to connect once, and return if the broker is availabily or not.
    The criterea of the broker availabily, is that KafkaAdminClient can connect to it, and validate the creation of a topic

    """

    topic_list = [NewTopic(name="test_conn_topic", num_partitions=1, replication_factor=1)]
    connected = False

    try: 
        testclient=KafkaAdminClient(bootstrap_servers=bootstrap_server, client_id='test',request_timeout_ms = 500)

    except ( UnrecognizedBrokerVersion) as msg:
        logger.warn('{} has been raised, not all broker services are up yet in {}'.format(msg,bootstrap_server))

    except ( NoBrokersAvailable) as msg:
        logger.warn('{} has been raised, could not connect to {} '.format(msg,bootstrap_server))

    except ( NodeNotReadyError) as msg:
        logger.warn('{} has been raised, the following node is not ready {} '.format(msg,bootstrap_server))
        
    except ( RequestTimedOutError) as msg:
        logger.warn('{} has been raised, Node is hanging {} '.format(msg,bootstrap_server))
        
    else:
        try:
            _=testclient.create_topics(topic_list,validate_only=True,timeout_ms=100) 
        except Exception as msg:
            logger.warn('Known error {}'.format(msg))
        else:
            connected = True
            testclient.close()
            logger.info('Connected to {} '.format(bootstrap_server))

    return connected


def block_until_all_brokers_are_connected(kafka_brokers_list : list[str]) -> None :
    
    """
    Takes a list of kafka brokers, will block the execution until the brokers are available
    The criterea of the broker availabily, is that KafkaAdminClient can connect to it, and validate the creation of a topic
    
    """
    for item in kafka_brokers_list:
        _ = test_connection_until_success(item)

    
    logger.info('The following brokers have been connected {}'.format(kafka_brokers_list))

    

def count_available_brokers(kafka_brokers_list : list[str]) -> int:

    """
    Takes a list of kafka brokers, and will count how many brokers  are available
    The criterea of the broker availabily, is that KafkaAdminClient can connect to it, and validate the creation of a topic
    
    """

    nb_available_brokers = 0

    for item in kafka_brokers_list:
        if test_connection_once(item) == True:
            nb_available_brokers += 1

    return nb_available_brokers
 
