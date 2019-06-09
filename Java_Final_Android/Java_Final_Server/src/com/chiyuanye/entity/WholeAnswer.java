package com.chiyuanye.entity;

public class WholeAnswer {
//	int userNum;
	String userNames;
	String userAnswer;
	
//	public int getUserNum() {
//        return userNum;
//    }
//
//    public void setUserNum(int userNum) {
//        this.userNum = userNum;
//    }
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
