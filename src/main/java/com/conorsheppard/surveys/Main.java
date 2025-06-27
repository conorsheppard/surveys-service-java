package com.conorsheppard.surveys;

import com.conorsheppard.surveys.repository.ResponseRepo;
import com.conorsheppard.surveys.repository.SurveyRepo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        SurveyRepo surveys;
        ResponseRepo responses;
        try {
            surveys = new SurveyRepo();
            responses = new ResponseRepo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Logger.getGlobal().log(Level.INFO, surveys.surveyById(200).toString());
        Logger.getGlobal().log(Level.INFO, responses.responsesByRespondent(300).toString());
    }
}