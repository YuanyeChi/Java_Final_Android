package com.cyy.entity;

import cn.ittiger.database.annotation.PrimaryKey;

public class TotalProblems {

    //主键
    @PrimaryKey(isAutoGenerate=true)
    private long ID;

    //子问题id
    private int SubID;

    private String ProblemType;

    private String SubProblemType;

    private String ExamType;

    private String Content;

    private String Answer;
    //是否是老师添加
    private String TeacherAdd;


    public TotalProblems() {
    }

    public long getID() {return ID;}

    public int getSubID() {return SubID;}

    public String getProblemType(){return ProblemType;}

    public String getSubProblemType(){return SubProblemType;}

    public String getExamType(){return ExamType;}

    public String getContent(){return Content;}

    public String getAnswer(){return Answer;}

    public void setTeacherAdd(String b){
        this.TeacherAdd = b;
    }

    public String getTeacherAdd() {
        return TeacherAdd;
    }


}
