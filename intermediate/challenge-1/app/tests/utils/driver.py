from sqlalchemy.orm import Session

import crud
import models
from schemas.driver import DriverCreate
from tests.utils.utils import random_lower_string, random_email, random_ssn


def create_random_driver(db: Session) -> models.Driver:
    first_name = random_lower_string()
    last_name = random_lower_string()
    email = random_email()
    ssn = random_ssn()
    driver_in = DriverCreate(first_name=first_name, last_name=last_name, email=email, ssn=ssn)
    return crud.driver.create(db=db, obj_in=driver_in)
