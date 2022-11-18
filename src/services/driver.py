from datetime import datetime, date, timedelta

from src.model import Driver, Schedule

class DriverService(object):
    async def get_with_schedules_by_week(self, driver_id: int, day_of_the_week: date):
        first_day_of_the_week = day_of_the_week - timedelta(days=day_of_the_week.weekday() + 1)
        last_day_of_the_week = first_day_of_the_week + timedelta(days=7)

        driver = await Driver.objects.get_or_none(id=driver_id)
        driver.schedules = await Schedule.objects.filter(driver=driver, begin__gte=first_day_of_the_week, end__lt=last_day_of_the_week).select_related(Schedule.bus).all()

        return driver

    async def get_top_drivers(self, weekDateBegin: date, weekDateEnd: date):
        first_day = weekDateBegin - timedelta(days=weekDateBegin.weekday() + 1)
        last_day = weekDateEnd - timedelta(days=weekDateEnd.weekday() + 1) + timedelta(days=7)

        drivers = await Driver.objects.database.fetch_all("""
            SELECT
                driver.id,
                driver.first_name,
                driver.last_name,
                driver.email,
                driver.social_security_number,
                COUNT(schedule.id) AS schedule_count
            FROM
                driver
            INNER JOIN
                schedule ON driver.id = schedule.driver
            WHERE
                schedule.begin >= :first_day AND schedule.end < :last_day
            GROUP BY
                driver.id
            ORDER BY
                schedule_count DESC
            LIMIT 10
        """, {'first_day': first_day, 'last_day': last_day})

        return drivers
