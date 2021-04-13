from cli.db import Session, engine, Base

#ORM
from sqlalchemy import Column, String, Integer, Date, Float, CHAR
 
class Countries(Base):
    __tablename__ = 'countries'
 
    id = Column(Integer, primary_key=True)
    country = Column(String(100))
    region = Column(String(100))
 
    def __init__(self, id, country, region):
        self.id = id
        self.country = country
        self.region = region