package com.conorsheppard.surveys.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class Response {
    private int respondent;
    private int question;
    private int choice;

    @Override
    public String toString() {
        return String.format("Respondent: %d chose option index: %d for Question: %d", respondent, choice, question);
    }
}
