package hellobeans;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;    // need this for eg List, ArrayList

public class SaveStops extends HttpServlet {

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
    MyStopsBeans stopBeans = new MyStopsBeans();
    UserBean userBean = (UserBean) session.getAttribute(PublicConstants.USERBEAN_ATTR);


    String routeid=request.getParameter( "routeid" );
    stopBeans.setrouteid(routeid);

    String route_title=request.getParameter("routetitle");
    stopBeans.setroute_title(route_title);

    String runid=request.getParameter("runid");
    stopBeans.setrunid(runid);

    String run_title = request.getParameter("run_title");
 	stopBeans.setrun_title(run_title);

    String stopid=request.getParameter("stopid");
    stopBeans.setstopid(stopid);

    String stoptitle=request.getParameter("stoptitle");
    stopBeans.setstop_title(stoptitle);
  

     stopBeans.setuser_id(userBean.getid());

    

  

    

    

   

   


    saved(stopBeans); 
   
    
    String json_string = new String();
    //String uid=request.getParameter( "userid" );
    //json_string = saved(userBean);
    /*if (CheckAccount(userBean) == 1) {
      // In this case, finished with the bean, clear so it does not
      // interfere with use of bean for next add account attempt.  
      session.setAttribute(PublicConstants.USERBEAN_ATTR, userBean);
      authenticated=1;

    }
    else{
      // createAccount failed; send user to an error notification page
      
      authenticated=0;
    }*/
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
     System.out.println("JsonReturn");                           
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    //out.println("here\n");
    out.println("Success");
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

  public void saved(MyStopsBeans stops) {
    Connection con;
    int found=0;
    MyStopsBeans returnstops=new MyStopsBeans();
    String JsonReturn = new String();
    try {
      //out.println("HELLO");
      //Integer uid = Integer.parseInt(ub);
      con = DBUtil.getConnection();
      // Get a statement (used to issue SQL statements to the DB)
      Statement stmt = con.createStatement();
      
      // Insert the requested uid, pwd into the table
      
      String query = "INSERT INTO MyStops (routeid, route_title,runid,run_title,stopid,stop_title,user_id) VALUES ("  
        + "'" + stops.getrouteid() + "'," 
        + "'" + stops.getroute_title() + "'," 
        + "'" + stops.getrunid() + "'," 
        + "'" + stops.getrun_title() + "'," 
        + "'" + stops.getstopid() + "'," 
        + "'" + stops.getstop_title() + "'," 
        + "'" + stops.getuser_id()+ "'" + ")";
      stmt.executeUpdate(query);
      boolean stop_found=false;
      JsonReturn="{\"stops\":[";
      stmt.close();
      con.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
