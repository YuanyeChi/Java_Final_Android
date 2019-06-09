package sqlite;

import java.sql.*;

public class Sqlite {

  public static void main( String args[] )
  {
    Connection c = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:/home/yeyuan/Downloads/尝试.sqlite");
      ResultSet res = c.createStatement().executeQuery( "SELECT * FROM Sheet1;" );
      while ( res.next() ) {
	         System.out.println(res.getString("题目"));
	      }
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Opened database successfully");
  }
}