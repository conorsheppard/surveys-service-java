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

I tried to keep most of the file structure the same and just added a SurveyService - to encapsulate the methods that address the tasks - and a set of tests, to confirm the functionality of the ServeyService.  
Test coverage is a little low because we're only testing what is outlined in the above tasks.

### Executing the tests

You can run the tests in your IDE, like in the demo below. Alternatively, you can run them with Maven (`make test`) or within a Docker container if you don't have Maven or Java 24 installed (`make docker-test`).

https://github.com/user-attachments/assets/86a871e1-e3f4-4026-837e-2d4702d10c09