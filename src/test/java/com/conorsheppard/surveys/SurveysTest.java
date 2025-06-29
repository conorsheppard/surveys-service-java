package com.conorsheppard.surveys;

import com.conorsheppard.surveys.models.Question;
import com.conorsheppard.surveys.models.Response;
import com.conorsheppard.surveys.models.Survey;
import com.conorsheppard.surveys.repository.ResponseRepo;
import com.conorsheppard.surveys.repository.SurveyRepo;
import com.conorsheppard.surveys.service.SurveyService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.mockito.Mockito.*;

import java.util.List;
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
            "300, 6", // 6 answers across 2 surveys
            "301, 5",
            "302, 4",
            "303, 2",
    })
    @DisplayName("Test for exercise task 1: Count no. of questions per respondent")
    void testTask1(int respondentId, int numQsAnsweredExpected) {
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
    void testTask2(int respondentId, int amountEarnedExpected) {
        Map<Integer, Long> respondentAmountsEarned = surveyService.getRespondentAmountsEarned(200);
        assertEquals(amountEarnedExpected, respondentAmountsEarned.get(respondentId));
    }

    @ParameterizedTest
    @CsvSource({
            "100, 50",
    })
    @DisplayName("Tests that all questions not belonging to the given survey are filtered out, avoiding java.lang.NullPointerException")
    void testFilterIrrelevantQuestions(int respondentId, int amountEarnedExpected) {
        // Mock the survey and set up questions with 1 irrelevant question
        var surveyRepoMock = mock(SurveyRepo.class);
        int surveyId = 500, questionId1 = 100, questionId2 = 101;
        when(surveyRepoMock.surveyById(surveyId)).thenReturn(new Survey()
                .id(surveyId)
                .name("Example Survey")
                .questions(List.of(
                        new Question(questionId1, "Example question 1", List.of(), 25),
                        new Question(questionId2, "Example question 2", List.of(), 25))));

        var responseRepo = mock(ResponseRepo.class);
        // If the respondent has taken more than 1 survey, under the current implementation, all responses for all surveys will be returned
        when(responseRepo.getResponses()).thenReturn(List.of(
                new Response(respondentId, questionId1, 0),
                new Response(respondentId, questionId2, 0),
                new Response(respondentId, 200, 0) // we're only interested in surveyId: 500, this should be filtered out
        ));

        var surveyServiceReposMocked = new SurveyService(surveyRepoMock, responseRepo);

        Map<Integer, Long> respondentAmountsEarned = surveyServiceReposMocked.getRespondentAmountsEarned(surveyId);

        assertEquals(amountEarnedExpected, respondentAmountsEarned.get(respondentId));
    }


    void testSurveyHasCycle() {

    }

    // Tests whether the choice index exists in each question the respondent answered
    void testRespondentMadeValidChoices() {

    }
}
