from src.model import Bus

unique_buses_count = 10000

async def seed_bus_table(fake):
    buses = [Bus(id=i, capacity=int(fake.random.uniform(20, 50)),
                model=fake.vehicle_model(),
                make=fake.vehicle_make()) for i in range(0, unique_buses_count)]

    await Bus.objects.bulk_create(buses)

    return buses
