#Create a base report querying the summer games showing the total number of athletes of the top 3 sports
SELECT sport, COUNT(DISTINCT athlete_id) FROM `assured-am-dev.tmp.summer_games`
GROUP BY sport;

#Create a report that shows every sport's number of unique events and unique athletes
SELECT sport, event, COUNT(DISTINCT athlete_id) AS total_by_sport_event
FROM (
SELECT * FROM `assured-am-dev.tmp.summer_games`
UNION ALL
SELECT * FROM `assured-am-dev.tmp.winter_games`
)
GROUP BY sport, event
ORDER BY  sport, event
;

#Create a report that shows the age of the oldest athlete by region
WITH athlete_by_country_id as
( SELECT athlete_id, ANY_VALUE(country_id) as country_id FROM `assured-am-dev.tmp.summer_games` GROUP BY athlete_id
UNION ALL SELECT athlete_id, ANY_VALUE(country_id) as country_id FROM `assured-am-dev.tmp.winter_games` GROUP BY athlete_id
),
athlete_by_country as (
SELECT * FROM athlete_by_country_id a
JOIN `assured-am-dev.tmp.countries` c on a.country_id = c.id
),
athlete_info as (
SELECT * FROM athlete_by_country ac
JOIN `assured-am-dev.tmp.athletes` a ON ac.athlete_id = a.id
)
SELECT country, region, MAX(age)
FROM athlete_info
GROUP BY country, region
;

#Create a report that shows the unique number of events held for each sport on both winter and summer games, and order them from the most number of events to the least number of events.
SELECT sport, event, count(*) number_evts
FROM(
    SELECT * FROM `assured-am-dev.tmp.summer_games`
    UNION ALL
    SELECT * FROM `assured-am-dev.tmp.summer_games`
)
GROUP BY sport, event
ORDER BY sport, number_evts DESC
;


/*
Validation:
Pull total_bronze_medals from summer_games
Create a subquery that shows the total number of bronze medals by country
Both queries should have the same output
*/
SELECT SUM(total_bronzes) as total_bronzes, SUM(tot_by_country) as sum_tot_by_country
FROM(
    SELECT count(*) as total_bronzes, 0 as tot_by_country FROM `assured-am-dev.tmp.summer_games`
    WHERE medal = 'Bronze'
    UNION ALL
    SELECT 0 as total_bronzes, SUM(tot_by_country)
    FROM(
        SELECT country, count(*) as tot_by_country FROM `assured-am-dev.tmp.summer_games` s
        JOIN `assured-am-dev.tmp.countries` c ON c.id = s.country_id
        WHERE medal = 'Bronze'
        GROUP BY country
    )
)
;


#Bonus: Get all athletes who won max number of medals grouped by medal for all summer games
WITH medals_by_athlete as(
SELECT athlete_id, medal, COUNT(*) as tot_by_medal
FROM `assured-am-dev.tmp.summer_games`
WHERE medal is not null
GROUP BY athlete_id, medal
ORDER BY tot_by_medal, athlete_id DESC
),
max_medal_won as(
SELECT medal, MAX(tot_by_medal) as max_medal_won FROM medals_by_athlete
GROUP BY medal
)
SELECT a.name, ma.medal, max_medal_won
FROM medals_by_athlete ma
JOIN max_medal_won mm ON ma.medal = mm.medal AND ma.tot_by_medal = mm.max_medal_won
JOIN tmp.athletes a ON ma.athlete_id = a.id
;