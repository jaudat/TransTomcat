package hellobeans;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;    // need this for eg List, ArrayList

public class AddAccount extends HttpServlet {

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

    // Use a "session" bean because add account could span multiple 
    // form submissions to correct errors (although not with this impl)
    // Alternatively could use a "request" bean that would not have to
    // be cleared below.
    HttpSession session = request.getSession(true);
    UserBean userBean = (UserBean) session.getAttribute(PublicConstants.USERBEAN_ATTR);
    String nextPage;
    if (createAccount(userBean) == 1) {
        session.setMaxInactiveInterval(30);
      // In this case, finished with the bean, clear so it does not
      // interfere with use of bean for next add account attempt.  
      session.setAttribute(PublicConstants.USERBEAN_ATTR, null);
       nextPage = "index.html";
      RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
      dispatcher.forward(request, response);
      
    }

    //else
    //RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
    //dispatcher.forward(request, response);

  }

  public int createAccount(UserBean ub) {
    Connection con;
    int rowsEffected = 0;

    try {
      //out.println("HELLO");
      System.out.println("IN CREATE ACCOUNTS");
      con = DBUtil.getConnection();

      // Get a statement (used to issue SQL statements to the DB)
      Statement stmt = con.createStatement();

      // Insert the requested uid, pwd into the table

      String query = "INSERT INTO Users (username, password,first_name,email,phone,last_name) VALUES ("  
        + "'" + ub.getusername() + "'," 
        + "'" + ub.getpassword() + "'," 
        + "'" + ub.getfirst_name() + "'," 
        + "'" + ub.getemail() + "'," 
        + "'" + ub.getphone() + "'," 
        + "'" + ub.getlast_name()+ "'" + ")";
      rowsEffected = stmt.executeUpdate(query);

      stmt.close();
      con.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return rowsEffected;
  }

}
