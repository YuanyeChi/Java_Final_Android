package textHandler;

import java.sql.ResultSet;
import java.util.*;

public class CreateDB {
	public static void main(String[] args) throws Exception {
		String tableName = "LogInfo";
		SQLiteJDBCUtils userDB = new SQLiteJDBCUtils();
		userDB.before("User.db");
		
//		StringBuffer insertions = new StringBuffer();
//		String sql = "INSERT INTO " + tableName + "(USERNAME, PASSWORD) " +
//	               "VALUES ('456', '123');\n";
//		insertions.append(sql);
		ResultSet rs = userDB.stmt.executeQuery("SELECT * FROM LogInfo group by id having password='123';");
		System.out.println(rs.isBeforeFirst());
		while (rs.next()) {
	          String id = rs.getString("id");
	          String userName = rs.getString("USERNAME");
	          String passWord = rs.getString("PASSWORD");
	          System.out.println("ID = " + id);
	          System.out.println("NAME = " + userName);
	          System.out.println("AGE = " + passWord);
	       }
		try {
//			userDB.insert(insertions);
			userDB.after();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
