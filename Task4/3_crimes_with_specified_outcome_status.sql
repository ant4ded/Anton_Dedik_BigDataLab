SELECT DISTINCT ON (id)
	s.id 																							AS id,
	s.name 																							AS name,
	os.category 																					AS category,
	COUNT(CASE WHEN os.category = 'Status update unavailable' THEN 1 END) OVER (PARTITION BY s.id)  AS count, 
	CASE 
		WHEN os.category IS NULL THEN NULL
		ELSE CONCAT(TRUNC((100*COUNT(*) OVER (PARTITION BY s.id, os.category = :category)/CAST(COUNT(*) OVER (PARTITION BY s.id) AS DECIMAL)), 2), '%')
	END 																							AS percentage 
FROM prod.crime AS c
INNER JOIN prod.location 		AS l
	ON l.id = c.location_id
INNER JOIN prod.street 			AS s
	ON s.id = l.street_id
LEFT JOIN prod.outcome_status 	AS os
	ON os.id = c.outcome_status_id 
WHERE 
	c.month BETWEEN CAST(:start AS varchar) AND CAST(:end AS varchar)
GROUP BY os.category, s.id
  