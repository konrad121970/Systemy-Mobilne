package com.example.quiz;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Question {

    private int questionId;
    private  boolean trueAnswer;

    public Question(int questionId, boolean trueAnswer)
    {
        this.questionId=questionId;
        this.trueAnswer=trueAnswer;

    }
}
