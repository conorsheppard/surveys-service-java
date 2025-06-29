package com.conorsheppard.surveys.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class Question {
    private int id;
    private String text;
    private List<Choice> options;
    private int payout;
}
