''' for managing database populations
'''

import logging
from typing import Tuple

import random
from random import randint, gauss
from datetime import datetime, timedelta, time

from src.db_model.db_models import Schedule, Driver, Bus, AvaiableSchedule, db

random.seed(0)

log = logging.getLogger(__name__)


# GENERATORS #################################################

def driver_gen(n: int):
    ''' generator function for drivers
        first / last names fetched from: # ? source: https://www.name-generator.org.uk/quick/
    args:
        n: number of items
    yields:
        driver dict
    '''
    NUM_NAMES = n

    FIRST_NAMES = [
        'Salim',
        'Arooj',
        'Kaiden',
        'Rhiana',
        'Kealan',
        'Myron',
        'Korey',
        'Krisha',
        'Irene',
        'Annaliese',
        'Komal',
        'Ayesha',
        'Todd',
        'Elora',
        'Judy',
        'Zane',
        'Arlene',
        'Rose',
        'Herbie',
        'Kean',
        'Dustin',
        'Soraya',
        'Merlin',
        'Zakariah',
        'Danielle',
        'Kasim',
        'Marie',
        'Keely',
        'Toni',
        'Kerys',
        'Orlando',
        'Axl',
        'Harri',
        'Tonisha',
        'Farah',
        'Mikaela',
        'Raymond',
        'Effie',
        'Agnes',
        'Francis',
        'Natan',
        'Amber',
        'Myah',
        'Stefano',
        'Hafsah',
        'Ozan',
        'Loui',
        'Jarred',
        'Claudia',
        'Neo',
        'Shirley',
        'Sameer',
        'Jago',
        'Ridwan',
        'Elisha',
        'Abul',
        'Alishia',
        'April',
        'Kristopher',
        'Harmony',
        'Hibah',
        'Anne',
        'Angel',
        'Calista',
        'Dina',
        'Kacie',
        'Maggie',
        'Miriam',
        'Catrin',
        'Elize',
        'Clementine',
        'Antonina',
        'Jamaal',
        'Affan',
        'Lexie',
        'Donnell',
        'Rafe',
        'Kristian',
        'Olivia',
        'Yusra',
        'Bronwyn',
        'Joel',
        'Yvie',
        'Beatrix',
        'Millie',
        'Margie',
        'Eva',
        'Jacque',
        'Collette',
        'Roscoe',
        'Esmee',
        'Skyla',
        'Gino',
        'Tim',
        'Ianis',
        'Jamie',
        'Haider',
        'Vinny',
        'Ceara',
        'Kaelan',
    ]

    LAST_NAMES = [
        'Cowan',
        'Knights',
        'Carpenter',
        'Mahoney',
        'Jarvis',
        'Sinclair',
        'Wilson',
        'Nunez',
        'Lowry',
        'Robson',
        'Coffey',
        'Cabrera',
        'Glover',
        'Brett',
        'Whitaker',
        'Ramirez',
        'Novak',
        'Trujillo',
        'Mcbride',
        'Tapia',
        'Sellers',
        'Maddox',
        'Barker',
        'Silva',
        'Stephens',
        'Palmer',
        'Branch',
        'Lane',
        'Tierney',
        'Griffiths',
        'Perez',
        'Howells',
        'Smart',
        'Drew',
        'Robinson',
        'Peters',
        'Hutchings',
        'Couch',
        'Grimes',
        'Nicholson',
        'Lott',
        'Watson',
        'Davison',
        'Knox',
        'Shields',
        'Fields',
        'Noble',
        'Dominguez',
        'Haney',
        'Fry',
        'Pope',
        'Kenny',
        'Brook',
        'Mccaffrey',
        'Harding',
        'Irving',
        'Bradshaw',
        'Salt',
        'Crouch',
        'Taylor',
        'Acevedo',
        'Velazquez',
        'Chester',
        'Salgado',
        'Frey',
        'Barron',
        'Tait',
        'Callaghan',
        'Wilkinson',
        'Millington',
        'Tate',
        'Dennis',
        'Cardenas',
        'Riddle',
        'Gonzalez',
        'Valencia',
        'Kinney',
        'Akhtar',
        'Zuniga',
        'Mcintosh',
        'Hardin',
        'Whitehouse',
        'Eaton',
        'Edwards',
        'Levine',
        'Grey',
        'Hoover',
        'Blundell',
        'Lowe',
        'Lugo',
        'Valentine',
        'Townsend',
        'Mcconnell',
        'Yoder',
        'Keeling',
        'Kaiser',
        'Morrow',
        'Lovell',
        'Medina',
        'Webster',
    ]

    DOMAIN_NAMES = [
        'hotmail.com',
        'gmail.com',
        'oracle.com',
        'amazon.com',
        'yahoo.ca'
    ]

    len_fn, len_ln = len(FIRST_NAMES), len(LAST_NAMES)
    len_dn = len(DOMAIN_NAMES)

    seen_names = set()

    for i in range(NUM_NAMES):
        fn = FIRST_NAMES[randint(0, len_fn - 1)]
        ln = LAST_NAMES[randint(0, len_ln - 1)]

        while (fn, ln) in seen_names:
            fn = FIRST_NAMES[randint(0, len_fn - 1)]
            ln = LAST_NAMES[randint(0, len_ln - 1)]

        seen_names.add((fn, ln))
        email = f'{fn[:5].lower()}.{ln.lower()}_{randint(10, 99)}@{DOMAIN_NAMES[randint(0, len_dn - 1)]}'
        ssn = f'{i:0>10}'
        yield {
            'first_name': fn,
            'last_name': ln,
            'email': email,
            'social_security_number': ssn,
        }


