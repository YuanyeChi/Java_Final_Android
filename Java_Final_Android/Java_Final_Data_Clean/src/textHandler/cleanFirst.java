package textHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cleanFirst {
	public static void main(String[] args) throws Exception {
		SQLiteJDBCUtils cyy = new SQLiteJDBCUtils();
		cyy.before("Problems.db");
		ArrayList<String> Name = new ArrayList<>();
		ResultSet rs = cyy.stmt.executeQuery("select name from sqlite_master where type='table' order by name;");
		while (rs.next()) {
			Name.add(rs.getString("name"));
	        System.out.println(rs.getString("name"));
	    }
		//ALTER  TABLE   table-name ADD COLUMN  column-namecolumn-type
		Iterator iterator = Name.iterator();
		while(iterator.hasNext()) {
			try {
				String string = iterator.next().toString();//name
				//System.out.println(string);
				//cyy.stmt.executeQuery("ALTER  TABLE " + iterator.next() +" ADD COLUMN  teacherAdd TEXT;");
				if (string.equals("Cloze")||string.equals("Reading_Deduce")||
						string.equals("Reading_Details")||string.equals("Reading_PastYears")||
						string.equals("Reading_Subject")||string.equals("Reading_WordMeaning")) 
				{
					Print.print(string);
					ArrayList<String> answer = new ArrayList<>();
					ArrayList<String> id = new ArrayList<>();
					ResultSet rs1 = cyy.stmt.executeQuery("select id,answer from " + string +";");
					while (rs1.next()) {
				       answer.add(rs1.getString("answer"));
				       id.add(rs1.getString("id"));
				       //System.out.println(rs.getString("answer"));
				    }
					for(int i = 0; i < answer.size(); i++) {
						Print.print(id.get(i));
						Print.print(answer.get(i));
						//String newAnswer = replaceBlank(answer.get(i).trim()).trim();
						//cyy.stmt.executeUpdate("UPDATE " + string +" set answer = '" + newAnswer + "' where id = " + id.get(i) + ";");
					}
				}
				//cyy.stmt.executeUpdate("UPDATE " + iterator.next() +" set teacherAdd = 'false';");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		cyy.after();
	}
	
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\\s\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
