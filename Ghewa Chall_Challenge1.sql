//Total number of athletes in summer games top three sports
select Sport , count(DISTINCT Athlete_Id) as Total_Athletes
from SummerGames sg 
group by Sport 
order by Total_Athletes DESC 
limit 3

//Unique number of events and athletes for every sport
select Sport, count(DISTINCT Event) as Unique_Events, count(DISTINCT Athlete_Id) as Unique_Athletes
from (select  *
 	from SummerGames sg 
 	UNION ALL 
 	select * 
 	from WinterGames wg)
 group by Sport
 
// Oldest Athlete by Regoin
select c.region , Max(Age)
from (select  *
 	from SummerGames sg 
 	UNION ALL 
	select * 
 	from WinterGames wg) b 
 	inner join Athletes a on b.Athlete_Id=a.Id 
 	inner join Countries c on c.id= b.country_id
 	group by c.region 


 //Unique number of events for each sport by Descending order
select Sport, count(DISTINCT Event) as Unique_Events
from (select  *
 	from SummerGames sg 
 	UNION ALL 
 	select * 
 	from WinterGames wg)
 group by Sport
 order by Unique_Events DESC 
 
 //Total Number of bronze medals in summer games
 SELECT COUNT(*) as Bronze_Medals
 from SummerGames sg 
 where medal='Bronze'
 
 //Total Number of bronze medals by country
 Select Sum(Bronze_Medals) as Bronze_Medals_Across_Countries
 from ( SELECT COUNT(*) as Bronze_Medals
 		from SummerGames sg 
 		where medal='Bronze' 
	    Group by Country_Id)
	   
 
//Average age of female athletes
 select ROUND(AVG(AGE)) as Female_Average_Age
 from Athletes a 
 where Gender ='F'
 
 //Number of Gold,Silver, Bronze for every country that has at least one medal of any kind
 Select Country_Id,sum (CASE WHEN Medal = 'Bronze' THEN 1 ELSE 0 END) as bronze,
 sum (CASE WHEN Medal = 'Gold' THEN 1 ELSE 0 END) as Gold,
 sum (CASE WHEN Medal = 'Silver' THEN 1 ELSE 0 END) as Silver
 from SummerGames sg 
 group by Country_Id 
 having gold <>0 or Silver <> 0 or Bronze <> 0
 order by Gold desc
 
