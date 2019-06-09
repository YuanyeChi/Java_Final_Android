package com.cyy.entity;

public class ProblemsHolder {
        String[] myAnswer1;
        TotalProblems thisProblem;
        String problemType;
        public void setMyAnswer1(String[] answer1){ this.myAnswer1 = answer1;}
        public String[] getMyAnswer1(){return myAnswer1;}
        public void setThisProblem(TotalProblems prob){this.thisProblem = prob;}
        public TotalProblems getThisProblem() { return thisProblem; }
        public void setProblemType(String problemType) {this.problemType = problemType;}
        public String getProblemType() {return problemType;}
        private static final ProblemsHolder holder = new ProblemsHolder();
        public static ProblemsHolder getInstance() {return holder;}


}
