package com.conorsheppard.surveys.models;

import java.util.List;

public class Question {
    public int id;
    public String text;
    public List<Choice> options;
    public int payout;
}
