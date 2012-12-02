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

    String nextPage="index.html";
    int isonline=0;
    String authenticated="false";

    // Use a "session" bean because add account could span multiple 
    // form submissions to correct errors (although not with this impl)
    // Alternatively could use a "request" bean that would not have to
    // be cleared below.
    HttpSession session = request.getSession(true);
    UserBean user = (UserBean) session.getAttribute(PublicConstants.USERBEAN_ATTR);

  if (user == null) {
        user = new UserBean();
        session.setAttribute(PublicConstants.USERBEAN_ATTR, user);
  }
  if (!user.getLoggedIn()) {

    String username = request.getParameter(PublicConstants.USERNAME_PARAM);
    String password = request.getParameter(PublicConstants.PASSWORD_PARAM);
    user.setusername(username);
    user.setpassword(password);
    System.out.println(username + password);
    user.setLoggedIn(false);
    user.setid("");


     authenticate(user);

     System.out.println(user.getLoggedIn());
     if (!user.getLoggedIn()){
      authenticated="false";
     }else {
      user.setLoggedIn(true);
      authenticated="true";}

  }

   response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    out.println("{\"authenticated\":\"" + authenticated
  + "\", \"page\":\"" + nextPage
  + "\", \"userId\":\"" + user.getid() + "\"}");



   }

 private void authenticate(UserBean user){

        Connection con;
        // using prepared statement for security
        // DEPRECATED lowercase the username when comparing, username is not casesensitive
        //String searchQuery = "SELECT * FROM Users WHERE LOWER(USERNAME) = ? AND PASSWORD = ?";
        String searchQuery = "SELECT * FROM Users WHERE password="+"'"+user.getpassword() +"'"+" and username=" + "'"+user.getusername()+"'";
        //SELECT * FROM Users WHERE password="ather123" and username="Ather123";

        System.out.println("In authenticate: " + user.getusername() + user.getpassword() + searchQuery);
        // connect to db and retrieve routes
        try {
            con = DBUtil.getConnection();
             //  rs = statement.executeQuery(QueryString); 
            Statement stmt=con.createStatement();
            ResultSet rs = stmt.executeQuery(searchQuery);

            if(rs.next()){ // if a row is returned, they are authenticated
                // fill the userBean with the rest of the properties
                System.out.println("USER FOUND");
                user.setid(rs.getString("id"));
                user.setLoggedIn(true);
            }else{
                 System.out.println("USER FOUND");

            }

            // close result set
            rs.close();

            // close prep statement
            stmt.close();

            //close db connection
            con.close();
           
        }

        catch(SQLException ex) {
          System.err.println("SQLException: " + ex.getMessage());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
         
    }


}
