from cli.cmd.loadFromCsv import load_from_csv
from cli.cmd.clearDatabase import clear_database


clear_database()
load_from_csv("./dataset/athletes.csv")
load_from_csv("./dataset/countries.csv")
load_from_csv("./dataset/country_stats.csv")
load_from_csv("./dataset/summer_games.csv")
load_from_csv("./dataset/winter_games.csv")