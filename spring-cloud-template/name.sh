#!/bin/bash

find . -depth -name "*changeme*" -type d -execdir sh -c 'mv {} $(echo {} | sed "s/changeme/bar/")' \;

find . -iname "*.gradle" -or -iname "*.java" -exec sed -i '' 's/changeme/bar/g' {} +