import os
import pymysql
from flask import Flask, Markup, render_template


"""os.environ["DB_HOST"] = "localhost"
os.environ["DB_USER"] = "root"
os.environ["DB_NAME"] = "evil_tweets"
os.environ["DB_PASSWORD"] = "evil"
"""

database_host = os.environ.get("DB_HOST")
database_user = os.environ.get("DB_USER")
database_name = os.environ.get("DB_NAME")
database_password = os.environ.get("DB_PASSWORD")


########### SQL QUERIES 
query_cities = "SELECT DISTINCT place FROM tweets ORDER BY place"

query_trend = "SELECT CONVERT_TZ(time, 'UTC', 'America/New_York') as start_time,place,sum(`count`) as total \
                FROM {} \
                WHERE time > DATE_SUB(UTC_TIMESTAMP(), INTERVAL 1 HOUR) \
                AND place = '{}' \
                GROUP BY place,time \
                ORDER BY place,time"

query_ranking = "SELECT place,sum(`count`) as total \
                FROM {} \
                WHERE time > DATE_SUB(UTC_TIMESTAMP(), INTERVAL 1 HOUR) \
                GROUP BY place \
                ORDER BY total"

###########

app = Flask(__name__)


connection = pymysql.connect(host=database_host,
                             user=database_user,
                             password=database_password,
                             database=database_name,
                             cursorclass=pymysql.cursors.DictCursor)
connection.autocommit(True)


def get_key_values_from_result(result, key):
    values = [d[key] for d in result]
    return values
    
def query_database(query):
    with connection.cursor() as cursor:
        # Read a single record
        cursor.execute(query)
        result = cursor.fetchall()
    return result


@app.route('/topfive')
def topfive():
    result = query_database(query=query_ranking.format("tweets"))
    labels = get_key_values_from_result(result, "place")[-5:]
    values = get_key_values_from_result(result, "total")[-5:]
    return render_template('bar_chart.html',
                           title=f'Top 5 Cities based on #tweets',
                           max=max(values)+1,
                           labels=labels,
                           values=values)

@app.route('/worstfive')
def worstfive():
    result = query_database(query=query_ranking.format("retweets"))
    labels = get_key_values_from_result(result, "place")[:5]
    values = get_key_values_from_result(result, "total")[:5]
    return render_template('bar_chart.html',
                           title=f'Worst 5 Cities based on #retweets',
                           max=max(values)+1,
                           labels=labels,
                           values=values)

@app.route('/metrics/tweets/<city>')
def tweet_count(city):
    result = query_database(query=query_trend.format("tweets", city))
    labels = get_key_values_from_result(result, "start_time")
    values = get_key_values_from_result(result, "total")
    return render_template('line_chart.html',
                           title=f'Total tweets over 15 minutes: ({city})',
                           max=max(values)+1,
                           labels=labels,
                           values=values)

@app.route('/metrics/retweets/<city>')
def retweet_count(city):
    result = query_database(query=query_trend.format("retweets", city))
    labels = get_key_values_from_result(result, "start_time")
    values = get_key_values_from_result(result, "total")
    return render_template('line_chart.html',
                           title=f'Total retweets over 15 minutes: ({city})',
                           max=max(values),
                           labels=labels,
                           values=values)

@app.route('/metrics/users/<city>')
def user_count(city):
    result = query_database(query=query_trend.format("users", city))
    labels = get_key_values_from_result(result, "start_time")
    values = get_key_values_from_result(result, "total")
    return render_template('line_chart.html',
                           title=f'Total active tweeters over 15 minutes: ({city})',
                           max=max(values),
                           labels=labels,
                           values=values)

@app.route("/")
def menu():
    cities = query_database(query=query_cities)
    cities = get_key_values_from_result(cities, "place")
    return render_template('menu.html', cities=cities)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)