#!/bin/bash

#######################################
# Global color constants
#######################################
COLOR_RED='\033[0;31m'
COLOR_GREEN='\033[0;32m'
COLOR_YELLOW='\033[1;33m'
COLOR_DEFAULT='\033[0m'

#######################################
# Getopts
#######################################
while getopts ":hs:e:q:c:" opt; do
	case ${opt} in
			h)
				printf "This script will execute queries to uk_police database:\n"
				printf "\t -h \t\t view help info for script arguments\n"
				printf "\t -s \t\t month start  (yyyy-mm)\n"
				printf "\t -e \t\t month end    (yyyy-mm)\n"
				printf "\t -c \t\t outcome category ('string'). Only for 3 query\n"
				printf "\t -q \t\t query number (n)\n"
				printf "\t    \t\t 1 Most dangerous streets.\n"
				printf "\t    \t\t   Description: Top streets by crime number within specified period.\n"
				printf "\t    \t\t 2 Month to month crime volume comparison.\n"
				printf "\t    \t\t   Description: Changes between each month in crime counts within each category and specified period.\n"
				printf "\t    \t\t 3 Crimes with specified outcome status.\n"
				printf "\t    \t\t   Description: Count and percentage of crimes per each location (street), within the given period, where outcome\n"
				printf "\t    \t\t                category equals to the given value. Taken into account only crimes where outcome status is present.\n"
				printf "\t    \t\t 4 Stop and search statistics by ethnicity.\n"
				printf "\t    \t\t   Description: Various Stop and Search statistics by ethnicity (officer_defined_ethnicity) within specified period:\n"
				printf "\t    \t\t                \t Total number of occurrences.\n"
				printf "\t    \t\t                \t Rate of Stop and Search with 'Arrest' outcome.\n"
				printf "\t    \t\t                \t Rate of Stop and Search with 'A no further action disposal' outcome.\n"
				printf "\t    \t\t                \t Rate of Stop and Search with other outcomes.\n"
				printf "\t    \t\t                \t Most popular object of search.\n"
				printf "\t    \t\t 5 Most probable Stop and Search snapshot on street level\n"
				printf "\t    \t\t   Description: Within specified date range, find the most probable Stop and Search characteristics for each street:\n"
				printf "\t    \t\t                \t Most popular age range.\n"
				printf "\t    \t\t                \t Most popular gender.\n"
				printf "\t    \t\t                \t Most popular ethnicity.\n"
				printf "\t    \t\t                \t Most popular object_of_search.\n"
				printf "\t    \t\t                \t Most popular outcome.\n"
				printf "\t    \t\t 6 Stop and Search correlation with crimes\n"
				printf "\t    \t\t   Description: Comparison between Stop and Search data with \"Arrest\" outcome and Crime data on the\n"
				printf "\t    \t\t                street level by the following Object of Search/Crime category pairs:\n"
				printf "\t    \t\t                \t \"drugs\" to \"Controlled drugs\".\n"
				printf "\t    \t\t                \t \"possession-of-weapons\" to \"Offensive weapons\" and \"Firearms\".\n"
				printf "\t    \t\t                \t \"theft-from-the-person\" and \"shoplifting\" to \"Stolen goods\".\n"
				;;
			s)
				start_date="${OPTARG}"
				;;
			e)
				end_date="${OPTARG}"
				;;
			c)
				category="${OPTARG}"
				;;
			q)
				query="${OPTARG}"
				;;
			\? )
				printf "${COLOR_RED}Invalid option: $OPTARG${COLOR_DEFAULT}\n" 1>&2
				;;
			: )
				printf "${COLOR_RED}Invalid option: $OPTARG requires an argument${COLOR_DEFAULT}\n" 1>&2
				;;
	esac
done

case ${query} in
	1)
		PGPASSWORD=root psql -d uk_police -U postgres -f 1_most_dangerous_streets.sql -v start=\'${start_date}\' -v end=\'${end_date}\'
		;;
	2)
		PGPASSWORD=root psql -d uk_police -U postgres -f 2_month_to_month_crime_volume_comparison.sql -v start=\'${start_date}\' -v end=\'${end_date}\'
		;;
	3)
		PGPASSWORD=root psql -d uk_police -U postgres -f 3_crimes_with_specified_outcome_status.sql -v start=\'${start_date}\' -v end=\'${end_date}\' -v category=\'${end_date}\'
		;;
	4)
		PGPASSWORD=root psql -d uk_police -U postgres -f 4_stop_and_search_statistics_by_ethnicity.sql -v start=\'${start_date}\' -v end=\'${end_date}\'
		;;
	5)
		PGPASSWORD=root psql -d uk_police -U postgres -f 5_most_probable_stop_and_search_snapshot_on_street_level.sql -v start=\'${start_date}\' -v end=\'${end_date}\'
		;;
	6)
		;;
	*)
		printf "${COLOR_RED}Invalid query parameter: ${query}${COLOR_DEFAULT}\n" 
		;;
esac