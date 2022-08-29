from datetime import date

import pytest

from app.utils import date_util


@pytest.mark.parametrize("dt, start_dt, end_dt", [
    [date.fromisoformat("2022-08-27"), date.fromisoformat("2022-08-22"), date.fromisoformat("2022-08-26")],
    [date.fromisoformat("2022-08-17"), date.fromisoformat("2022-08-15"), date.fromisoformat("2022-08-19")],
    [date.fromisoformat("2022-08-22"), date.fromisoformat("2022-08-22"), date.fromisoformat("2022-08-26")]
])
def test_get_wk_date(dt, start_dt, end_dt):
    start, end = date_util.get_start_and_end_wk_dt(dt)
    assert start == start_dt
    assert end == end_dt
