## 📝 Surveys Service Java

<table>
<tr>
<td>
Test Coverage
</td>
<td>
<img src="./badges/jacoco.svg" style="display: flex;" alt="jacoco-test-coverage-badge">
</td>
</tr>
</table>

### Task 1
- Find the number of questions that each respondent has answered so far as part of `survey 1` (id: `200`)

### Task 2
- Find the amount of money (in pence) earned by each respondent for the answers they have given so far as part of `survey 1` (id: `200`)

### Implementation

I tried to keep most of the file structure the same as it is in the original repo and just added a SurveyService - to encapsulate the methods that address the tasks - and a set of tests, to confirm the functionality of the SurveyService.  
Test coverage is a little low because we're only testing what is outlined in the above tasks.

### Executing main

To run the main method with Maven, execute the following command:

```shell
make exec
```

### Executing the tests

You can run the tests in your IDE, like in the demo below. Alternatively, you can run them with Maven (`make test`) or within a Docker container if you don't have Maven or Java 24 installed (`make docker-test`).

https://github.com/user-attachments/assets/86a871e1-e3f4-4026-837e-2d4702d10c09

### Notes/Thoughts

- Choice as an index is space-efficient, however, it is a little fragile - if the question's option order changes, old responses break.
- The terms choice, option, answer, and response are used to represent similar concepts. While they each carry subtly different meanings, their overlap may cause some confusion during implementation or review.
- It could be prudent to maintain a link directly from Response to Survey (add surveyId to Response so it doesn’t have to be inferred from the questions answered)
- To decouple logic from data, we could implement a routing service that maps question-choice pairs to the next (optional) question ID, instead of keeping this data inside Choice