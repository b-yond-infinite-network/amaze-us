from flask import Flask,jsonify,request
from flask import render_template
import ast, time, os

from collections import defaultdict

from flaskext.mysql import MySQL
import pymysql

class Metric:
    x = ""
    y = 0

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def toJson(self):
        return {"x": self.x, "y": self.y}

app = Flask(__name__)
app.config['MYSQL_DATABASE_USER'] = os.getenv('DB_USER')
app.config['MYSQL_DATABASE_PASSWORD'] = os.getenv('DB_PASSWORD')
app.config['MYSQL_DATABASE_DB'] = os.getenv('DB_NAME')
app.config['MYSQL_DATABASE_HOST'] = os.getenv('DB_HOST')
mysql = MySQL(
    app, 
    host=os.getenv('DB_HOST'), 
    user=os.getenv('DB_USER'), 
    passwd=os.getenv('DB_PASSWORD'), 
    db=os.getenv('DB_NAME'), 
    autocommit=True, 
    cursorclass=pymysql.cursors.DictCursor
)

RETWEETS_QUERY = """
SELECT CONVERT_TZ(start_date, 'UTC', 'EST') as start_date,location,sum(`total`) as total FROM
`retweets`
WHERE `start_date` > DATE_SUB(UTC_TIMESTAMP(), INTERVAL 1 HOUR)
AND location in ('Toronto','Vancouver','Calgary','Ottawa','Halifax','Montreal')
GROUP BY location,`start_date`
ORDER BY location,`start_date`
"""

TWEETS_QUERY = """
SELECT CONVERT_TZ(start_date, 'UTC', 'EST') as start_date,location,sum(`total`) as total FROM
`tweets`
WHERE `start_date` > DATE_SUB(UTC_TIMESTAMP(), INTERVAL 1 HOUR)
AND location in ('Toronto','Vancouver','Calgary','Ottawa','Halifax','Montreal')
GROUP BY `start_date`,location
ORDER BY location,`start_date`
"""

USERS_QUERY = """
SELECT CONVERT_TZ(start_date, 'UTC', 'EST') as start_date,location,sum(`total`) as total FROM
`users`
WHERE `start_date` > DATE_SUB(UTC_TIMESTAMP(), INTERVAL 1 HOUR)
AND location in ('Toronto','Vancouver','Calgary','Ottawa','Halifax','Montreal')
GROUP BY location,`start_date`
ORDER BY location,`start_date`
"""

TRENDING_CITIES_QUERY = """
SELECT location,sum(`total`) as total FROM
`tweets`
WHERE `start_date` > DATE_SUB(UTC_TIMESTAMP(), INTERVAL 1 HOUR)
GROUP BY location
ORDER BY total desc
LIMIT 5
"""

WORST_CITIES_QUERY = """
SELECT location,sum(`total`) as total FROM
`retweets`
WHERE `start_date` > DATE_SUB(UTC_TIMESTAMP(), INTERVAL 1 HOUR)
GROUP BY location
ORDER BY total desc
LIMIT 5
"""

COLORS = [
    'rgba(255, 99, 132, 0.8)',
    'rgba(54, 162, 235, 0.8)',
    'rgba(255, 206, 86, 0.8)',
    'rgba(75, 192, 192, 0.8)',
    'rgba(153, 102, 255, 0.8)',
    'rgba(255, 159, 64, 0.8)',
    'rgba(255, 99, 132, 0.8)',
    'rgba(54, 162, 235, 0.8)',
    'rgba(255, 206, 86, 0.8)',
    'rgba(75, 192, 192, 0.8)',
    'rgba(153, 102, 255, 0.8)'
]

def get_data(graph):
    graph_type = "line"
    cursor = mysql.get_db().cursor()
    if graph == "chartTotalTweets":
        cursor.execute(TWEETS_QUERY)
    elif graph == "chartTotalUsers":
        cursor.execute(USERS_QUERY)
    elif graph == "chartTotalRetweets":
        cursor.execute(RETWEETS_QUERY)
    elif graph == "chartActiveCities":
        cursor.execute(TRENDING_CITIES_QUERY)
        graph_type = "bar"
    elif graph == "chartWorstCities":
        cursor.execute(WORST_CITIES_QUERY)
        graph_type = "bar"

    records = cursor.fetchall()

    # Format timestamp
    totalers = []
    md = defaultdict(list)

    cursor.close()

    if graph_type == "line":
        datasets = []
        for x in records:
            totalers.append(int(x['total']))
            x['total'] = int(x['total'])
            x['start_date'] = str(x['start_date'])
            md[x['location']].append({'x': str(x['start_date']), 'y': int(x['total'])})

        x = 0
        for key in md.keys():
            datasets.append({"label": key, "data": md[key], "fill": False, "borderColor": COLORS[x % 10]})
            x += 1
        
        return {"datasets": datasets}
    else:
        labels = []
        datasets = []
        for x in records:
            labels.append(x['location'])
            datasets.append(int(x['total']))

        return {
            "labels": labels, 
            "datasets": [{"data": datasets,
                "label": 'total',
                "backgroundColor": [
                	'rgba(255, 99, 132, 0.2)',
                	'rgba(54, 162, 235, 0.2)',
                	'rgba(255, 206, 86, 0.2)',
          	      'rgba(75, 192, 192, 0.2)',
                	'rgba(153, 102, 255, 0.2)',
                	'rgba(255, 159, 64, 0.2)',
                	'rgba(255, 99, 132, 0.2)',
                	'rgba(54, 162, 235, 0.2)',
                	'rgba(255, 206, 86, 0.2)',
                	'rgba(75, 192, 192, 0.2)',
                	'rgba(153, 102, 255, 0.2)'
            	],
            	"borderColor": [
                	'rgba(255,99,132,1)',
                	'rgba(54, 162, 235, 1)',
        	        'rgba(255, 206, 86, 1)',
                	'rgba(75, 192, 192, 1)',
                	'rgba(153, 102, 255, 1)',
                	'rgba(255, 159, 64, 1)',
                	'rgba(255,99,132,1)',
                	'rgba(54, 162, 235, 1)',
                	'rgba(255, 206, 86, 1)',
                	'rgba(75, 192, 192, 1)',
                	'rgba(153, 102, 255, 1)'
            	],
            	"borderWidth": 1
                }]
        }

@app.route("/")
def index():
    return render_template('metrics.html')

@app.route('/data')
def get_graph_data():

    graph = request.args.get('graph')
    return jsonify(get_data(graph))

if __name__ == "__main__":
    app.run(host='0.0.0.0')