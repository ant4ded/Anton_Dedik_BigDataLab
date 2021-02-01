#!/bin/bash
# This script will install the following in your system:
#   tool		verion
# 1. Jdk 		11
# 2. Maven 		3.5.4
# 3. Git 		2.27.0
# 4. PostgreSQL	10.15

#######################################
# Global color constants
#######################################
COLOR_RED='\033[0;31m'
COLOR_GREEN='\033[0;32m'
COLOR_YELLOW='\033[1;33m'
COLOR_DEFAULT='\033[0m'

runApp() {
	for ((i=0; i <=mnumber && month<=12; i++))
	do
		if [[ ${view_mode} == "-v" ]]
		then
			java -jar target/analysis-crime-1.0.jar -Ddate=${year}-$((month++)) -Dpath=${path} -Dverbose=true
		else
			java -jar target/analysis-crime-1.0.jar -Ddate=${year}-$((month++)) -Dpath=${path}
		fi
	done
}

#######################################
# Getopts
#######################################
while getopts ":hvqcmd:n:s:p:" opt; do
	case ${opt} in
			h)
				printf "This script will deploy analysis crime application on the system:\n"
				printf "\t -h \t\t view help info for script arguments\n"
				printf "\t -v \t\t view detail logs\n"
				printf "\t -q \t\t hide detail logs (default mode)\n"
				printf "\t -c \t\t enable database table clean\n"
				printf "\t -m \t\t enable maven build\n"
				printf "\t -d \t\t month date (yyyy-mm)\n"
				printf "\t -n \t\t month number (n)\n"
				printf "\t -p \t\t path to csv file with streets and coordinates\n"
				;;
			q)
				view_mode="-q"
				;;
			v)
				view_mode="-v"
				;;
			c)
				enableClear=1
				;;
			m)
				enableMaven=1
				;;
			d)
				year=$(echo "${OPTARG}" | cut -d "-" -f 1)
				month=$(echo "${OPTARG}" | cut -d "-" -f 2)
				;;
			n)
				mnumber=${OPTARG}
				;;
			p)
				path=${OPTARG}
				;;
			\? )
				echo "Invalid option: $OPTARG" 1>&2
				;;
			: )
				echo "Invalid option: $OPTARG requires an argument" 1>&2
				;;
	esac
done

if !$(PGPASSWORD=root psql -U postgres uk_police -c "SELECT * FROM prod.crime") &>/dev/null; 
then
	printf "${COLOR_YELLOW}Creating db schema...${COLOR_DEFAULT}\n"
	if [[ ${view_mode} == "-v" ]]
	then
		PGPASSWORD=root psql uk_police -U postgres -f db.sql
	else
		PGPASSWORD=root psql uk_police -U postgres -f db.sql -q
	fi
else
    printf "${COLOR_GREEN}Table exists.${COLOR_DEFAULT}\n"
	if [[ ${enableClear} == 1 ]]
	then
		printf "${COLOR_YELLOW}Cleaning tables...${COLOR_DEFAULT}\n"
		if [[ ${view_mode} == "-v" ]]
		then
			PGPASSWORD=root psql -U postgres uk_police -c "DELETE FROM prod.crime;"
			PGPASSWORD=root psql -U postgres uk_police -c "DELETE FROM prod.location;"
		else
			PGPASSWORD=root psql -q -U postgres uk_police -c "DELETE FROM prod.crime;"
			PGPASSWORD=root psql -q -U postgres uk_police -c "DELETE FROM prod.location;"
		fi
	fi
fi

POM=./pom.xml
if [ -f "${POM}" ]; 
then
	if [[ ${enableMaven} == 1 ]]
	then
		printf "${COLOR_YELLOW}Building project...${COLOR_DEFAULT}\n"
		if [[ ${view_mode} == "-v" ]]
		then
			mvn package
		else
			mvn -q package
		fi
	fi
else 
    printf "${COLOR_RED}${POM} does not exist.${COLOR_DEFAULT}\n"
fi

runApp
