import pandas as pd
from cli.db import Session, engine, Base
from cli.models.Athletes import Athletes
from cli.models.Countries import Countries
from cli.models.Country_stats import Country_stats
from cli.models.Summer_games import Summer_games
from cli.models.Winter_games import Winter_games
import sys
import os

def load_from_csv(file_name): 
    print("el nombre es ",os.path.splitext(os.path.basename(file_name))[0])
    model = os.path.splitext(os.path.basename(file_name))[0]
    Base.metadata.create_all(engine)

    print("Loading ",model, " from ", file_name)
    session = Session()
    try:
        data = pd.read_csv(file_name)     
        data = data.where(pd.notnull(data),None)  
        
        #Cleaning duplicate athletes
        if model == 'athletes':    
            data.sort_values("age", inplace = True)
            data.drop_duplicates(subset ="id",keep = 'last', inplace = True)

        for i, row in data.iterrows():
            if model == 'athletes':
                athletes = Athletes(row['id'],row['name'],row['gender'],row['age'],row['height'],row['weight'])
                session.add(athletes)
            elif model == 'countries':
                countries = Countries(row['id'],row['country'],row['region'])
                session.add(countries)
            elif model == 'country_stats':
                country_stats = Country_stats(row['year'],row['country_id'],row['gdp'],row['population'],row['nobel_prize_winners'])
                session.add(country_stats)
            elif model == 'summer_games':
                summer_games = Summer_games(row['sport'],row['event'],row['year'],row['athlete_id'],row['country_id'],row['medal'])
                session.add(summer_games)
            elif model == 'winter_games':
                winter_games = Winter_games(row['sport'],row['event'],row['year'],row['athlete_id'],row['country_id'],row['medal'])
                session.add(winter_games)                

        session.commit()
    except Exception as e :
        print(e)
        session.rollback()
    finally:
        session.close()


    
