package hellobeans;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
// import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;  // moved to Controller
// import org.apache.commons.dbcp.datasources.*;

public class Login extends HttpServlet {

  //private static final String DB_JDBC_DRIVER = "org.h2.Driver";
  /* moved to Controller ...
  private static final String DB_JDBC_DRIVER = "com.mysql.jdbc.Driver";
  private static final String DB_USER = "cscc09f12_39";
  private static final String DB_PWD = "supersecret";
  */

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    process(request, response);
  }

  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException,
      IOException {
    process(request, response);
  }

  /** 
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void process(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
      IOException {
    synchronized(this) {

        int status = 0; // holds status of login

      String nextPage = request.getParameter("page");
      String authenticated = "true";
      int userId = 0;

      HttpSession session = request.getSession(true);
      // session.setMaxInactiveInterval(60);  // now set by init() in Controller
      UserBean user = (UserBean) request.getAttribute(PublicConstants.USERBEAN_ATTR);

      // if the userbean doesn't exists, create it
      if (user == null) {
        user = new UserBean();
        session.setAttribute(PublicConstants.USERBEAN_ATTR, user);
      }

      if (!user.getLoggedIn()) {
            // retrieve request parameters; if we used a JSP on the front-end
      // would already have these values in a bean as part of request
            String username = request.getParameter(PublicConstants.USERNAME_PARAM);
            String password = request.getParameter(PublicConstants.PASSWORD_PARAM);

            // record the username and password values in a User Bean
            user.setusername(username);
            user.setpassword(password);
            // in case malicious user posts these values
            user.setLoggedIn(false);
            user.setid("");

            // attempt to login
            authenticate(user);

            // if we failed, redirect to the login page
            if (user.getLoggedIn()) {
    // since user is now logged in, we will retrieve their playlist
              //HashMap<String, CDBean> playlist = this.getPlaylist(user);
    //session.setAttribute("playlist", playlist);
      }
            else {
          nextPage = "#login";
    authenticated = "false";
            }
        } 

    // debugging
    System.out.println(nextPage + "," + authenticated); // printed to logs/catalina.out

    // redirect to the next action, if login invoked on startup of another action
    /* in server-controller model without Ajax, tell client which page to load ...
       response.sendRedirect(nextPage);
    */
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    out.println("{\"authenticated\":\"" + authenticated
  + "\", \"page\":\"" + nextPage
  + "\", \"userId\":\"" + user.getid() + "\"}");
  }
 }


    /*
     * Set the loggedIn property of the userbean if authentication succeeds.
     */
    private void authenticate(UserBean user){

        Connection con;
        // using prepared statement for security
        // DEPRECATED lowercase the username when comparing, username is not casesensitive
        //String searchQuery = "SELECT * FROM Users WHERE LOWER(USERNAME) = ? AND PASSWORD = ?";
        String searchQuery = "SELECT * FROM Users WHERE USERNAME = ? AND PASSWORD = ?";

        // connect to db and retrieve routes
        try {
            // get connection pool
            DataSource dbcp = (DataSource)getServletContext().getAttribute("dbpool");
            con = dbcp.getConnection();

            PreparedStatement searchQueryP = con.prepareStatement(searchQuery);

            // set username and password
            //searchQueryP.setString(1, user.getUsername().toLowerCase());
            searchQueryP.setString(1, user.getusername());
            searchQueryP.setString(2, user.getpassword());

            ResultSet rs = searchQueryP.executeQuery();

            if(rs.next()){ // if a row is returned, they are authenticated
                // fill the userBean with the rest of the properties
                user.setid(rs.getString("id"));
                user.setLoggedIn(true);
            }

            // close result set
            rs.close();

            // close prep statement
            searchQueryP.close();

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


    /*
     * returns a HashMap of user's playlist
     */
    /*private HashMap<String, CDBean> getPlaylist(UserBean user){

        // initialize a new cdmap
        HashMap<String, CDBean> CDMap = new HashMap<String,CDBean>();

        Connection con;
        // use prepared statement
        String searchQuery = "SELECT * FROM Playlist WHERE USER_ID = ?";

        // connect to db and retrieve routes
        try {
            // get connection pool
            DataSource dbcp = (DataSource)getServletContext().getAttribute("dbpool");
            con = dbcp.getConnection();

            PreparedStatement searchQueryP = con.prepareStatement(searchQuery);

            searchQueryP.setInt(1, user.getId());
            ResultSet rs = searchQueryP.executeQuery();

            while(rs.next()){ // convert results to CDBeans
                CDBean cd = new CDBean();
                cd.setId(rs.getString("id"));
                cd.setTitle(rs.getString("title"));
                cd.setBand(rs.getString("band"));
                cd.setPrice(rs.getString("price"));
                cd.setUserId(user.getId());
                
                CDMap.put(cd.getId(), cd); //add to hashmap keyed by CD id
            }

            // close result set
            rs.close();

            // close prep statement
            searchQueryP.close();


            //close db connection
            con.close();

        }

        catch(SQLException ex) {
          System.err.println("SQLException: " + ex.getMessage());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        return CDMap;
    }*/

}