def bus_gen(n: int):
    ''' generator function for buses
    args:
        n: number of items
    yields:
        bus dict
    '''
    NUM_BUSES = n

    make_list = [
        'Audi',
        'Bentley',
        'BMW',
        'Bugatti',
        'Cadillac',
        'Chevrolet',
        'Chrysler'
    ]

    model_list = [f'{i:0>2}' for i in range(1, 51)]

    len_make, len_model = len(make_list), len(model_list)

    seen_buses = set()
    for i in range(NUM_BUSES):
        make = make_list[randint(0, len_make - 1)]
        model = f'{make[:2]}-{model_list[randint(0, len_model - 1)]}'

        while (make, model) in seen_buses:
            make = make_list[randint(0, len_make - 1)]
            model = f'{make[:2]}-{model_list[randint(0, len_model - 1)]}'

        seen_buses.add((make, model))
        yield {
            'model': model,
            'make': make,
        }


def schedule_gen(dt_start: str, dt_end: str):
    ''' generator function for schedules
    args:
        dt_start: start date str in `DATE_FMT`
        dt_end: end date str in `DATE_FMT`
    yields:
        schedule dict
    '''
    DATE_FMT = '%Y-%m-%d'

    DATE_START_STR, DATE_END_STR = dt_start, dt_end
    TIME_START_STR, TIME_END_STR = '06:00', '22:00'

    DT_START, DT_END = list(map(lambda dt_str: datetime.strptime(dt_str, DATE_FMT), (DATE_START_STR, DATE_END_STR)))
    T_START, T_END = list(map(lambda t: time(*map(int, t.split(':'))), (TIME_START_STR, TIME_END_STR)))

    class BusSchedule():
        ''' schedule for a particular bus.
            determines a random trip_duration, trip_frequency for a given bus_id.
        '''
        def __init__(self, id: int):
            self.id = id

            self.t_start = T_START.replace(                             # bus schedule starting hour
                hour=T_START.hour + abs(int(gauss(0, 0.5))),
                minute=T_START.minute + abs(int(gauss(0, 10)))
            )

            self.time_now = self.t_start                                # running time
            self.trip_duration = timedelta(minutes=randint(20, 60))     # duration of bus trip

            self.every = self.trip_duration + timedelta(                # trip period
                hours=abs(int(gauss(0, 0.4))),
                minutes=randint(20, 40)
            )

        def reset(self): self.time_now = self.t_start

        def __repr__(self):
            return f'@{self.t_start} [{self.trip_duration}] every {self.every}'

        def __str__(self): return repr(self)

        def __dict__(self):
            return {
                'id': self.id,
                't_start': self.t_start,
                'trip_duration': self.trip_duration,
                'every': self.every,
            }

    def add_delta_2_time(time: time, td: timedelta) -> time:
        ''' add timedelta to time objects
        return:
            result time
        '''
        start = datetime(2000, 1, 1, time.hour, time.minute, time.second)
        end = start + td
        return end.time()

    # * QUERY BUSES
    all_buses = Bus.query.all()
    bus_dicts = [bus.as_dict() for bus in all_buses]
    for bus in bus_dicts:
        bus['sched'] = BusSchedule(id=bus['id'])

    # * QUEY DRIVERS
    drivers_in_db = Driver.query.all()
    drivers_dicts = [driver.as_dict() for driver in drivers_in_db]

    today = DT_START
    while today < DT_END:
        today += timedelta(days=1)

        # * GENERATE SCHEDULES FOR EVERY DAY
        for bus in bus_dicts:
            time_now: time = bus['sched'].time_now
            every: timedelta = bus['sched'].every
            trip_duration: time = bus['sched'].trip_duration

            # * KEEP GENERATING SO LONG AS TIME IS INBOUND
            while time_now < T_END:
                time_now = add_delta_2_time(time_now, every)
                bus['sched'].time_now = time_now

                dt_today = datetime(today.year, today.month, today.day, time_now.hour, time_now.minute)
                time_end = add_delta_2_time(dt_today, trip_duration)
                dt_end = datetime(today.year, today.month, today.day, time_end.hour, time_end.minute)

                driver_i = drivers_dicts.pop(0)     # pop driver for this slot

                yield {
                    'driver_id': driver_i['id'],
                    'bus_id': bus['id'],
                    'dt_start': dt_today,
                    'dt_end': dt_end
                }
                drivers_dicts.append(driver_i)      # driver is returned to deque
            bus['sched'].reset()                    # when bus finished the day, restart running hour


