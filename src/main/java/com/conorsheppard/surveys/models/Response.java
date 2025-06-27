package com.conorsheppard.surveys.models;

public class Response {
    public int respondent;
    public int question;
    public int choice;

    @Override
    public String toString() {
        return String.format("Respondent: %d chose option index: %d for Question: %d", respondent, choice, question);
    }
}
