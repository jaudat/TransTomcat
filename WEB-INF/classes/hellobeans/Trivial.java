package hellobeans; 

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Trivial extends HttpServlet {

    public void doGet( HttpServletRequest request, 
		       HttpServletResponse response)
	throws IOException, ServletException {

	Connection con;
	response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

	try { 
		out.println("IN TRIVIAL ");
	    con = DBUtil.getConnection();
      
	    Statement stmt = con.createStatement();

	    String query="SELECT * FROM Users";

	    ResultSet rs = stmt.executeQuery(query);
	    out.println("BEFORE WHILE LOOP");
            while(rs.next()) {
		out.println(rs.getString("TABLE_SCHEMA"));
		out.println(rs.getString("TABLE_NAME"));
		out.println(rs.getString("TABLE_TYPE"));
		out.println("IN WHILE LOOP");
	    }
	    rs.close();
	    stmt.close();
	    con.close();
	} catch(Exception e) {
	    out.println("Exception: " + e.getMessage());
    }
  }
}