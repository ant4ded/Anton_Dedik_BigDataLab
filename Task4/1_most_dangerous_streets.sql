SELECT DISTINCT
	s.id 								 AS id,
	s.name 								 AS name,
	COUNT(c.id) OVER (PARTITION BY s.id) AS crime_count,
	CONCAT
		(
			'from ',
			:start,
			' till ',
			:end
		) 								 AS Period
FROM prod.street AS s
INNER JOIN prod.location 	AS l
	ON l.street_id = s.id
INNER JOIN prod.crime 		AS c
	ON c.location_id = l.id
WHERE c.month BETWEEN :start AND :end
ORDER BY crime_count DESC;
