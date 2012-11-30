package hellobeans;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;    // need this for eg List, ArrayList

public class Stops extends HttpServlet {

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

    String nextPage;

    // Use a "session" bean because add account could span multiple 
    // form submissions to correct errors (although not with this impl)
    // Alternatively could use a "request" bean that would not have to
    // be cleared below.
    HttpSession session = request.getSession(true);
    UserBean userBean = (UserBean) session.getAttribute(PublicConstants.USERBEAN_ATTR);

    if (createAccount(userBean) == 1) {
      // In this case, finished with the bean, clear so it does not
      // interfere with use of bean for next add account attempt.  
      session.setAttribute(PublicConstants.USERBEAN_ATTR, userBean);
      nextPage = "index.html";
    }
    else{
      // createAccount failed; send user to an error notification page
      nextPage = "login_failed.jsp";
    }

    // Dispatch to appropriate JSP view
    RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
    dispatcher.forward(request, response);

  }

  public int createAccount(UserBean ub) {
    Connection con;
    int found=0;
    try {
      //out.println("HELLO");
      con = DBUtil.getConnection();

      // Get a statement (used to issue SQL statements to the DB)
      Statement stmt = con.createStatement();

      // Insert the requested uid, pwd into the table

      String query = "SELECT * FROM Users WHERE password="+"'"+ub.getpassword() +"'"+" and username=" + "'"+ub.getusername()+"'";
      ResultSet rs = stmt.executeQuery(query);

      if(rs.next()){ // if a row is returned, they are authenticated
                // fill the userBean with the rest of the properties
        found=1;
              
      }else{
                 System.out.println("USER not FOUND");

      }


      stmt.close();
      con.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return found;
  }

}
