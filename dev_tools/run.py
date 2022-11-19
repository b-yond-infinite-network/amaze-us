import asyncio
import datetime
import json
import jwt
import secrets

import typer
from dev_tools.initialize_database import initialize_database

app = typer.Typer()

def get_jwt_secret():
    secrets_file = open('jwt.json')
    jwt_data = json.load(secrets_file)
    secret_key = jwt_data.get('secret')
    algorithm = jwt_data.get('algorithm')
    secrets_file.close()

    return {
        "secret_key": secret_key,
        "algorithm": algorithm
    }

@app.command()
def rotate_jwt_secret():
    print('Rotating JWT secret')
    with open('jwt.json', 'w+') as f:
        data = {
            "secret": secrets.token_urlsafe(32),
            "algorithm": "HS256"
        }
        f.seek(0)
        json.dump(data, f, indent=4)
        f.truncate()

@app.command()
def init_database():
    asyncio.run(initialize_database())

@app.command()
def create_manager_jwt():
    secrets = get_jwt_secret()
    token = jwt.encode({
        "exp": datetime.datetime.now() + datetime.timedelta(hours=2),
        "roles": ["manager", "employee"]
    }, secrets["secret_key"], algorithm=secrets["algorithm"])
    print(f"Bearer {token.decode()}")

@app.command()
def create_employee_jwt():
    secrets = get_jwt_secret()
    token = jwt.encode({
        "exp": datetime.datetime.now() + datetime.timedelta(minutes=30),
        "roles": ["employee"]
    }, secrets["secret_key"], algorithm=secrets["algorithm"])
    print(f"Bearer {token.decode()}")


if __name__ == "__main__":
    app()
