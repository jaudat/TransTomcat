package hellobeans;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;    // need this for eg List, ArrayList

public class DeleteStops extends HttpServlet {

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
    String stopid=request.getParameter("stopid");
   
    delete(userBean,stopid);

    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    //out.println("here\n");
    out.println("deleted");

  }

  public void delete(UserBean ub,String stopid) {
    Connection con;
    int rowsEffected;

    try {
     
      con = DBUtil.getConnection();

     
      Statement stmt = con.createStatement();

      
      String query = "DELETE FROM MyStops WHERE user_id="+ub.getid()+ " AND stopid=" + stopid;  
      System.out.print(query);
       
      rowsEffected = stmt.executeUpdate(query);
      stmt.close();
      con.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
  }

}
