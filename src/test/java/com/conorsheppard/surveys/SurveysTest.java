package com.conorsheppard.surveys;

import com.conorsheppard.surveys.repository.ResponseRepo;
import com.conorsheppard.surveys.repository.SurveyRepo;
import com.conorsheppard.surveys.service.SurveyService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SurveysTest {
    private static SurveyService surveyService;

    @SneakyThrows
    @BeforeAll
    static void setUp() {
        surveyService = new SurveyService(new SurveyRepo(), new ResponseRepo());
    }

    @ParameterizedTest
    @CsvSource({
            "300, 5",
            "301, 5",
            "302, 4",
            "303, 2",
    })
    @DisplayName("Test for exercise task 1: Count no. of questions per respondent")
    void task1Test(int respondentId, int numQsAnsweredExpected) {
        Map<Integer, Long> respondentCounts = surveyService.getRespondentAnswerCounts();
        assertEquals(numQsAnsweredExpected, respondentCounts.get(respondentId));
    }

    @ParameterizedTest
    @CsvSource({
            "300, 29",
            "301, 24",
            "302, 19",
            "303, 3",
    })
    @DisplayName("Test for exercise task 2: Aggregate amount earned by each respondent in pence")
    void task2Test(int respondentId, int amountEarnedExpected) {
        Map<Integer, Long> respondentAmountsEarned = surveyService.getRespondentAmountsEarned(200);
        assertEquals(amountEarnedExpected, respondentAmountsEarned.get(respondentId));
    }

}
