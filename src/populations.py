''' for managing database populations
'''

import logging
from typing import Tuple, Dict

import random
from random import randint, gauss
from datetime import datetime, timedelta, time

from src.common import DATE_FMT
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
    DATE_START_STR, DATE_END_STR = dt_start, dt_end
    TIME_START_STR, TIME_END_STR = '06:00', '22:00'

    DT_START, DT_END = list(map(lambda dt_str: datetime.strptime(dt_str, DATE_FMT), (DATE_START_STR, DATE_END_STR)))
    T_START, T_END = list(map(lambda t: time(*map(int, t.split(':'))), (TIME_START_STR, TIME_END_STR)))

    def add_delta_2_time(time: time, td: timedelta) -> time:
            ''' add timedelta to time objects
            return:
                result time
            '''
            start = datetime(2000, 1, 1, time.hour, time.minute, time.second)
            end = start + td
            return end.time()

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

            self.day_slots = self.daily_schedule()

        def daily_schedule(self) -> list:
            curr_time = self.t_start
            all_slots = []
            while curr_time < T_END:
                t_end_trip = add_delta_2_time(curr_time, self.trip_duration)
                all_slots.append((curr_time, t_end_trip))
                curr_time = add_delta_2_time(curr_time, self.every)

            return all_slots

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

 
    # * QUERY BUSES
    db_bus_ids = [_id[0] for _id in Bus.query.with_entities(Bus.id).all()]

    # * QUERY DRIVERS
    db_drivers = Driver.query.all()

    # * PREDETERMINE SCHEDULE DT SHIFTS
    bus_scheds: Dict[int, BusSchedule] = {}
    for bus_id in db_bus_ids:
        bus_scheds[bus_id] = BusSchedule(id=bus_id)

    today = DT_START
    while today < DT_END:
        for bus_id, sched in bus_scheds.items():
            for t_start, t_end in sched.day_slots:
                dt_today = datetime(today.year, today.month, today.day, t_start.hour, t_start.minute)
                dt_end = datetime(today.year, today.month, today.day, t_end.hour, t_end.minute)
                driver_i = db_drivers.pop(0)

                yield {
                    'driver_id': driver_i.id,
                    'bus_id': bus_id,
                    'dt_start': dt_today,
                    'dt_end': dt_end
                }
                db_drivers.append(driver_i)     # append driver back in front

        today += timedelta(days=1)


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

    def get_next_month(dt: datetime) -> datetime:
        ''' get next month dt
        '''
        try:
            return dt.replace(month=dt.month + 1, day=1)
        except ValueError:
            return dt.replace(year=dt.year + 1, month=1 , day=1)

    def get_month_slots(dt_start: str, dt_end: str) -> list[datetime, datetime]:
        ''' split datetime window into monthly windows
        '''
        dt_start, dt_end = list(map(
            lambda dt: datetime.strptime(dt, DATE_FMT),
            (dt_start, dt_end)
        ))

        month_slots = []
        curr_dt = dt_start
        next_dt = get_next_month(curr_dt)
        while next_dt < dt_end:
            month_slots.append((curr_dt, next_dt))
            curr_dt, next_dt = next_dt, get_next_month(next_dt)

        if curr_dt < dt_end:
            month_slots.append((curr_dt, dt_end))

        str_slots = list(map(
            lambda slot: [datetime.strftime(slot[0], DATE_FMT), datetime.strftime(slot[1], DATE_FMT)],
            month_slots
        ))
        return str_slots

    # * IN CASE POPULATING MILLIONS OF SCHEDS TAKES LONG, SPLIT THE TIME WINDOW INTO MONTHS
    # * AND KEEP LOGGING FOR FEEDBACK SO USER DOES NOT GET BORED ...

    dt_slots = get_month_slots(dt_start, dt_end)
    for i, (dt_s, dt_e) in enumerate(dt_slots):
        log.info(f'---{i + 1:>3}/{len(dt_slots):<3} populating from {dt_s} to {dt_e} ...')
        for schedule in schedule_gen(dt_s, dt_e):
            if random.random() > EMPTY_SLOT_CHANCE:
                db.session.add(Schedule(**schedule))
                num_scheds += 1
            else:
                db.session.add(AvaiableSchedule(**schedule))
                num_available_scheds += 1

        db.session.commit()
        log.info(f'+++ populated from {dt_s} to {dt_e} created:{num_scheds:>10}, avaialable:{num_available_scheds:>10}')

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

    app = create_app(conf='volume/config/flask.yaml')
    db.create_all()
    delete_all()

    log.info('populating Bus table ...')
    populate_buses(5)

    log.info('populating Driver table ...')
    populate_drivers(10)

    log.info('populating Schedule & Available_Schedule tables ...')
    created, available = populate_schedules(dt_start='2022-01-01', dt_end='2022-05-01')
    log.info(f'created, avaialbe: {created}, {available}')

    log.info('done')
    # delete_all()
    # db.drop_all()
