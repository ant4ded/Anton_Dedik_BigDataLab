SELECT DISTINCT
	s.id 								 AS id,
	s.name 								 AS name,
	COUNT(c.id) OVER (PARTITION BY s.id) AS crime_count,
	CONCAT
		(
			'from ',
			CAST(:start AS varchar),
			' till ',
			CAST(:end AS varchar)
		) 		AS Period
FROM prod.street AS s
INNER JOIN prod.location 	AS l
	ON l.street_id = s.id
INNER JOIN prod.crime 	AS c
	ON c.location_id = l.id
WHERE c.month BETWEEN CAST(:start AS varchar) AND  CAST(:end AS varchar)
ORDER BY crime_count DESC;
