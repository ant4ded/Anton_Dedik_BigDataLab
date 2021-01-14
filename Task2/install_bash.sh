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

#######################################
# Check if tool of passed command was
# installed. If tool was not installed
# install it and check is installing 
# was successful.
# Globals:
#	COLOR_RED
#	COLOR_GREEN
#	COLOR_YELLOW
#	COLOR_DEFAULT
# Arguments:
#	command for check
#	quiet mod for yum
# Output:
#	Write steps of installing
# Returns:
#	None
#######################################
check_and_install_tool_for_command () {
	if ! command -v ${1} &> /dev/null
	then
		case ${1} in
			"java")
				printf "${COLOR_YELLOW}Install Java...${COLOR_DEFAULT}\n"
				sudo yum install java-11-openjdk-devel -y ${2}
				if command -v ${1} &> /dev/null
				then
					printf "${COLOR_GREEN}Java was installed!${COLOR_DEFAULT}\n"
				else
					printf "${COLOR_RED}Error: Java was not installed!${COLOR_DEFAULT}\n"
				fi
				;;
			"mvn")
				printf "${COLOR_YELLOW}Install Maven...${COLOR_DEFAULT}\n"
				sudo yum install maven -y ${2}
				if command -v ${1} &> /dev/null
				then
					printf "${COLOR_GREEN}Maven was installed!${COLOR_DEFAULT}\n"
				else
					printf "${COLOR_RED}Error: Maven was not installed!${COLOR_DEFAULT}\n"
				fi
				;;
			"git")
				printf "${COLOR_YELLOW}Install Git...${COLOR_DEFAULT}\n"
				sudo yum install git -y ${2}
				if command -v ${1} &> /dev/null
				then
					printf "${COLOR_GREEN}Git was installed!${COLOR_DEFAULT}\n"
				else
					printf "${COLOR_RED}Error: Git was not installed!${COLOR_DEFAULT}\n"
				fi
				;;
			"psql")
				printf "${COLOR_YELLOW} Install PostgreSQL...${COLOR_DEFAULT}\n"
				sudo yum install postgresql-server -y ${2}
				if command -v ${1} &> /dev/null
				then
					printf "${COLOR_GREEN}PostgreSQL was installed!${COLOR_DEFAULT}\n"
					sudo postgresql-setup --initdb
					sudo systemctl start postgresql
					sudo systemctl enable postgresql
				else
					printf "${COLOR_RED}Error: PostgreSQL was not installed!${COLOR_DEFAULT}\n"
				fi
				;;
		esac
	else
		printf "${COLOR_GREEN}Tool for ${1} command already installed!${COLOR_DEFAULT}\n"
	fi
}

#######################################
# Getopts
#######################################
while getopts "qvh" opt; do
	case ${opt} in
			h)
				h=${OPTARG}
				printf "This script will install the following in your system:\n"
				printf "   tool		verion\n"
				printf "1. Jdk 		11\n"
				printf "2. Maven 	3.5.4\n"
				printf "3. Git 		2.27.0\n"
				printf "4. PostgreSQL	10.15\n\n"
				printf "\t -h \t\t view help info for script arguments\n"
				printf "\t -v \t\t view detail yum logs\n"
				printf "\t -q \t\t hide yum logs\n"
				;;
			q)
				view_mode="-q"
				;;
			v)
				view_mode="-v"
				;;
			\?)
				exit 1
				;;
	esac
done

printf "${COLOR_YELLOW}Update yum...${COLOR_DEFAULT}\n"
sudo yum update -y ${view_mode}
printf "${COLOR_GREEN}Yum was updated!${COLOR_DEFAULT}\n"
check_and_install_tool_for_command java ${view_mode}
check_and_install_tool_for_command mvn ${view_mode}
check_and_install_tool_for_command git ${view_mode}	
check_and_install_tool_for_command psql ${view_mode}
