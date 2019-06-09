package com.chiyuanye.demo.entity;

/**
 * Created by  ansen
 * Create Time 2017-06-10
 */
public class User extends BaseProtocol{
    private String password;
    private String username;
    private String exist;
    private String answer;
    private String comment;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExist() {
        return exist;
    }

    public void setExist(String Exist) {
        this.exist = Exist;
    }

    public String getAnswer() {return answer;}

    public void setAnswer(String answer) {this.answer = answer; }

    public String getComment() {return comment;}

    public void setComment(String comment) {this.comment = comment;}


    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", exist='" + exist + '\'' +
                ", answer='" + answer + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
