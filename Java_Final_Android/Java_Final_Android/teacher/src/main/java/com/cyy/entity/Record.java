package com.cyy.entity;

import cn.ittiger.database.annotation.PrimaryKey;

public class Record {

    @PrimaryKey(isAutoGenerate=true)
    private long ID;

    private int SubID;

    private String ProblemType;

    private String SubProblemType;

    private String ExamType;
    //废弃
    private String Finished;

    private int CorrectTimes;

    private int WrongTimes;

    private String TeacherAdd;

    private float Score;

    private int FinishedTimes;


    //必须实现无参构造
    public Record() {
    }

    public String getProblemType(int ID){
        return this.ProblemType;
    }

    public long getID() {return ID;}

    public int getSubID() {return SubID;}

    public String getProblemType(){return ProblemType;}

    public String getSubProblemType(){return SubProblemType;}

    public String getExamType(){return ExamType;}

    public String getFinished(){return Finished;}

    public void setFinished(String finished) {this.Finished = finished;}

    public int getCorrectTimes() {return CorrectTimes;}

    public void setCorrectTimes(int correctTimes) {this.CorrectTimes = correctTimes;}

    public int getWrongTimes() {return WrongTimes;}

    public void setWrongTimes(int wrongTimes) {this.WrongTimes = wrongTimes;}

    public void setTeacherAdd(String teacherAdd){
        this.TeacherAdd = teacherAdd;
    }

    public String getTeacherAdd() {
        return TeacherAdd;
    }

    public void setScore(float score){this.Score = score;}

    public float getScore() {return Score;}

    public void setFinishedTimes(int finishedTimes){this.FinishedTimes = finishedTimes;}

    public int getFinishedTimes() {return FinishedTimes;}


}

