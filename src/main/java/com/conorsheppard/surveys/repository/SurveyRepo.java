package com.conorsheppard.surveys.repository;

import com.conorsheppard.surveys.models.Survey;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class SurveyRepo {
    private List<Survey> surveys;

    public SurveyRepo() throws IOException {
        this.surveys = Collections.singletonList(SurveyRepo.loadSurvey("survey.json"));
    }

    public Survey surveyById(int surveyId) {
        return surveys.stream().filter(survey -> survey.id() == surveyId)
                .findFirst()
                .orElse(null);
    }

    public List<Survey> listSurveys() {
        return this.surveys;
    }

    public static Survey loadSurvey(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            URL filenameUrl = SurveyRepo.class.getResource("/survey-data/" + filename);
            return objectMapper.readValue(filenameUrl, Survey.class);
        } catch (IOException e) {
            throw new IOException("Could not load survey from file: " + filename, e);
        }
    }
}
