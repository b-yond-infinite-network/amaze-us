import asyncio
import os
import sqlalchemy
from faker import Faker
from faker_vehicle import VehicleProvider

from dev_tools.seed_bus_table import seed_bus_table
from dev_tools.seed_driver_table import seed_driver_table
from dev_tools.seed_schedule_table import seed_schedule_table
from src import main


async def initialize_database():
    engine = sqlalchemy.create_engine(os.getenv('DB_PATH'))
    main.metadata.drop_all(engine)
    main.metadata.create_all(engine)

    fake = Faker()
    fake.add_provider(VehicleProvider)

    print('Seeding Bus Table')
    buses = await seed_bus_table(fake)

    print('Seeding Driver Table')
    drivers = await seed_driver_table(fake)

    print('Seeding Schedule Table')
    await seed_schedule_table(buses, drivers)

asyncio.run(initialize_database())