from datetime import date, timedelta


def get_start_and_end_wk_dt(dt: date) -> tuple:
    """
    Get the start and end of the week based on a date.
    :param dt: a date.
    :return: start and end wk date.
    """

    start_dt = dt - timedelta(days=dt.weekday())
    end_dt = start_dt + timedelta(days=4)
    return start_dt, end_dt
