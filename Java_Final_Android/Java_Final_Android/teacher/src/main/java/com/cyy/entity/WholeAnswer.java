package com.cyy.entity;

public class WholeAnswer extends BaseProtocol{
    String userNames;
    String userAnswer;

    public WholeAnswer(){
        this.userNames = "";
        this.userAnswer = "";
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }
    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswers(String userAnswers) {
        this.userAnswer = userAnswers;
    }
}