# POPULATE ###################################################

def populate_drivers(n: int = 1000):
    ''' populcate database with n drivers
    '''
    for driver in driver_gen(n):
        db.session.add(Driver(**driver))
    db.session.commit()


def populate_buses(n: int = 250):
    ''' populcate database with n buses
    '''
    for bus in bus_gen(n):
        db.session.add(Bus(**bus))
    db.session.commit()


def populate_schedules(dt_start: str, dt_end: str) -> Tuple[int]:
    ''' populcate database with schedules from dt_start until dt_end.
        here populate Schedules and AvailableSchedules.
        `EMPTY_SLOT_CHANCE` determines if the schedule makes it inside Schedules or AvailableSchedules table.
        this way we may have a table of available schedules so we may add them to the
        schedules table without having to worry about conflicts.
    args:
        dt_start: start date str following DATE_FMT = '%Y-%m-%d'
        dt_end: end date str follwing DATE_FMT = '%Y-%m-%d'
    returns:
        number of schedules added, number of schedules available
    '''
    EMPTY_SLOT_CHANCE = 0.10
    num_scheds, num_available_scheds = 0, 0
    for schedule in schedule_gen(dt_start, dt_end):
        if random.random() > EMPTY_SLOT_CHANCE:
            db.session.add(Schedule(**schedule))
            num_scheds += 1
        else:
            db.session.add(AvaiableSchedule(**schedule))
            num_available_scheds += 1

    db.session.commit()
    return num_scheds, num_available_scheds


def delete_all():
    ''' delete all rows in all tables
    '''
    log.info('deleting Schedule table ...')
    Schedule.query.delete()

    log.info('deleting Available_Schedule table ...')
    AvaiableSchedule.query.delete()

    log.info('deleting Bus table ...')
    Bus.query.delete()

    log.info('deleting Driver table ...')
    Driver.query.delete()

    db.session.commit()


if __name__ == '__main__':
    from src import create_app

    app = create_app(conf='volume/config/flask.yml')
    db.create_all()
    delete_all()

    log.info('populating Bus table ...')
    populate_buses(5)

    log.info('populating Driver table ...')
    populate_drivers(10)

    log.info('populating Schedule & Available_Schedule tables ...')
    populate_schedules(dt_start='2022-01-01', dt_end='2022-02-01')

    log.info('done')
    # delete_all()
    # db.drop_all()
