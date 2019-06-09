package textHandler;

import textHandler.TextFile;

import java.sql.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.JList.DropLocation;

import textHandler.Print;

public class textHandler {
	public static void main(String[] args) throws Exception {
		
		TextFile text = new TextFile("/home/yeyuan/Desktop/Java_Final_Txt/6　sentence.txt");
		Print.print(text.size());
		List<List<List<List<String>>>> res = textHandle(text);
		Print.print(res.get(0));
		store(res);
		
		
	}
	
	public static List<List<List<List<String>>>> textHandle(TextFile text) {
		List<List<List<List<String>>>> res = new ArrayList<>(3);
		//修改 i
		for (int i = 0; i < 7; i++) {
			res.add(new ArrayList<>());
			for (int j = 0; j < 3; j++) {
				res.get(i).add(new ArrayList<>());
				for (int k = 0; k < 2; k++) {
					res.get(i).get(j).add(new ArrayList<>());
				}
			}
		}
		Print.print(res.size());

		int problemTypeCount = 0; 
		for (int i = 0; i < text.size(); i++) {
			//i是行数
			String s = text.get(i).trim();
			Print.print(s);
			if (s.equals("并列句")) {
				res = addProblem(text, res, i + 1, 0, problemTypeCount);
				Print.print("fuck");
			}
			if (s.equals("定语从句")) {
				res = addProblem(text, res, i + 1, 1, problemTypeCount);
				Print.print("fuck1");
			}
			if (s.equals("名词性从句")) res = addProblem(text, res, i + 1, 2, problemTypeCount);
			if (s.equals("状语从句")) res = addProblem(text, res, i + 1, 3, problemTypeCount);
			if (s.equals("倒装句")) res = addProblem(text, res, i + 1, 4, problemTypeCount);
			if (s.equals("强调句")) res = addProblem(text, res, i + 1, 5, problemTypeCount);
			if (s.equals("其他特殊句式")) res = addProblem(text, res, i + 1, 6, problemTypeCount++);
			Print.print(problemTypeCount);
		}
		return res;
	}
	
	public static List<List<List<List<String>>>> addProblem(TextFile text, 
			List<List<List<List<String>>>> res, int rowCount, int wordType, int problemType) {
		//wordType 0名词 1代词 2冠词  probleType 0练习 1高考 2模拟 problemType 0 题目 1 答案
		//wordType 0介词 1动词短语  probleType 0练习 1高考 2模拟
		//wordType 0形容词 1副词  probleType 0练习 1高考 2模拟
		//wordType 0非谓语动词  probleType 0练习 1高考 2模拟
		//wordType 0并列句 1定语从句 2名词性从句 3状语从句 4倒装句 5强调句 6其他特殊句式   probleType 0练习 1高考 2模拟 problemType 0 题目 1 答案
		for(int i = rowCount; ;i++) {
			//最后一个跳出
			if (i == text.size()) break;
			String s = text.get(i).trim();

			boolean helper = false;
			boolean helper1 = false;
			Matcher matcher = Pattern.compile("^(\\d+\\.)(.*)").matcher(s);//数字开头加."^(\\d+)(\\.)(.*)"
			if (helper = matcher.find()) {
				res.get(wordType).get(problemType).get(0).add(matcher.group(2));
				Print.print(matcher.group(0));
			}

			Matcher matcher1 = Pattern.compile("^(答案　)(.*)").matcher(s);//数字开头加."^(\\d)(\\.)(.*)"
			if (helper1 = matcher1.find()) {
				res.get(wordType).get(problemType).get(1).add(matcher1.group(2));
				Print.print(matcher1.group(0));
			}
			if (!helper && !helper1) {break;}
		}
		Print.print("====================");
		return res;
	}

	public static void store(List<List<List<List<String>>>> res) throws Exception {
		StringBuffer insertions = new StringBuffer();
		
		for (int i = 0; i < res.size(); i++) {
			for (int j = 0; j < res.get(i).size(); j++) {
				for (int k = 0; k < res.get(i).get(j).get(0).size(); k++) {
					String tableName = "";
					String Type = "";
					String POrA = "";
					
					if (i == 0) tableName = "binglie";
					else if (i == 1) tableName = "dingyu";
					else if (i == 2) tableName = "mingcixing";
					else if (i == 3) tableName = "zhuangyu";
					else if (i == 4) tableName = "daozhuang";
					else if (i == 5) tableName = "qiangdiao";
					else tableName = "qitateshuju";
					
					if (j == 0) Type = "lx";
					else if (j == 1) Type = "gk";
					else Type = "mn";
					
					if (k == 0) POrA = "PROBLEMS";
					else if (k == 1) POrA = "ANSWER";
					
					String sql = "INSERT INTO " + tableName + "(TYPE, PROBLEMS, ANSWER) " +
				               "VALUES ('" + Type + "', '" + res.get(i).get(j).get(0).get(k) + 
				               "','" + res.get(i).get(j).get(1).get(k) +  "');\n";
					insertions.append(sql);
				}
			}
		} 
		
		List<String> headers = new LinkedList<>();
		headers.add("TYPE TEXT NOT NULL,");
		headers.add("PROBLEMS TEXT NOT NULL,");
		headers.add("ANSWER TEXT NOT NULL");
		
		SQLiteJDBCUtils cyy = new SQLiteJDBCUtils();
		cyy.before("Problems.db");
		cyy.createTable("binglie", headers);
		cyy.createTable("dingyu", headers);
		cyy.createTable("mingcixing", headers);
		cyy.createTable("zhuangyu", headers);
		cyy.createTable("daozhuang", headers);
		cyy.createTable("qiangdiao", headers);
		cyy.createTable("qitateshuju", headers);
			
		cyy.insert(insertions);
		cyy.after();
		
//		drop table binglie;
//		drop table dingyu;
//		drop table mingcixing;
//		drop table zhuangyu;
//		drop table daozhuang;
//		drop table qiangdiao;
//		drop table qitateshuju;
	}
}
