package com.conorsheppard.surveys.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class Survey {
    private int id;
    private String name;
    private List<Question> questions;

    @Override
    public String toString() {
        return String.format("Survey ID: %d | Name: %s | Question Count: %d", id, name, questions.size());
    }
}
