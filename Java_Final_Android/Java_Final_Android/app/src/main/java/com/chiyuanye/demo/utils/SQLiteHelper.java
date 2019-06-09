package com.chiyuanye.demo.utils;

import com.chiyuanye.demo.entity.Record;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SQLiteHelper {
    public static int InfoToID(ArrayList<Record> Info, String problemType, String subProblemType, int subID){
        int id = -1;
        Iterator<Record> iterator = Info.iterator();
        while(iterator.hasNext()) {
            Record nowRecord = iterator.next();
            if(nowRecord.getProblemType().equals(problemType) && nowRecord.getSubProblemType().equals(subProblemType)
            && nowRecord.getSubID() == subID) {
                id = (int)nowRecord.getID();
                break;
            }
        }
        return id;
    }

    public static int InfoToID(ArrayList<Record> Info, String subProblemType, int subID){
        int id = -1;
        Iterator<Record> iterator = Info.iterator();
        while(iterator.hasNext()) {
            Record nowRecord = iterator.next();
            if(nowRecord.getSubProblemType().equals(subProblemType) && nowRecord.getSubID() == subID) {
                id = (int)nowRecord.getID();
                break;
            }
        }
        return id;
    }
}
