SELECT DISTINCT
 	c.category 	AS category,
 	c.month		AS month,
 	COUNT(*)	AS current_crime_count,
   	CASE 
		WHEN month=CAST(:start AS varchar) THEN NULL 
		ELSE LAG(count(*)) OVER (ORDER BY category, month) 
	END			AS previous_crime_count,
	CASE 
		WHEN month=CAST(:start AS varchar) THEN NULL
		ELSE @(COUNT(*) - LAG(count(*)) OVER (ORDER BY category, month)) 
	END			AS delta_count,
	CASE 
		WHEN month=CAST(:start AS varchar) THEN NULL 
		ELSE CONCAT(TRUNC((100 * (LAG(count(*)) OVER (ORDER BY category, month)-CAST(COUNT(*) AS DECIMAL)) / COUNT(*)), 2), '%') 
	END			AS basic_growth_rate
FROM 
	prod.crime AS c
WHERE 
	month BETWEEN CAST(:start AS varchar) AND CAST(:end AS varchar)
GROUP BY category, month
ORDER BY month DESC, category, current_crime_count DESC