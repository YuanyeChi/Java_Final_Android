package com.chiyuanye.demo.utils;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;

import com.chiyuanye.demo.entity.Record;
import com.chiyuanye.demo.entity.TotalProblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;

import cn.ittiger.database.SQLiteDB;

public class ProblemHelper {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Record fetchUserProblem(Intent intent, SQLiteDB recordDB) {
        List<Record> list = new ArrayList();
        list = recordDB.queryAll(Record.class);
        Log.i("CYY", String.valueOf(list.size()));
        ListIterator<Record> iter = list.listIterator();
        while (iter.hasNext()) {
            Record r = iter.next();
            if (!r.getProblemType().equals(intent.getStringExtra("ProblemType"))
                    || !r.getExamType().equals(intent.getStringExtra("ExamType"))
                    || !r.getSubProblemType().equals(intent.getStringExtra("SubProblemType"))) {
                iter.remove();
            }
        }
        //生成一个包含指定题目的优先队列，错误率高的优先提供，没做过的优先做
        PriorityQueue<Record> q = new PriorityQueue<>(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                if (o1.getFinishedTimes() == 0 && o2.getFinishedTimes() == 0) {
                    return 1;
                } else if (o1.getFinishedTimes() == 0) {
                    return -1;
                } else if (o2.getFinishedTimes() == 0) {
                    return 1;
                } else {
                    return (int) (o1.getScore() - o2.getScore());
                }
            }
        });
        q.addAll(list);
        return q.poll();
    }

    public static boolean[] judgeSelectAnswer(String[] myAnswer, TotalProblems problem) {
        boolean[] res = new boolean[getAnswerNumber(problem)];
        if (problem.getProblemType().equals("Reading") || problem.getProblemType().equals("Cloze")) {
            List<String> list = new ArrayList<String>(Arrays.asList(problem.getAnswer().split("\\d+\\.")));
            list.remove(0);
            for (int i = 0; i < myAnswer.length; i++) {
                res[i] = list.get(i).equals(myAnswer[i]);
            }
        } else if (problem.getProblemType().equals("Sentence") || problem.getProblemType().equals("Word")) {
            if (problem.getAnswer().contains(";") && (!myAnswer[0].contains("；") || !myAnswer[0].contains(";"))) {
                res[0] = false;
                return res;
            }
            if (problem.getAnswer().contains(";")) {
                String[] s = problem.getAnswer().split(";");
                //考虑到中文分号
                String temp = myAnswer[0].replace("；", ";");
                String[] s1 = temp.split(";");
                for (int i = 0; i < s.length; i++) {
                    if (s[i].contains("/")) {
                        String[] temp1 = s[i].split("/");
                        for (int j = 0; j < temp.length(); j++) {
                            res[0] = temp1[j].toLowerCase().equals(s1[i].toLowerCase());
                        }
                    } else {
                        res[0] = s[i].toLowerCase().equals(s1[i].toLowerCase());
                    }
                }
            } else {
                res[0] = myAnswer[0].toLowerCase().equals(problem.getAnswer().toLowerCase());
            }
        }

        return res;
    }

    public static int getAnswerNumber(TotalProblems problem) {
        if (problem.getProblemType().equals("Reading") || problem.getProblemType().equals("Cloze")) {
            List<String> list = new ArrayList<>(Arrays.asList(problem.getAnswer().split("\\d+\\.")));
            list.remove(0);
            return list.size();
        } else if (problem.getProblemType().equals("Sentence") || problem.getProblemType().equals("Word")) {
            return 1;
        }
        return 0;
    }

    public static int getWordNumber(TotalProblems problems) {
        if (problems.getAnswer().contains(";")) {
            String[] s = problems.getAnswer().split(";");
            return s.length;
        } else return 1;
    }

    public static int getMyWordNumber(String s) {
        String temp = s.replace("；", ";");
        String[] s1 = temp.split(";");
        return s1.length;
    }

    public static void store(TotalProblems problem, SQLiteDB recordDB, boolean[] result) {
        //次数加1
        Record record = recordDB.query(Record.class, String.valueOf(problem.getID()));
        record.setFinishedTimes(record.getFinishedTimes() + 1);
        //计算正确率
        int correctNum = 0;
        int wrongNum = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i]) correctNum++;
            else wrongNum++;
        }
        float thisScore = (float) correctNum / ((float) correctNum + (float) wrongNum);
        float totalScore = (record.getScore() * (record.getFinishedTimes() - 1) + thisScore) /
                record.getFinishedTimes();
        Log.i("CYY","" + totalScore);
        record.setScore(totalScore);
        Log.i("CYY","" + record.getID());
        recordDB.update(record);
        //Record record1 = recordDB.query(Record.class, String.valueOf(problem.getID()));
        //Log.i("CYY", String.valueOf(record1.getScore() + "" + record1.getID()));
        //List<Record> list = recordDB.queryAll(Record.class);

    }

    public static String generateReport(TotalProblems problems, SQLiteDB recordDB, boolean[] result, String[] myAnswer){

//        List<Record> list1 = recordDB.queryAll(Record.class);
//        Log.i("CYY", String.valueOf(list1.size()));
//        for (int i = 0; i < list1.size(); i++) {
//            Log.i("CYY", String.valueOf(i));
//            Log.i("CYY", String.valueOf(list1.get(i).getScore()));
//        }
        String report = "<h2>结果报告</h2><p>本题正确率为<b>";
        //还是通过ID找到record
        int id = (int)problems.getID();
        String problemType = problems.getProblemType();
        Record record = recordDB.query(Record.class, String.valueOf(id));

        float correctNum = 0;
        float wrongNum = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i]) correctNum += 1;
            else wrongNum += 1;
        }
        //计算正确率
        Log.i("CYY", String.valueOf(correctNum));
        Log.i("CYY", String.valueOf(wrongNum));
        float thisScore = correctNum * 100 / (correctNum + wrongNum) / 100;
        report += "" + thisScore + ";</b>";
        float totalScore = (record.getScore() * record.getFinishedTimes() + thisScore) /
                (record.getFinishedTimes() + 1);

        //返回错误细节和标准答案
        report += "具体情况如下:<p>";
        for (int i = 0; i < result.length; i++) {
            report += "第" + (i + 1) + "题 ";
            report += myAnswer[i];
            if (result[i]) report += " <b>正确</b>;  ";
            else report += " <b>错误</b>;  ";
        }
        report += "标准答案为" + problems.getAnswer() + "</p>";

        if (problemType.equals("Word")) {
            report += "在<strong>词汇题</strong>中正确率高于其他<b>";
        }else if (problemType.equals("Sentence")) {
            report += "在<strong>句子题</strong>中正确率高于其他<b>";
        }else if (problemType.equals("Cloze")) {
            report += "在<strong>完形填空题</strong>中正确率高于其他<b>";
        }else if (problemType.equals("Reading")) {
            report += "在<strong>阅读理解题</strong>中正确率高于其他<b>";
        }
        List<Record> list = recordDB.queryAll(Record.class);
        //本类型战胜率
        float beatNum = 0;
        float totalNum = 0;
        Log.i("CYY", "total score" + totalScore);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFinishedTimes() != 0 && list.get(i).getProblemType().equals(problems.getProblemType()) && list.get(i).getScore() < totalScore) {
                beatNum += 1;
                totalNum += 1;
            }
            if (list.get(i).getFinishedTimes() != 0 && list.get(i).getProblemType().equals(problems.getProblemType()) && list.get(i).getScore() >= totalScore) {
                totalNum += 1;
            }
        }
        Log.i("CYY", String.valueOf(beatNum));
        Log.i("CYY", String.valueOf(totalNum));
        float thisBeatRatio = beatNum / totalNum;
        //总战胜率
        beatNum = 0;
        totalNum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFinishedTimes() != 0 && list.get(i).getScore() < totalScore) {
                beatNum++;
                totalNum++;
            }
            if (list.get(i).getFinishedTimes() != 0 && list.get(i).getScore() >= totalScore) {
                totalNum++;
            }
        }
        float totalBeatRatio = beatNum / totalNum;
        report += thisBeatRatio + "</b>的题，在所有客观题中正确率高于其他<b>" + totalBeatRatio + "</b>的题。";

        if (totalBeatRatio < thisBeatRatio) report += "鉴于你的总正确率要小于此类正确率，可以多刷刷其他题型～";
        else report += "看样子你这方面相对比较薄弱诶，要多刷刷！";

        if (record.getFinishedTimes() == 0 && thisScore < 0.5) report += "<p>第一次做，分数稍微低了点，继续努力！</p>";
        else if  (record.getFinishedTimes() == 0 && thisScore >= 0.5) report += "<p>第一次做就那么厉害，做的不错！</p>";
        else if (record.getScore() != 0 && record.getScore() > thisScore) report += "<p>相较上次这次退步了，要注意！</p>";
        else if (record.getScore() != 0 && record.getScore() <= thisScore) report += "<p>有进步，再接再厉！</p>";


        if (problems.getExamType().equals("mn") || problems.getExamType().equals("lx"))
            report += "</p><p>由于你这次做的是模拟题</b>,建议抽空刷刷高考真题。</p>";
        else
            report += "</p><p>由于你这次做的是高考题</b>,建议抽空刷刷模拟题，多见见偏题怪题也有好处。</p>";

        return report;

    }
}
