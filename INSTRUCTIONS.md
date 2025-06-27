## Overview

* All data for this test is provided in the [survey-data](src/main/resources/survey-data) directory.
* We have provided a simple application that loads the JSON data from survey data into classes, you may use this as a starting point, or start your implementation from scratch.
* For the purposes of this task please consider the following rules as factual:
    1. Each question is a single choice and responses can not contain more than one response for each question per respondent
    2. Each answer choice contains a `route`, when a respondent selects this answer, this `route` is the next question the respondent will be served
    3. Where an answer choice does not contain a `route` this should be considered the end of the survey
    4. Respondents can only answer each survey once, and respondent IDs are universally unique
    5. Each question contains a `payout` this is the amount (in pence) that a respondent will be paid for answering the question
    6. The integer value `choice` in each response refers to the index of the option in the question it is referring too

## Tasks

1. Find the number of questions that each respondent has answered so far as part of `survey 1` (id: `200`)
2. Find the amount of money (in pence) earned by each respondent for the answers they have given so far as part of `survey 1` (id: `200`)

## Time

We don't expect you to spend more than around an hour on this take home task.

## How we assess your answers

We care about the following (in priority order):

1. Correctness: does your solution correctly implement the functionality required and return the correct results?
2. Simple, clean, and readable code: is the code easy for us to follow and understand?
3. Performance: is your solution sufficiently performant given the task it is performing?

## Follow up

Should you be invited for a follow-up pairing session we will:

1. Discuss your approach to the task listed above
2. Ask you to implement more functionality around routes through a survey (note surveys are not necessarily strictly linear, and answers can route to questions other than the next index) [More information regarding survey routes can be found here](./Routes.md)

## Helpful Tips

### Gradle: to build, test and run the project with Gradle

There is no need to manually install gradle in order to use it in this repo, simply use the included `gradlew` (MacOS, Linux) or `gradlew.bat` (Windows)

#### Run the code
To build, test and run the project use the following tasks

```console
$ ./gradlew clean build run
```

### Maven: to build, test and run the project with Maven.

#### Install Maven
First install Maven if you don't already have it installed. This can be done in a number of ways

1. [The official way and for Windows users](https://maven.apache.org/install.html)
2. On MacOs with [Homebrew](https://brew.sh/)
   ```console
   $ brew install maven
   ```
3. On Linux with APT package manager
   ```console
   $ apt install maven
   ```

#### Run the code
To build, test and run the project use the following tasks

```console
$ mvn clean package exec:java
```

