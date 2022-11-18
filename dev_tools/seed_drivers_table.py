from src.model import Driver

unique_drivers_count = 10000

async def seed_drivers_table(fake):
    drivers = [Driver(
        id=i,
        first_name=fake.first_name(),
        last_name=fake.last_name(),
        social_security_number=fake.iban(),
        email=fake.email()
    ) for i in range(0, unique_drivers_count)]

    await Driver.objects.bulk_create(drivers)

    return drivers
