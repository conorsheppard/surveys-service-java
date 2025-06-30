package com.conorsheppard.surveys.repository;

import com.conorsheppard.surveys.models.Response;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Data
@Accessors(chain = true)
public class ResponseRepo {
    private List<Response> responses;

    public ResponseRepo() throws IOException {
        this.responses = ResponseRepo.loadResponses("responses.json");
    }

    public List<Response> getResponsesByRespondent(int respondentId) {
        return this.responses.stream().filter(response -> response.respondent() == respondentId).toList();
    }

    public List<Response> getResponsesByRespondentAndQuestion(int respondentId, int questionId) {
        return this.responses.stream().filter(response -> response.respondent() == respondentId && response.question() == questionId).toList();
    }

    public static List<Response> loadResponses(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            URL filenameUrl = ResponseRepo.class.getResource("/survey-data/" + filename);
            return objectMapper.readValue(filenameUrl, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IOException("Could not load survey from file: " + filename, e);
        }
    }
}
