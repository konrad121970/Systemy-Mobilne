package com.example.quiz2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {
    private int QuestionId;
    private boolean trueAnswer;

        public Question(int QuestionId, boolean trueAnswer){
                this.QuestionId = QuestionId;
                this.trueAnswer = trueAnswer;
        }
}
