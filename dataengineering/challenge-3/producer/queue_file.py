from asyncio import events
from health_check import count_available_brokers
from setup_logging import logger
from queue import Empty, Queue
from queue import Empty as StdEmpty
from time import sleep  
from ast import literal_eval
import subprocess
from os import system

def check_brokers_and_switch_condition(kafka_brokers_list : list[str], one_broker_is_up , all_brokers_are_down ) -> None:
    """
    Takes broker lists, and two events "one_broker_is_up, all_brokers_are_down" .

    Acts as an event switcher to be run as a seperate and continious thread, will count available brokers, and switch events.
    
    The event acts as a boolean in the While loop in queue_to_file, , will retrun true when it's set, and will hold the execution if it's unset.

    If no brokers are available, it will set all_brokers_are_down and unset one_broker_is_up
    
    If at least one broker is up, it will unset all_brokers_are_down and set one_broker_is_up

    all_brokers_are_down and one_broker_is_up will always be set to have opposit logic( one is set, the other is cleared), 
    technically they have the same meaning, and each will be used as an event trigger in opposit functions.

    The reason they are both used is that event does not return false it will wait, so two events are needed to alternate between functions. 

    """
    while True:
        a = count_available_brokers(kafka_brokers_list)
        
        if a  >= 1:
            one_broker_is_up.set()
            all_brokers_are_down.clear()    
        elif a == 0:
            all_brokers_are_down.set()
            one_broker_is_up.clear()

        logger.debug("check_brokers_and_switch_condition has set all_brokers_are down to: {}".format(all_brokers_are_down.is_set()))


def queue_to_file(queue : Queue , all_brokers_are_down ) -> None:
    """
    Will flush elements of queue to file.
    
    When the event all_brokers_are_down is set, all_brokers_are_down.wait() will return true, and it will allow to enter a batch of of flushing queue to file.

    When all_brokers_are_down is unset, all_brokers_are_down.wait() will wait, and will block the while loop

    """
    while True:

        while all_brokers_are_down.wait():
            logger.debug('Executing 1 batch of flushing queue to file')
            
            if queue.qsize() > 0:
                with open('file.txt', 'a+', encoding="GB18030") as fp:
                    for _ in range(1,queue.qsize()):

                        try:
                            item = queue.get(timeout=2)
                            logger.debug(item)
                        except (StdEmpty,Empty) as e:
                            logger.error(e)
                            break
                        else:
                            fp.write("{}\n".format(item))

                fp.close()
            
            logger.debug('1 batch of flushing queue to file has been executed')


def file_to_queue(queue : Queue, one_broker_is_up) -> None:
    """
    Will read elements from file and add it to queue 

    When the event one_broker_is_up is set, one_broker_is_up.wait() will return true, and it will allow to enter a batch of of flushing queue to file.
    """

    while True:

        while one_broker_is_up.wait():

            sleep(3)
            number_of_lines = int(subprocess.getoutput('wc -l < file.txt'))


            if number_of_lines > 0:
                
                with open('file.txt', 'r+',encoding="GB18030") as file_pointer:
                    try:
                        for _ in range(0,number_of_lines):
                            a= file_pointer.readline()
                            logger.debug("Tweet from file to queue : {}".format(a))
                            queue.put(literal_eval(a),block=True,timeout=1)
                    except Exception as msg:
                        logger.error("file_to_queue error message :{}".format(msg))
    
                    system("sed -i '1,{}d' file.txt".format(number_of_lines))
                    