package com.conorsheppard.surveys.service;

import com.conorsheppard.surveys.repository.ResponseRepo;
import com.conorsheppard.surveys.repository.SurveyRepo;

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
     * Time complexity: O(n)
     * Space complexity: O(k), where k is the distinct number of respondents, at most n
     * */
    public Map<Integer, Long> getRespondentAnswerCounts() {
        return responseRepo.listResponses().stream()
                .collect(Collectors.groupingBy(
                        r -> r.respondent,
                        Collectors.counting()
                ));
    }

    /**
     * Returns a mapping between Respondent ID (Integer) and the amount earned (in pence) for questions answered.
     * Time complexity: O(q + n), where q is the number of questions (when building the payout map) and n is the number of responses
     * Space complexity: O(q + k), where we're storing an entry for each question q, and k is the number of unique respondents
     * */
    public Map<Integer, Long> getRespondentAmountsEarned(int surveyId) {
        var questions = surveyRepo.surveyById(surveyId).questions;

        Map<Integer, Integer> questionPayoutMap = questions.stream()
                .collect(Collectors.toMap(q -> q.id, q -> q.payout));

        return responseRepo.listResponses().stream()
                .filter(r -> questionPayoutMap.containsKey(r.question)) // Only consider relevant questions
                .collect(Collectors.groupingBy(
                        r -> r.respondent,
                        Collectors.summingLong(r -> questionPayoutMap.get(r.question))
                ));
    }
}
