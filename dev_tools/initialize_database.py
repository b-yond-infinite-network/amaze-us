import asyncio
import os
import sqlalchemy
from faker import Faker
from faker_vehicle import VehicleProvider

from .seed_buses_table import seed_buses_table
from .seed_drivers_table import seed_drivers_table
from .seed_schedules_table import seed_schedules_table
from src import main


async def initialize_database():
    engine = sqlalchemy.create_engine(os.getenv('DB_PATH'))
    main.metadata.drop_all(engine)
    main.metadata.create_all(engine)

    fake = Faker()
    fake.add_provider(VehicleProvider)

    print('Seeding Buses Table')
    buses = await seed_buses_table(fake)

    print('Seeding Drivers Table')
    drivers = await seed_drivers_table(fake)

    print('Seeding Schedules Table')
    await seed_schedules_table(buses, drivers)

asyncio.run(initialize_database())