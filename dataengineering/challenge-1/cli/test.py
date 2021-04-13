from cli.db import Session, engine, Base

from cli.models.Athletes import Athletes
from cli.models.Countries import Countries
from cli.models.Country_stats import Country_stats
from cli.models.Summer_games import Summer_games
from cli.models.Winter_games import Winter_games


Base.metadata.create_all(engine)

session = Session()

#Test Athletes
#id,name,gender,age,height,weight
antonio = Athletes(928, "Antonio Abadia Beci", 'M', 26, 170, 65)
session.add(antonio)

#Test Countries
#id,country,region
countries = Countries(1, "	AFG - Afghanistan", "ASIA (EX. NEAR EAST)")
session.add(countries)

#Test Country_stats
#id - ,year,country_id,gdp,population,nobel_prize_winners
country_stats = Country_stats("2000-01-01",2,3632043908, 3089027,None)
session.add(country_stats)

#Test Summer_games
#id - ,sport,event,year,athlete_id,country_id, medal
summer_games = Summer_games("Gymnastics", "Gymnastics Men's Individual All-Around", "2016-01-01",51, 173,None )
session.add(summer_games)

#Test Winter_games
#id -, sport,event,year,athlete_id,country_id, medal
winter_games = Winter_games("Gymnastics", "Gymnastics Men's Individual All-Around", "2016-01-01",51, 173, None)
session.add(winter_games)

session.commit()
session.close()