from cli.db import Session, engine, Base

#ORM
from sqlalchemy import Column, String, Integer, Date, Float, CHAR
 
class Country_stats(Base):
    __tablename__ = 'country_stats'
 
    id = Column(Integer, autoincrement=True, primary_key=True)
    year = Column(Date)
    country_id = Column(Integer)
    gdp = Column(Float)
    population = Column(Integer)
    nobel_prize_winners = Column(Integer)
 
    def __init__(self, id, year, country_id , gdp, population , nobel_prize_winners):
        self.id = id
        self.year = year
        self.country_id = country_id
        self.gdp = gdp
        self.population = population
        self.nobel_prize_winners = nobel_prize_winners