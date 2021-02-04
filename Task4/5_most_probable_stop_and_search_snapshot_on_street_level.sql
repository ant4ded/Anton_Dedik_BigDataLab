SELECT DISTINCT
	s.id 		AS id,
	s.name 		AS name,
 	MODE() WITHIN GROUP (ORDER BY sas.age_range) 				AS most_popular_age_range,
 	MODE() WITHIN GROUP (ORDER BY sas.gender)					AS most_popular_gender,
	MODE() WITHIN GROUP (ORDER BY sas.self_defined_ethnicity) 	AS most_popular_ethnicity,
	MODE() WITHIN GROUP (ORDER BY sas.object_of_search) 		AS most_popular_object_of_search,
	MODE() WITHIN GROUP (ORDER BY oo.name) 						AS most_popular_outcome_object
FROM prod.stop_and_search AS sas
INNER JOIN prod.location 		AS l
	ON l.id = sas.location_id
INNER JOIN prod.street 			AS s
	ON s.id = l.street_id
INNER JOIN prod.outcome_object 	AS oo
	ON oo.id = sas.outcome_object_id
WHERE sas.datetime::date BETWEEN TO_DATE(:start, 'yyyy-mm') AND TO_DATE(:end, 'yyyy-mm')
GROUP BY s.id
ORDER BY s.id