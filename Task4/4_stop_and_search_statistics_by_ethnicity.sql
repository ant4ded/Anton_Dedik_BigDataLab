SELECT DISTINCT
	sas.officer_defined_ethnicity								 									AS officer_defined_ethnicity,
	COUNT (*) 																						AS count_by_officer_defined_ethnicity,
 	COUNT (CASE WHEN oo.name = 'Arrest' THEN 1 END)  												AS count_arest,
	COUNT (CASE WHEN oo.name = 'A no further action disposal' THEN 1 END)  							AS count_no_further_action,
 	COUNT (CASE WHEN oo.name != 'A no further action disposal' AND oo.name != 'Arrest' THEN 1 END)  AS count_other,
  	MODE() WITHIN GROUP (ORDER BY sas.object_of_search) 											AS most_popular_object_of_search
FROM prod.stop_and_search AS sas
INNER JOIN prod.outcome_object AS oo
	ON oo.id = sas.outcome_object_id
WHERE sas.datetime::date BETWEEN TO_DATE(:start, 'yyyy-mm') AND TO_DATE(:end, 'yyyy-mm')
GROUP BY officer_defined_ethnicity
ORDER BY sas.officer_defined_ethnicity