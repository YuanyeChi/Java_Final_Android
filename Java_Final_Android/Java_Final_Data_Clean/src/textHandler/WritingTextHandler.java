package textHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WritingTextHandler {

	public static void main(String[] args) throws Exception {
		TextFile text = new TextFile("/home/yeyuan/Desktop/Java_Final_Txt/12　词义猜测.txt");
		//Print.print(text.size());
		List<List<List<List<String>>>> res = textHandle(text);
		//Print.print(res.get(0));
		store(res); 
	}
	public static List<List<List<List<String>>>> textHandle(TextFile text) {
		List<List<List<List<String>>>> res = new ArrayList<>(3);
		//修改 i
		for (int i = 0; i < 1; i++) {
			res.add(new ArrayList<>());
			for (int j = 0; j < 2; j++) {
				res.get(i).add(new ArrayList<>());
				for (int k = 0; k < 2; k++) {
					res.get(i).get(j).add(new ArrayList<>());
				}
			}
		}
		Print.print(res.size());
		Print.print(res.get(0).size());
		Print.print(res.get(0).get(0).size());

		int problemTypeCount = 0; 
		for (int i = 0; i < text.size(); i++) {
			//i是行数
			String s = text.get(i).trim();
			//Print.print(s);
			if (s.equals("词义推测")) {
				res = addProblem(text, res, i + 1, 0, problemTypeCount++);
				Print.print(i + "hhh");
			}
//			if (s.equals("定语从句")) {
//				res = addProblem(text, res, i + 1, 1, problemTypeCount);
//				Print.print("fuck1");
//			}
			//Print.print(problemTypeCount);
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
			String problems = "";
			String answer = "";
			//最后一个跳出
			if (i == text.size()) break;
			String s = text.get(i).trim();

			boolean helper = false;
			boolean helper1 = false;
			Matcher matcher = Pattern.compile("^(Passage \\d)(.*)").matcher(s);//数字开头加."^(\\d+)(\\.)(.*)"
			if (helper = matcher.find()) {
				problems += matcher.group(2);
				Print.print(matcher.group(0));
				problems = searchDownProblem(i + 1, problems, text);
				//Print.print(problems);
				Print.print("====================");
				res.get(wordType).get(problemType).get(0).add(problems);
			}

			Matcher matcher1 = Pattern.compile("^(答案)(.*)").matcher(s);//数字开头加."^(\\d)(\\.)(.*)"
			if (helper1 = matcher1.find()) {
				answer += matcher1.group(2);//
				Print.print(matcher1.group(0));
				answer = searchDownAnswer(i + 1, answer, text);
				//Print.print(answer);
				
				res.get(wordType).get(problemType).get(1).add(answer);
			}
			if (s.equals("词义推测")) {
				Print.print("ssssssssssssssssss");
				break;
			}
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
					//Print.print(res.get(i).get(j).get(0).size());
					//Print.print(res.get(i).get(j).get(1).size());
					//Print.print("i = " + i + "j = " + j + "k = " + k);
					if (i == 0) tableName = "Reading_WordMeaning";
//					else tableName = "qitateshuju";
					
					//if (j == 0) Type = "lx";
					if (j == 0) Type = "gk";
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
		cyy.createTable("Reading_WordMeaning", headers);
		cyy.insert(insertions);
		cyy.after();
		
		
//		cyy.createTable("dingyu", headers);
//		cyy.createTable("mingcixing", headers);
//		cyy.createTable("zhuangyu", headers);
//		cyy.createTable("daozhuang", headers);
//		cyy.createTable("qiangdiao", headers);
//		cyy.createTable("qitateshuju", headers);

//		drop table binglie;
//		drop table dingyu;
//		drop table mingcixing;
//		drop table zhuangyu;
//		drop table daozhuang;
//		drop table qiangdiao;
//		drop table qitateshuju;
	}

	public static String searchDownProblem(int rowCount, String string, TextFile text) {
		for (int i = rowCount; i < text.size();i++ ) {
			String s = text.get(i).trim();
			Matcher matcher1 = Pattern.compile("^(答案)(.*)").matcher(s);
			if (!matcher1.find()) {
				string += "\n" + s;
			}
			else break;
		}
		return string;
	}
	
	public static String searchDownAnswer(int rowCount, String string, TextFile text) {
		for (int i = rowCount; i < text.size();i++ ) {
			String s = text.get(i).trim();
			Matcher matcher = Pattern.compile("^(Passage \\d)(.*)").matcher(s);
			if (!matcher.find()) {
				string += "\n" + s;
			}
			else break;
		}
		return string;
	}
}
