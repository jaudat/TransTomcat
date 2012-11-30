package hellobeans;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;    // need this for eg List, ArrayList

public class Controller extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
      IOException, ServletException {
    process(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws
      IOException, ServletException {
    process(request, response);
  }

  private void process(HttpServletRequest request, HttpServletResponse response) throws
      IOException, ServletException {

    String action = request.getParameter("action");

    if (action.equals("AddAccount")) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/AddAccount");
        dispatcher.forward(request, response);
        }
    else if (action.equals("ViewAccounts")) {
        RequestDispatcher view_dispatcher = request.getRequestDispatcher("/ViewAccounts");
	// Note we "include" this result rather than forwarding it
        view_dispatcher.include(request, response);
	 }
  else if (action.equals("Login")){
      RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
        dispatcher.forward(request, response);
      }

    else if (action.equals("AddStops")){
      RequestDispatcher dispatcher = request.getRequestDispatcher("/AddStops");
        dispatcher.forward(request, response);
      }



    else {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bad_request.jsp");
        dispatcher.forward(request, response);
        }  
  }

  public void init() {
    Connection con;
    int rowsEffected = 0;   // intended for error checking; not used here

    try {
      String dbInit = getServletContext().getInitParameter("dbInit");
      if (dbInit.equals("true")) {	// dbInit context says to initialize DB
        con = DBUtil.getConnection();

	// Get a statement (used to issue SQL statements to the DB)
	/*Statement stmt = con.createStatement();

	String query = "DROP TABLE PASSWD_TBL";
	rowsEffected = stmt.executeUpdate(query);

	query = "CREATE CACHED TABLE PASSWD_TBL ("
    		+ "UID CHAR(8) NOT NULL,"
    		+ "PWD CHAR(8) NOT NULL)";
	rowsEffected = stmt.executeUpdate(query);
	System.out.println("DEBUG: " + rowsEffected);
	query = "INSERT INTO PUBLIC.PASSWD_TBL (UID, PWD) VALUES"
		+ "('user', 'ok')," + "('user2', 'pass2')";
	rowsEffected = stmt.executeUpdate(query);
	// should check to make sure table was created without an error
  
	stmt.close();*/
	con.close();
      }
    }
    catch(SQLException ex) {
      System.err.println("SQLException: " + ex.getMessage());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
