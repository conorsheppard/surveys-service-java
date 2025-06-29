package com.conorsheppard.surveys.repository;

import com.conorsheppard.surveys.models.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Data
public class ResponseRepo {
    private List<Response> responses;

    public ResponseRepo() throws IOException {
        this.responses = ResponseRepo.loadResponses("responses.json");
    }

    public List<Response> responsesByRespondent(int respondentId) {
        return this.responses.stream().filter(response -> response.respondent() == respondentId).toList();
    }

    public List<Response> responsesByRespondentAndQuestion(int respondentId, int questionId) {
        return this.responses.stream().filter(response -> response.respondent() == respondentId && response.question() == questionId).toList();
    }

    public static List<Response> loadResponses(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            URL filenameUrl = ResponseRepo.class.getResource("/survey-data/" + filename);
            return objectMapper.readValue(filenameUrl, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IOException("Could not load survey from file: " + filename, e);
        }
    }
}
