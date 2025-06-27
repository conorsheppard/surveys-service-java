package com.conorsheppard.surveys.models;

import java.util.List;

public class Survey {
    public int id;
    public String name;
    public List<Question> questions;

    @Override
    public String toString() {
        return String.format("Survey ID: %d | Name: %s | Question Count: %d", id, name, questions.size());
    }
}
