package com.conorsheppard.surveys.service;

import com.conorsheppard.surveys.models.Question;
import com.conorsheppard.surveys.models.Response;
import com.conorsheppard.surveys.repository.ResponseRepo;
import com.conorsheppard.surveys.repository.SurveyRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SurveyService {
    private final SurveyRepo surveyRepo;
    private final ResponseRepo responseRepo;

    public SurveyService(SurveyRepo surveyRepo, ResponseRepo responseRepo) {
        this.surveyRepo = surveyRepo;
        this.responseRepo = responseRepo;
    }

    /**
     * Returns a mapping between Respondent ID (Integer) and the number of questions answered by the given respondent (Long).
     * Groups by respondent and counts occurrences.
     * Time complexity: O(n).
     * Space complexity: O(k), where k is the distinct number of respondents, at most n.
     */
    public Map<Integer, Long> getRespondentAnswerCounts() {
        return responseRepo.getResponses().stream()
                .collect(Collectors.groupingBy(
                        Response::respondent,
                        Collectors.counting()
                ));
    }

    /**
     * Returns a mapping between Respondent ID (Integer) and the amount earned (in pence) for questions answered.
     * Time complexity: O(q + n), where q is the number of questions (when building the payout map) and n is the number of responses.
     * Space complexity: O(q + k), where we're storing an entry for each question q, and k is the number of unique respondents.
     *
     * @param surveyId ID of the survey the respondent has completed (or in the process of).
     */
    public Map<Integer, Long> getRespondentAmountsEarned(int surveyId) {
        var questionToPayoutMap = surveyRepo.surveyById(surveyId)
                .questions()
                .stream()
                .collect(Collectors.toMap(Question::id, Question::payout));

        return responseRepo.getResponses()
                .stream()
                .filter(response -> questionToPayoutMap.containsKey(response.question())) // filter out questions from other surveys
                .collect(Collectors.groupingBy(
                        Response::respondent,
                        Collectors.summingLong(r -> questionToPayoutMap.get(r.question()))));
    }
}
