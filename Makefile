SHELL := /bin/bash

default: test

clean:
	mvn clean

test: clean
	mvn test

package:
	mvn package

docker-test:
	docker build . -t conorsheppard/surveys-service-java

test-coverage:
	mvn clean org.jacoco:jacoco-maven-plugin:0.8.13:prepare-agent verify org.jacoco:jacoco-maven-plugin:0.8.13:report

check-coverage:
	open -a Google\ Chrome target/jacoco-report/index.html

coverage-badge-gen:
	python3 -m jacoco_badge_generator -j target/jacoco-report/jacoco.csv

test-suite: test-coverage check-coverage coverage-badge-gen

.SILENT:
.PHONY: default clean test package docker-test test-coverage check-coverage coverage-badge-gen test-suite
