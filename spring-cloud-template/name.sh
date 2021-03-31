#!/bin/bash

if [ -n "$1" ]; then
  echo "Naming project $1"
else
  echo "Project name is required"
  exit 1
fi

find . -depth -name "*changeme*" -type d -execdir sh -c 'mv {} $(echo {} | sed "s/changeme/$1/")' \;

find . -iname "*.gradle" -or -iname "*.java" | xargs sed -i '' "s/changeme/$1/g"

echo "Finished"
