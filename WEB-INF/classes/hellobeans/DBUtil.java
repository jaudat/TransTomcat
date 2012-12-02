package hellobeans; 

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.*;

public class DBUtil {

  private static final String DB_JDBC_DRIVER = "com.mysql.jdbc.Driver";
  // Replace next line with retrieval of dbUrl from context-param
  private static final String URL = "jdbc:mysql://mathlab.utsc.utoronto.ca:3306/cscc09f12_30";
  private static final String DB_USER = "cscc09f12_30";
  private static final String DB_PASSWD = "athersucks";

  public static Connection getConnection() throws ClassNotFoundException,
      SQLException {
    try {
	Class.forName(DB_JDBC_DRIVER);
        return DriverManager.getConnection(URL, DB_USER, DB_PASSWD);
    }
    catch(SQLException ex) {
        System.err.println("SQLException: " + ex.getMessage());
	return null;
    }
    catch (Exception e) {
      	e.printStackTrace();
	return null;
    }

  }

}
