from cli.db import Session, engine, Base

#ORM
from sqlalchemy import Column, String, Integer, Date, Float, CHAR
 
class Summer_games(Base):
    __tablename__ = 'summer_games'
 
    id = Column(Integer, autoincrement=True, primary_key=True)
    sport = Column(String(100))
    event = Column(String(300))
    year = Column(Date)
    athlete_id = Column(Integer)
    country_id = Column(Integer)
    medal = Column(String(100))
 
    def __init__(self, id, sport, event , year, athlete_id , country_id, medal):
        self.id = id
        self.sport = sport
        self.event = event
        self.year = year
        self.athlete_id = athlete_id
        self.country_id = country_id
        self.medal = medal