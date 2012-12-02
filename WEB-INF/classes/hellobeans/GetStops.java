package hellobeans;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;    // need this for eg List, ArrayList

public class GetStops extends HttpServlet {

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
    int authenticated=0;

    // Use a "session" bean because add account could span multiple 
    // form submissions to correct errors (although not with this impl)
    // Alternatively could use a "request" bean that would not have to
    // be cleared below.
    HttpSession session = request.getSession(true);
    UserBean userBean = (UserBean) session.getAttribute(PublicConstants.USERBEAN_ATTR);
    MyStopsBeans tempstops = new MyStopsBeans();
    String json_string = new String();
    //String uid=request.getParameter( userBean.get);
    //Integer.parseInt( string );
    // /Integer.toString(i)
    json_string = getstops(Integer.toString(userBean.getid()));
    //Integer.parseInt( string );
    /*


                               + "\"stops\":[" +
                               "{\"routeid\":\"" + tempstops.getrouteid() + 

                                "\", \"route_title\":\"" + tempstops.getroute_title() + 

                                "\", \"runid\":\"" + tempstops.getrunid() +

                                "\", \"run_title\":\"" + tempstops.getrun_title() +

                                "\", \"stopid\":\"" + tempstops.getstopid() +

                                 "\", \"stop_title\":\"" + tempstops.getstop_title()+

                                  "\", \"user_id\":\"" + tempstops.getuser_id()    

                                + "\"}]"

                                "{\"stops\":[" + "{\"username\":\"" + tempstops.getstop_title()+ "\", \"authenticated\":\"" + uid + "\"}]}"

    */

    // Dispatch to appropriate JSP view
   // printed to logs/catalina.out
     //System.out.println("JsonReturn");                           
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    //out.println("here\n");
    out.println(json_string);
    /*out.println("{\"stops\":[" +
                               "{\"routeid\":\"" + tempstops.getrouteid() + 

                                "\", \"route_title\":\"" + tempstops.getroute_title() + 

                                "\", \"runid\":\"" + tempstops.getrunid() +

                                "\", \"run_title\":\"" + tempstops.getrun_title() +

                                "\", \"stopid\":\"" + tempstops.getstopid() +

                                 "\", \"stop_title\":\"" + tempstops.getstop_title()+

                                  "\", \"user_id\":\"" + tempstops.getuser_id()    

                                + "\"}]}");*/

    //RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
    //dispatcher.forward(request, response);

  }

  public String getstops(String ub) {
    Connection con;
    int found=0;
    MyStopsBeans returnstops=new MyStopsBeans();
    String JsonReturn = new String();
    try {
      //out.println("HELLO");
      Integer uid = Integer.parseInt(ub);
      con = DBUtil.getConnection();
      // Get a statement (used to issue SQL statements to the DB)
      Statement stmt = con.createStatement();
      
      // Insert the requested uid, pwd into the table
      
      String query = "SELECT * FROM MyStops WHERE user_id="+"'"+uid+"'";
      ResultSet rs = stmt.executeQuery(query);
      boolean stop_found=false;
      JsonReturn="{\"stops\":[";
      while(rs.next()){ // if a row is returned, they are authenticated
                // fill the userBean with the rest of the properties
        returnstops.setid(rs.getInt("id"));
        returnstops.setrouteid(rs.getString("routeid"));
        returnstops.setroute_title(rs.getString("route_title"));
        returnstops.setrunid(rs.getString("runid"));
        returnstops.setrun_title(rs.getString("run_title"));
        returnstops.setstopid(rs.getString("stopid"));
        returnstops.setstop_title(rs.getString("stop_title"));
        returnstops.setuser_id(rs.getInt("user_id"));
        //Integer.toString
        stop_found=true;
        JsonReturn = JsonReturn +
                               "{\"routeid\":\"" + returnstops.getrouteid() + 

                                "\", \"route_title\":\"" + returnstops.getroute_title() + 

                                "\", \"runid\":\"" + returnstops.getrunid() +

                                "\", \"run_title\":\"" + returnstops.getrun_title() +

                                "\", \"stopid\":\"" + returnstops.getstopid() +

                                 "\", \"stop_title\":\"" + returnstops.getstop_title()+

                                  "\", \"user_id\":\"" + returnstops.getuser_id() +"\""    

                                + "},";

              
      }
      
      if (!stop_found){
          JsonReturn=JsonReturn + "{ \"route_title\":\"No Stops Saved\"}]}";

      }else{

      JsonReturn = JsonReturn.substring(0, JsonReturn.length() - 1);
      JsonReturn =JsonReturn+"]}";
      }
      stmt.close();
      con.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return JsonReturn;
  }

}
