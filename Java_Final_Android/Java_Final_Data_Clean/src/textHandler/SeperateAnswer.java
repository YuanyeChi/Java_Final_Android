package textHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeperateAnswer {
	public static void main(String[] args) throws Exception {
		SQLiteJDBCUtils Problems = new SQLiteJDBCUtils();
		Problems.before("ProblemsTotal.db");
		ResultSet rs = Problems.stmt.executeQuery("select content from totalproblems where (problemtype='Cloze');");
		int count = 0;
		while (rs.next()) {
	        //System.out.println(rs.getString("content"));
	        List<String> list = Arrays.asList(rs.getString("content").split("\n"));
	        int i = 0;
	        
	        for (i = 0; i < list.size(); i++) {
	        	String string = list.get(i);
	        	Matcher matcher = Pattern.compile("^(1\\.)(.*)").matcher(string);
	        	if (matcher.find()) {
	        		count++;
					//Print.print(matcher.group(0));
					//Print.print("======================");
	        		break;
				}
	        }
	        
	        for (int j = i; j < list.size(); j++) {
	        	//Print.print(list.get(j));
	        	String string = list.get(j);
	        	Matcher matcher = Pattern.compile("^(\\d+\\.)(.*)").matcher(string);
	        	if (matcher.find()) {
					String string1 = matcher.group(2);
					//Print.print(Arrays.toString(string1.split("\\s+", 4)));
					String[] strings = string1.split("\\s+", 4);
					String res = "";
					for (int k = 0; k < strings.length; k++) {
						Print.print(strings[k]);
//						Pattern p = Pattern.compile("^(\\S+)(.*)");
//			            Matcher m = p.matcher(strings[k]);
//			            res += m.group(0);
					}
//					Print.print(res);
				}
	        }	        
	    }
        Print.print(count);
		
		Problems.after();
	}
	
}
