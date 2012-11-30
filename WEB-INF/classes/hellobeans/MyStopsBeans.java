package hellobeans;
/******
This file should have the same
field names as the table.
We get values from the sql database in 
the Users table and set
values in the database using this java file 

**/
public class MyStopsBeans implements java.io.Serializable {
    /** the username and password */
    private int id;
    private String routeid;
    private String route_title;
    private String runid;
    private String run_title;
    private String stopid;
    private String stop_title;
    private int user_id;

    public MyStopsBeans() {	   /** default constructor */
    //Initializing all variables to empty string
	setid(0);
	setrouteid("");
    setroute_title("");
    setrunid("");
    setrun_title("");
    setstopid("");
    setstop_title("");
    setuser_id(0);
    

    }

    /* Note the getter/setter method names used here appear as JSP
     * property names without the get/set part (e.g. getuid() here
     * becomes just property uid in JSP).
     */

    /**
     * Get the user name
     */

/////////////////

    public int getid(){
        return this.id;
    }

    public void setid (int id){
        this.id = id;
    }

/////////////////
    public String getrouteid(){
        return this.routeid;
    }

    public void setrouteid (String routeid){
        this.routeid = routeid;
    }


//////////////////////////
    public String getroute_title(){
        return this.route_title;
    }

    public void setroute_title (String route_title){
        this.route_title = route_title;
    }
//////////////////
    public String getrunid(){
        return this.runid;
    }

    public void setrunid (String runid){
        this.runid = runid;
    }
//////////////////
//////////////////
    public String getrun_title(){
        return this.run_title;
    }

    public void setrun_title (String run_title){
        this.run_title = run_title;
    }
//////////////////

//////////////////
    public String getstopid(){
        return this.stopid;
    }

    public void setstopid (String stopid){
        this.stopid = stopid;
    }
//////////////////

///////////////////
    public String getstop_title(){
        return this.stop_title;
    }

    public void setstop_title(String stop_title){
        this.stop_title = stop_title;
    }

//////////////////
    public int getuser_id() { 
        return this.user_id;
    }

    /**
     * Set the user name
     */
    public void setuser_id(int user_id) {
        this.user_id = user_id;
    }
//////////////////

//////////////////
}
