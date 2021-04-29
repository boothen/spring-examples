#!/bin/bash

if [ -n "$1" ]; then
  echo "Naming project $1"
else
  echo "Project name is required"
  exit 1
fi

projectname=$1
export projectname
find . -depth -name "*changeme*" -type d -execdir sh -c 'mv {} $(echo {} | sed "s/changeme/$projectname/")' \;

find . -iname "*.gradle" -or -iname "*.java" -or -iname "application.yaml" | xargs sed -i '' "s/changeme/$1/g"

echo "Finished"
