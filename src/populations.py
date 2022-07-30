import logging

import random
from random import randint, gauss
from datetime import datetime, timedelta, time

from src.db_model.db_models import Schedule, Driver, Bus, AvaiableSchedule, db

random.seed(0)

log = logging.getLogger(__name__)


# GENERATORS #################################################

def driver_gen(n: int):
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


def schedule_gen(dt_start: str = '2022-01-01', dt_end: str = '2022-12-01'):
    DATE_FMT = '%Y-%m-%d'

    DATE_START_STR, DATE_END_STR = dt_start, dt_end
    TIME_START_STR, TIME_END_STR = '06:00', '22:00'

    DT_START, DT_END = list(map(lambda dt_str: datetime.strptime(dt_str, DATE_FMT), (DATE_START_STR, DATE_END_STR)))
    T_START, T_END = list(map(lambda t: time(*map(int, t.split(':'))), (TIME_START_STR, TIME_END_STR)))

    class BusSchedule():
        def __init__(self, id: int):
            self.id = id

            self.t_start = T_START.replace(
                hour=T_START.hour + abs(int(gauss(0, 0.5))),
                minute=T_START.minute + abs(int(gauss(0, 10)))
            )

            self.time_now = self.t_start

            self.trip_duration = timedelta(minutes=randint(20, 60))
            self.every = self.trip_duration + timedelta(
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

    def add_delta_2_time(time: time, td: timedelta):
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

        for bus in bus_dicts:
            time_now: time = bus['sched'].time_now
            every: timedelta = bus['sched'].every
            trip_duration: time = bus['sched'].trip_duration

            while time_now < T_END:
                time_now = add_delta_2_time(time_now, every)
                dt_today = datetime(today.year, today.month, today.day, time_now.hour, time_now.minute)
                time_end = add_delta_2_time(dt_today, trip_duration)
                dt_end = datetime(today.year, today.month, today.day, time_end.hour, time_end.minute)

                driver_i = drivers_dicts.pop(randint(0, len(drivers_dicts) - 1))

                yield {
                    'driver_id': driver_i['id'],
                    'bus_id': bus['id'],
                    'dt_start': dt_today,
                    'dt_end': dt_end
                }
                drivers_dicts.append(driver_i)
            bus['sched'].reset()


# POPULATE ###################################################

def populate_drivers(n: int = 1000):
    for driver in driver_gen(n):
        db.session.add(Driver(**driver))
    db.session.commit()


def populate_buses(n: int = 250):
    for bus in bus_gen(n):
        db.session.add(Bus(**bus))
    db.session.commit()


def populate_schedules(n: int = int(1e6)):
    EMPTY_SLOT_CHANCE = 0.10
    for schedule in schedule_gen(dt_start='2022-01-01', dt_end='2022-03-01'):
        if random.random() > EMPTY_SLOT_CHANCE:
            db.session.add(Schedule(**schedule))
        else:
            db.session.add(AvaiableSchedule(**schedule))

    db.session.commit()


if __name__ == '__main__':
    from src import create_app

    app = create_app(config='volume/config/flask.yml')

    log.info('populating Bus table ...')
    populate_buses(25)

    log.info('populating Driver table ...')
    populate_drivers(100)

    log.info('populating Schedule table ...')
    populate_schedules()

    log.info('done')
