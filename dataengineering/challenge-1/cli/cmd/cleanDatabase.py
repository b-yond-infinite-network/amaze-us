from cli.db import Session, engine, Base

def clean_database():
    try:
        print("Cleaning database ")
        Base.metadata.drop_all(engine)
    except Exception as e :
        print(e)
