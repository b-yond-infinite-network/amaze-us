from datetime import datetime, timedelta

from fastapi import Body, APIRouter, Depends, HTTPException, Security, status
from fastapi.security import OAuth2PasswordRequestForm
from sqlalchemy.orm import Session

from .auth.authentication import (
    authenticate_user,
    create_access_token,
    get_current_user
)
from .db import schemas
from .db.crud import get_buses, get_drivers, get_top_drivers
from .db.database import get_db
from .utils import settings


router = APIRouter(prefix='/api')


@router.post('/token', response_model=schemas.Token, status_code=201)
async def login_for_access_token(
    form_data: OAuth2PasswordRequestForm = Depends(),
    db: Session = Depends(get_db)
):
    '''
    OAuth2 based authentication method.

    Input format:
    {
        username: str
        password: str
    }

    Output format:
    {
        access_token: str,
        token_type: str
    }
    '''
    user = authenticate_user(db, form_data.username, form_data.password)
    if not user:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail='Incorrect username or password',
            headers={'WWW-Authenticate': 'Bearer'},
        )
    access_token_expires = timedelta(
        minutes=int(settings['ACCESS_TOKEN_EXPIRE_MINUTES'])
    )
    access_token = create_access_token(
        data={
            'sub': user.email,
            'scopes': [user.scope]
        }, expires_delta=access_token_expires)
    return {'access_token': access_token, 'token_type': 'bearer'}


@router.get('/driver', response_model=list[schemas.Driver])
async def fetch_drivers(
    current_user: schemas.User = Security(
        get_current_user, scopes=['manager', 'employee']),
    db: Session = Depends(get_db)
):
    '''
    Method to retrieve all existing drivers from the database.

    Output format:
    [
        {
            id: int
            first_name: str
            last_name: str
            ssn: str
            email: str
        }
    ]
    '''
    return get_drivers(db)


@router.get('/bus', response_model=list[schemas.Bus])
async def fetch_buses(
    current_user: schemas.User = Security(
        get_current_user, scopes=['manager', 'employee']),
    db: Session = Depends(get_db)
):
    '''
    Method to retrieve all existing buses from the database.

    Output format:
    [
        {
            id: int
            capacity: int
            make: str
            model: str
        }
    ]
    '''
    return get_buses(db)


@router.get('/driver/top', response_model=list[schemas.DriverSummary])
async def fetch_top_drivers(
    start: datetime,
    end: datetime,
    n: int,
    current_user: schemas.User = Security(
        get_current_user, scopes=['manager', 'employee']),
    db: Session = Depends(get_db)
):
    '''
    Method to retrieve the top n drivers from the database, between dates
    start and end.

    Input format:
    {
      start: datetime,
      end: datetime,
      n: int,
    }

    Output format:
    [
        {
            first_name: str
            last_name: str
            total_tasks: int
            total_distance: int
        }
    ]
    '''
    results = get_top_drivers(db, start, end, n)

    output = []
    for result in results:
        output.append(schemas.DriverSummary(
            first_name=result.first_name,
            last_name=result.last_name,
            total_tasks=result.total_tasks,
            total_distance=result.total_distance
        ))

    return output
