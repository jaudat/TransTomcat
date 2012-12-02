package hellobeans;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;    // need this for eg List, ArrayList

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp.datasources.*;
import javax.sql.DataSource;

public class Controller extends HttpServlet {

   private static final String DB_JDBC_DRIVER = "com.mysql.jdbc.Driver";
  private static final String DB_USER = "cscc09f12_30";
  private static final String DB_PWD = "athersucks";


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
        System.out.println("IN CONTROLLER");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/AddAccount");
        dispatcher.forward(request, response);
        }
    else if (action.equals("Login")){
      RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
        dispatcher.forward(request, response);
      }

    else if (action.equals("GetStops")){
      RequestDispatcher dispatcher = request.getRequestDispatcher("/GetStops");
        dispatcher.forward(request, response);
      }
      else if (action.equals("DeleteStops")){
      RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteStops");
        dispatcher.forward(request, response);
      }
      else if (action.equals("SaveStops")){
      RequestDispatcher dispatcher = request.getRequestDispatcher("/SaveStops");
        dispatcher.forward(request, response);
      }
      else {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bad_request.jsp");
        dispatcher.forward(request, response);
        }  
  }

     public void init() {

        Connection con; // to store the connection object
        int rowsEffected = 0;   // intended for error checking; not used here

        try {
                // initialize connection pool
                DriverAdapterCPDS ds = new DriverAdapterCPDS();
                ds.setDriver(DB_JDBC_DRIVER); // set the DB driver
                // ds.setUrl("jdbc:mysql:" + getServletContext().getInitParameter("dbUrl")); // set the url from dbUrl // FOR H2 DB
                ds.setUrl("jdbc:mysql:" + "//mathlab.utsc.utoronto.ca:3306/cscc09f12_30");
                ds.setUser(DB_USER);
                ds.setPassword(DB_PWD);

                SharedPoolDataSource dbcp = new SharedPoolDataSource();
                dbcp.setConnectionPoolDataSource(ds); // set the datasource for connection pool

                getServletContext().setAttribute("dbpool",dbcp); // make it available to all servlets
            }
            catch (Exception ex) {
                getServletContext().log("SQLGatewayPool Error: " + ex.getMessage());
        }


        try {
          String dbInit = getServletContext().getInitParameter("dbInit");

          if (dbInit.equals("true")) {  // dbInit context says to initialize DB
            System.out.println("dbinit");

            // get connection pool
            DataSource dbcp = (DataSource)getServletContext().getAttribute("dbpool");
            con = dbcp.getConnection();

            // Get a statement (used to issue SQL statements to the DB)
            Statement stmt = con.createStatement();

            // create Users table and drop existing
            String query = "DROP TABLE IF EXISTS Users";
            rowsEffected = stmt.executeUpdate(query);

            //query = "CREATE CACHED TABLE Users ("

            /*
             "CREATE TABLE Users (id MEDIUMINT AUTO_INCREMENT, username VARCHAR(16),password VARCHAR(16),first_name VARCHAR(64),email VARCHAR(255),phone VARCHAR(64),last_name VARCHAR(64),PRIMARY KEY (id))
                    
                    + "phone VARCHAR(64),"
                    + "last_name VARCHAR(64)"
                    + "PRIMARY KEY (id))"
            */
            query = "CREATE TABLE Users ("
                    //+ "id IDENTITY,"
                    + "id MEDIUMINT AUTO_INCREMENT,"
                    + "username VARCHAR(16),"
                    + "password VARCHAR(16),"
                    + "first_name VARCHAR(64),"
                    + "email VARCHAR(255),"
                    + "phone VARCHAR(64),"
                    + "last_name VARCHAR(64),"
                    + "PRIMARY KEY (id))";
            System.out.println(query);
            rowsEffected = stmt.executeUpdate(query);
            System.out.println("DEBUG: " + rowsEffected);

            // insert sample user
            query = "INSERT INTO Users (username, password,first_name,email,phone,last_name) VALUES ('testing','4Testing','Tom','tom@gmail.com','1-888-555-1212','Tester')";  
                      // "('testing','4Testing')";

            rowsEffected = stmt.executeUpdate(query);

            // create Playlist table and drop existing
            query = "DROP TABLE IF EXISTS MyStops";
            rowsEffected = stmt.executeUpdate(query);
            /*
                id  mediumint not null auto_increment
routeid varchar(3)
route_title varchar(255)
runid varchar(12)
run_title varchar(255)
stopid  varchar(12)
stop_title  varchar(255)
user_id bigint

            */

            //query = "CREATE CACHED TABLE Playlist ("
            query = "CREATE TABLE MyStops ("
                    //+ "id IDENTITY,"
                    + "id MEDIUMINT AUTO_INCREMENT,"
                    + "routeid VARCHAR(3),"
                    + "route_title VARCHAR(255),"
                    + "runid VARCHAR(12),"
                    + "run_title VARCHAR(255),"
                    + "stopid VARCHAR(12),"
                    + "stop_title VARCHAR(255),"
                    + "user_id MEDIUMINT,"
                    + "PRIMARY KEY (id) );";

            rowsEffected = stmt.executeUpdate(query);
            System.out.println("DEBUG: Controller init MyStops Table: " + rowsEffected);

            // should check to make sure table was created without an error

            stmt.close();
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








}//Controller Class ends here
