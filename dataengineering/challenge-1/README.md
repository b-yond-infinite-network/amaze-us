# Challenge 1 - One moment in time

We enjoy watching major competitions such as the Olympics, eager to see the winners and if our conuntry is participating we go crazy and start counting medals.
Sports brings us together, unfortunately the data is not.

Bring us together by providing some stats on the given dataset.
Use the stack of your choice; and make it happen!

## Excercises
1. Take the datasets on the dataset folder and build your data model
2. Create a base report querying the summer games showing the total number of athletes of the top 3 sports 
2. Create a report that shows every sport's number of unique events and unique athletes
3. Create a report that shows the age of the oldest athlete by region
4. Create a report that shows the unique number of events held for each sport on both winter and summer games, and order them from the most number of events to the least number of events.
5. Validation:  
      - Pull total_bronze_medals from summer_games
      - Create a subquery that shows the total number of bronze medals by country
      - Both queries should have the same output
6. Now that you have all your data in place, please share any other interesting insights from the data analysis you conducted (whatever you think is interesting and/or important)

      
## Expected steps
+ Create a branch of this project (or fork it in your github account if you prefer)
+ Do you **_thang_** inside this folder (challenge-1)
+ Push your change inside a Pull Request to our master

# Challenge 1 - Solution

The challenge was solved in two parts. 
* *Part 1* includes the offline analysis of the data using Jupyter Notebooks. 
* *Part 2* is a dockerized project consisting of a MySQL database and a CLI. *Part 2* is meant to be used for a production environment. 

## Part 1:

### Prerequisites
To run the Jupyter notebook you will need:
- Jupyter Notebook
- pandas
- numpy
- seaborn
- matplotlib

### Start
Open file **olympic_analysis.ipynb** in **notebooks** folder with Jupyter Notebook.


## Part 2:

### Prerequisites
- docker-compose 

[Install docker compose](https://docs.docker.com/compose/install/) 

### Start

Create and start the dockerized project in a termina. These commands will start 2 docker containers: one for the MySQL DB and one for the CLI.
```
	cd amaze-us/dataengineering/challenge-1
	docker-compose up --build
```

Open a new terminal and execute the following commands:

**To clean the MySQL database** (optional, run when database is full)
```
docker exec -it cli actions clear_db
```

**To load the .csv files to the database**
```
docker exec -it cli actions load_csv /dataset/athletes.csv
docker exec -it cli actions load_csv /dataset/country_stats.csv
docker exec -it cli actions load_csv /dataset/countries.csv
docker exec -it cli actions load_csv /dataset/winter_games.csv
docker exec -it cli actions load_csv /dataset/summer_games.csv
```
**To generate .xls reports**
```
docker exec -it cli actions generate_reports

```

The reports are generated in the folder **cli-reports**