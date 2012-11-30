package hellobeans;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;    // need this for eg List, ArrayList
import javax.sql.DataSource;



public class Login extends HttpServlet {

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

      synchronized(this){    
    
    int authenticated=0;

    // Use a "session" bean because add account could span multiple 
    // form submissions to correct errors (although not with this impl)
    // Alternatively could use a "request" bean that would not have to
    // be cleared below.
    HttpSession session = request.getSession(true);
    UserBean userBean = (UserBean) session.getAttribute(PublicConstants.USERBEAN_ATTR);
    UserBean tempuser = CheckAccount(userBean);
    MyStopsBeans tempstops = new MyStopsBeans();
    if (tempuser.getid() != 0) {
      // In this case, finished with the bean, clear so it does not
      // interfere with use of bean for next add account attempt.
      //Integer.parseInt( string );
      //String dbInit = getServletContext().getInitParameter("idleTimeout");
      //session.setMaxInactiveInterval(30);

      session.setAttribute(PublicConstants.USERBEAN_ATTR, tempuser);
      authenticated=tempuser.getid();
      //tempstops = getstops(tempuser);
      //authenticated = tempstops.getuser_id()+100;

    }
    else{
      // createAccount failed; send user to an error notification page
        authenticated=tempuser.getid();
    }

    // Dispatch to appropriate JSP view
   // printed to logs/catalina.out

    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    out.println("{\"login\":[" 
                              + "{\"username\":\"" + tempuser.getusername() + 

                                "\", \"userid\":\"" + authenticated + "\"}]"

                              +"}");

  

  }
}

  public UserBean CheckAccount(UserBean ub) {
    Connection con;
    String query = "SELECT * FROM Users WHERE password="+"'"+ub.getpassword() +"'"+" and username=" + "'"+ub.getusername()+"'";
    int found=0;
    try {
     
     

      // Get a statement (used to issue SQL statements to the DB)
      DataSource dbcp = (DataSource)getServletContext().getAttribute("dbpool");
      con = dbcp.getConnection();
      Statement stmt = con.createStatement();

      // Insert the requested uid, pwd into the table

      //String query = "SELECT * FROM Users WHERE password="+"'"+ub.getpassword() +"'"+" and username=" + "'"+ub.getusername()+"'";
      ResultSet rs = stmt.executeQuery(query);

      if(rs.next()){ // if a row is returned, they are authenticated
                // fill the userBean with the rest of the properties
        ub.setid(rs.getInt("id"));
        //ub.setus
              
      }else{
                ub.setid(0);

      }
      rs.close();
      stmt.close();
      con.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return ub;
  }

  /*public MyStopsBeans getstops(UserBean ub) {
    Connection con;
    int found=0;
    MyStopsBeans returnstops=new MyStopsBeans();
    try {
      //out.println("HELLO");
      con = DBUtil.getConnection();
      // Get a statement (used to issue SQL statements to the DB)
      Statement stmt = con.createStatement();
      
      // Insert the requested uid, pwd into the table

      String query = "SELECT * FROM MyStops WHERE user_id="+"'"+ub.getid()+"'";
      ResultSet rs = stmt.executeQuery(query);

      if(rs.next()){ // if a row is returned, they are authenticated
                // fill the userBean with the rest of the properties
        returnstops.setid(rs.getInt("id"));
        returnstops.setrouteid(rs.getString("routeid"));
        returnstops.setroute_title(rs.getString("route_title"));
        returnstops.setrunid(rs.getString("runid"));
        returnstops.setrun_title(rs.getString("run_title"));
        returnstops.setstopid(rs.getString("stopid"));
        returnstops.setstop_title(rs.getString("stop_title"));
        returnstops.setuser_id(rs.getInt("user_id"));

              
      }else{
        returnstops.setid(0);

      }
      stmt.close();
      con.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return returnstops;
  }*/

}
