package com.conorsheppard.surveys.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class Choice {
    private String text;
    private int route = -1;
    private int index;
}
