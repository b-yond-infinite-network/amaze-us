from cli.db import Session, engine, Base

#ORM
from sqlalchemy import Column, String, Integer, Date, Float, CHAR
 
class Athletes(Base):
    __tablename__ = 'athletes'
 
    id = Column(Integer, primary_key=True)
    name = Column(String(100))
    gender = Column(CHAR)
    age = Column(Integer)
    height = Column(Integer)
    weight = Column(Integer)
 
    def __init__(self, id, name, gender, age, height, weight):
        self.id = id
        self.name = name
        self.gender = gender
        self.age = age
        self.height = height
        self.weight = weight