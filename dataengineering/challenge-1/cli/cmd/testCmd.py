from cli.cmd.loadFromCsv import load_from_csv
from cli.cmd.cleanDatabase import clean_database


clean_database()
load_from_csv("./dataset/athletes.csv",'athletes')
load_from_csv("./dataset/countries.csv",'countries')
load_from_csv("./dataset/country_stats.csv",'country_stats')
load_from_csv("./dataset/summer_games.csv",'summer_games')
load_from_csv("./dataset/winter_games.csv",'winter_games')