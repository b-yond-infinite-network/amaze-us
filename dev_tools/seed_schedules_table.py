import math
import os
import random
import sqlite3

import pandas
import pendulum

schedules_count = 1000000

async def seed_schedules_table(buses, drivers):
    today = pendulum.today()
    shifts = pandas.date_range(start=str(today.subtract(months=3)), end=str(today),
                               freq='8H')

    buses_per_shift = math.ceil(schedules_count/(len(shifts) - 1))gi
    schedules = []

    for i in range(0, len(shifts) - 1):
        begin = shifts[i].to_pydatetime()
        end = shifts[i + 1].to_pydatetime()

        choosen_drivers = random.sample(drivers, buses_per_shift)
        choosen_buses = random.sample(buses, buses_per_shift)

        schedules.extend([
           {"driver": driver.id, "bus": bus.id, "begin": begin, "end": end} for driver, bus in zip(choosen_drivers, choosen_buses)
        ])

    database = sqlite3.connect(os.getenv('DB_FILE_NAME'))
    cursor = database.cursor()
    cursor.executemany("""
        INSERT INTO schedules (driver, bus, begin, end)
        VALUES (:driver, :bus, :begin, :end)
    """, schedules)
    database.commit()

    return schedules
