#!/bin/bash

#Remove old screenshots
rm ./screenshots/actual/*;
rm ./screenshots/diff/*;
rm ./screenshots/gifs/*;

#clear errors log
> ./report/errors.log

# Run tests

#test desktop
mvn test -Dbrowser=ch -Denv=dev -Dbash=1;
#test tablet
mvn test -Dbrowser=ch -Denv=dev -Ddimension=768x1000 -Dbash=1;
#test mobile
mvn test -Dbrowser=ch -Denv=dev -Ddimension=360x1000 -Dbash=1;
#generate report
mvn test -Dtest=BaseTestFinish


