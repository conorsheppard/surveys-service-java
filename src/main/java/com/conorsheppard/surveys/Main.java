package com.conorsheppard.surveys;

import com.conorsheppard.surveys.repository.ResponseRepo;
import com.conorsheppard.surveys.repository.SurveyRepo;
import com.conorsheppard.surveys.service.SurveyService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        var surveyService = new SurveyService(new SurveyRepo(), new ResponseRepo());

        surveyService.getRespondentAnswerCounts()
                .forEach((respondentId, answerCount) ->
                        log.info("Respondent ID: {}, Questions answered: {}", respondentId, answerCount));

        surveyService.getRespondentAmountsEarned(200)
                .forEach((respondentId, amountEarned) -> log.info("Respondent ID: {}, Amount earned: {}", respondentId, amountEarned));
    }
}