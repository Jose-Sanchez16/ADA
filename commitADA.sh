#!/bin/bash

git add .
read -p "Dime el nombre del commit --> " var
git commit -m "$var"
git push
