package com.conorsheppard.surveys.repository;

import com.conorsheppard.surveys.models.Survey;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@Data
public class SurveyRepo {
    private final List<Survey> surveys;

    public SurveyRepo() throws IOException {
        this.surveys = Collections.singletonList(SurveyRepo.loadSurvey("survey.json"));
    }

    public Survey getSurveyById(int surveyId) {
        return surveys.stream().filter(survey -> survey.id() == surveyId)
                .findFirst()
                .orElse(null);
    }

    public List<Survey> listSurveys() {
        return this.surveys;
    }

    public static Survey loadSurvey(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            URL filenameUrl = SurveyRepo.class.getResource("/survey-data/" + filename);
            return objectMapper.readValue(filenameUrl, Survey.class);
        } catch (IOException e) {
            throw new IOException("Could not load survey from file: " + filename, e);
        }
    }
}
