package com.chiyuanye.util;

import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class SQLiteJDBCUtils {

	   Connection c;
	   Statement stmt;

	   /**
	    * 连接到一个现有的数据库。如果数据库不存在， 那么它就会被创建，最后将返回一个数据库对象。
	    */
	   public void before(String databaseName) {
	       try {
	           Class.forName("org.sqlite.JDBC");
	           c = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
	           System.out.println("Opened database successfully");
	           stmt = c.createStatement();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	   }

	   public void after() {
	       try {
	           stmt.close();
	           c.close();
	       } catch (SQLException e) {
	           e.printStackTrace();
	       }
	   }

	   public void createTable(String tableName, List<String> headers) throws SQLException {
	       String sql = "CREATE TABLE " + tableName + " ";
	       sql += "(ID INTEGER PRIMARY KEY AUTOINCREMENT,";
	       Iterator iterator = headers.iterator();
	       while(iterator.hasNext()) {
	    	   sql += iterator.next();
	       }
	       sql += ")";
	       stmt.executeUpdate(sql);
	   }

	   public void insert(StringBuffer sb) throws SQLException {
	       c.setAutoCommit(false);
	       //sb.append("INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
	       //        + "VALUES (1, 'Paul', 32, 'California', 20000.00 );\n");
	       stmt.executeUpdate(sb.toString());
	       c.commit();
	   }

	   public void select() throws SQLException {
	       ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
	       while (rs.next()) {
	          int id = rs.getInt("id");
	          String name = rs.getString("name");
	          int age = rs.getInt("age");
	          String address = rs.getString("address");
	          float salary = rs.getFloat("salary");
	          System.out.println("ID = " + id);
	          System.out.println("NAME = " + name);
	          System.out.println("AGE = " + age);
	          System.out.println("ADDRESS = " + address);
	          System.out.println("SALARY = " + salary);
	          System.out.println("--------");
	       }
	       rs.close();
	   }

	   public void update() throws SQLException {
	       c.setAutoCommit(false);
	       String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
	       stmt.executeUpdate(sql);
	       c.commit();

	       ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
	       while (rs.next()) {
	          int id = rs.getInt("id");
	          String name = rs.getString("name");
	          int age = rs.getInt("age");
	          String address = rs.getString("address");
	          float salary = rs.getFloat("salary");
	          System.out.println("ID = " + id);
	          System.out.println("NAME = " + name);
	          System.out.println("AGE = " + age);
	          System.out.println("ADDRESS = " + address);
	          System.out.println("SALARY = " + salary);
	          System.out.println("--------");
	       }
	       rs.close();
	   }

	   public void delete() throws SQLException {
	       c.setAutoCommit(false);
	       String sql = "DELETE from COMPANY where ID=2;";
	       stmt.executeUpdate(sql);
	       c.commit();

	       ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
	       while (rs.next()) {
	          int id = rs.getInt("id");
	          String name = rs.getString("name");
	          int age = rs.getInt("age");
	          String address = rs.getString("address");
	          float salary = rs.getFloat("salary");
	          System.out.println("ID = " + id);
	          System.out.println("NAME = " + name);
	          System.out.println("AGE = " + age);
	          System.out.println("ADDRESS = " + address);
	          System.out.println("SALARY = " + salary);
	          System.out.println("--------");
	       }
	       rs.close();
	   }
}
