from cli.db import Session, engine, Base

#ORM
from sqlalchemy import Column, String, Integer, Date, Float, CHAR
 
class Country_stats(Base):
    __tablename__ = 'country_stats'
 
    year = Column(Date,primary_key=True)
    country_id = Column(Integer,primary_key=True)
    gdp = Column(Float)
    population = Column(Integer)
    nobel_prize_winners = Column(Integer)
 
    def __init__(self, year, country_id , gdp, population , nobel_prize_winners):
        self.year = year
        self.country_id = country_id
        self.gdp = gdp
        self.population = population
        self.nobel_prize_winners = nobel_prize_winners