from cli.db import Session, engine, Base

#ORM
from sqlalchemy import Column, String, Integer, Date, Float, CHAR
 
class Winter_games(Base):
    __tablename__ = 'winter_games'
 
    sport = Column(String(100))
    event = Column(String(300),primary_key=True)
    year = Column(Date,primary_key=True)
    athlete_id = Column(Integer,primary_key=True)
    country_id = Column(Integer)
    medal = Column(String(100))
 
    def __init__(self,sport, event , year, athlete_id , country_id, medal):
        self.sport = sport
        self.event = event
        self.year = year
        self.athlete_id = athlete_id
        self.country_id = country_id
        self.medal = medal