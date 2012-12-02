package hellobeans;
/******
This file should have the same
field names as the table.
We get values from the sql database in 
the Users table and set
values in the database using this java file 

**/
public class UserBean implements java.io.Serializable {
    /** the username and password */
    private int id;
    private String password;
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private boolean loggedIn;

    public UserBean() {	   /** default constructor */
    //Initializing all variables to empty string
	setid(0);
	setpassword("");
    setusername("");
    setfirst_name("");
    setlast_name("");
    setemail("");
    setphone("");
    setLoggedIn(false);

    }

    /* Note the getter/setter method names used here appear as JSP
     * property names without the get/set part (e.g. getuid() here
     * becomes just property uid in JSP).
     */

    /**
     * Get the user name
     */

/////////////////

    public String getusername(){
        return this.username;
    }

    public void setusername (String username){
        this.username = username;
    }

/////////////////
    public String getemail(){
        return this.email;
    }

    public void setemail (String email){
        this.email = email;
    }


//////////////////////////
    public String getfirst_name(){
        return this.first_name;
    }

    public void setfirst_name (String first_name){
        this.first_name = first_name;
    }
//////////////////
    public String getlast_name(){
        return this.last_name;
    }

    public void setlast_name (String last_name){
        this.last_name = last_name;
    }
//////////////////

///////////////////
    public String getphone(){
        return this.phone;
    }

    public void setphone (String phone){
        this.phone = phone;
    }

//////////////////
    public int getid() { 
        return this.id;
    }

    /**
     * Set the user name
     */
    public void setid(int id) {
        this.id = id;
    }
//////////////////
    /**
     * Get the password
     */
    public String getpassword() {
        return this.password;
    }

    /**
     * Set the password
     */
    public void setpassword(String password) {
        this.password = password;
    }
//////////////////
    /**
     * Set whether we are currently logged in or not
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Determine if we are currently logged in or not
     */
    public boolean getLoggedIn() {
        return loggedIn;
    }

}
