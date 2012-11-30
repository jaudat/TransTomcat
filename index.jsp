<!DOCTYPE html>

<html>
  <head>
    <title>iTrans</title>
    <meta charset="UTF-8"/>  
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.1.1/jquery.mobile-1.1.1.min.css"/> 
    <!--<link rel="stylesheet" href="themes/themes/itrans.min.css"/> -->
    <script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.1.1/jquery.mobile-1.1.1.min.js"></script>
    <script src="webapp/js/getroutes.js"></script>
  </head>	
  <body>
    <!-- Page containing the list of routes -->
    <div id="all_routes" data-role="page" data-theme="c">
      <!-- Header containing the routes title, and the login and search button -->
      <div data-role="header" data-position="fixed" data-add-back-btn="true">
        <h1> Routes</h1>
	<div class="ui-btn-right">
	  <a href="#" data-role="button" data-icon="search">Me</a>
	  <a href="#" data-role="button" data-icon="gear" >Login</a>
	</div>	
      </div>
      <!-- Displays the content in the middle containing a filter and list of results -->
      <div id="disp_routes" data-role="content">
        <p>Select a Route to View Directions</p>
        <ul id="list_routes" data-role="listview" data-theme="b" data-inset="true" data-filter="true" data-filter-placeholder="Enter route name or number"> 
        </ul>
	<!-- Use Javascript to insert the filtered result here -->
      </div>
      <!-- The footer containing the remaining buttons with links to Routes, Stops etc. -->
      <div data-role="footer" data-position="fixed">
        <div data-role="navbar">
          <ul> 
	    <li><a href="#all_routes" data-transition="slideup" data-icon="home">Routes</a></li>
	    <li><a href="#saved_stops" data-transition="slideup" data-icon="arrow-u" class="btn mystops">My Stops</a></li>
	    <li><a href="#" data-icon="grid">Map</a></li>
	    <li><a href="#" data-icon="info">Info</a></li>
	  </ul>
        </div>
      </div>
    </div>

    <!-- -->
    <div id="route_runs" data-role="page" data-theme="c">
      <!-- Header containing the routes title, and the login and search button -->
      <div data-role="header" data-position="fixed">
        <h1 id="runName"></h1>
	<div class="ui-btn-right">
          <a href="#" data-role="button" data-icon="search">Me</a>
          <a href="#" data-role="button" data-icon="gear" >Login</a>
	</div>	
      </div>
      <!-- Displays the content in the middle containing a filter and list of results -->
      <div id="disp_runs" data-role="content">
        <p>Select a Direction to View Stops</p>
	<ul id="list_runs" data-role="listview" data-theme="b" data-inset="true" data-filter="true" data-filter-placeholder="Enter route name or number">
  	  <!--Use Javascript to insert the filtered result here -->
        </ul>
      </div>
      <!-- The footer containing the remaining buttons with links to Routes, Stops etc. -->
      <div data-role="footer" data-position="fixed">
        <div data-role="navbar">
	  <ul> 
	    <li><a href="#all_routes" data-transition="slideup" data-icon="home">Routes</a></li>
	    <li><a href="#saved_stops" data-icon="arrow-u" data-transition="slideup" class="btn mystops">My Stops</a></li>
	    <li><a href="#" data-icon="grid">Map</a></li>
	    <li><a href="#" data-icon="info">Info</a></li>
	  </ul>
        </div>
      </div>
    </div>

    <div id="run_stops" data-role="page" data-theme="c">
      <!-- Header containing the routes title, and the login and search button -->
      <div data-role="header" data-position="fixed">
        <h1 id="stopName"></h1>
	<div class="ui-btn-right">
	  <a href="#" data-role="button" data-icon="search">Me</a>
	  <a href="#" data-role="button" data-icon="gear" >Login</a>
	</div>	
      </div>
      <!-- Displays the content in the middle containing a filter and list of results -->
      <div id="disp_stops" data-role="content">
        <!-- <p>Select a Route to View Directions</p> -->
        <ul id="list_stops" data-role="listview" data-theme="b" data-inset="true" data-filter="true" data-filter-placeholder="Enter route name or number">
  	  <!-- Use Javascript to insert the filtered result here -->
        </ul>
      </div>
      <!-- The footer containing the remaining buttons with links to Routes, Stops etc. -->
      <div data-role="footer" data-position="fixed">
        <div data-role="navbar">
	  <ul> 
	    <li><a href="#all_routes" data-icon="home" data-transition="slideup">Routes</a></li>
	    <li><a href="#saved_stops" data-icon="arrow-u" class="btn
	    mystops" data-transition="slideup">My Stops</a></li>
	    <li><a href="#" data-icon="grid">Map</a></li>
	    <li><a href="#" data-icon="info">Info</a></li>
	  </ul>
        </div>
      </div>
    </div>

    <div id="stop_predictions" data-role="page" data-theme="c">
      <!-- Header containing the routes title, and the login and search button -->
      <div data-role="header" data-position="fixed">
        <h1> Arrivals </h1>
	<div class="ui-btn-right">
	  <a href="#" data-role="button" data-icon="search">Me</a>
	  <a href="#" data-role="button" data-icon="gear" >Login</a>
	</div>	
      </div>
      <!-- Displays the content in the middle containing a filter and list of results -->
      <div id="disp_predictions" data-role="content">
        <!-- <p>Select a Route to View Directions</p> -->
        <ul id="list_predictions" data-role="listview" data-theme="b" data-inset="true" data-filter="true" data-filter-placeholder="Enter route name or number">
  	  <!-- Use Javascript to insert the filtered result here -->
        </ul>
      </div>
      <!-- The footer containing the remaining buttons with links to Routes, Stops etc. -->
      <div data-role="footer" data-position="fixed">
        <div data-role="navbar">
	  <ul> 
	    <li><a href="#all_routes" data-icon="home" data-transition="slideup">Routes</a></li>
	    <li><a href="#saved_stops" data-icon="arrow-u" class="btn
	    mystops" data-transition="slideup">My Stops</a></li>
	    <li><a href="#" data-icon="grid">Map</a></li>
	    <li><a href="#" data-icon="info">Info</a></li>
	  </ul>
        </div>
      </div>
    </div>

    <div id="saved_stops" data-role="page" data-theme="c">
      <!-- Header containing the routes title, and the login and search button -->
      <div data-role="header" data-position="fixed">
        <h1> My Stops </h1>
	<div class="ui-btn-right">
	  <a href="#" data-role="button" data-icon="search">Me</a>
	  <a href="#" data-role="button" data-icon="gear" >Login</a>
	</div>	
      </div>
      <!-- Displays the content in the middle containing a filter and list of results -->
      <div id="disp_saves" data-role="content">
        <!-- <p>Select a Route to View Directions</p> -->
        <ul id="list_saves" data-role="listview" data-theme="b" data-inset="true" data-filter="true" data-filter-placeholder="Enter route name or number">
  	  <!-- Use Javascript to insert the filtered result here -->
        </ul>
      </div>
      <!-- The footer containing the remaining buttons with links to Routes, Stops etc. -->
      <div data-role="footer" data-position="fixed">
        <div data-role="navbar">
	  <ul> 
	    <li><a href="#all_routes" data-icon="home" data-transition="slideup">Routes</a></li>
	    <li><a href="#saved_stops" data-icon="arrow-u" class="btn
	    mystops" data-transition="slideup" >My Stops</a></li>
	    <li><a href="#" data-icon="grid">Map</a></li>
	    <li><a href="#" data-icon="info">Info</a></li>
	  </ul>
        </div>
      </div>
    </div>


    <div id="login" data-role="page" data-theme="c">
      <!-- Header containing the routes title, and the login and search button -->
      <div data-role="header" data-position="fixed">
        <h1> Login </h1>
  <div class="ui-btn-right">
    <a href="#" data-role="button" data-icon="search">Me</a>
    <a href="#" data-role="button" data-icon="gear" >Login</a>
  </div>  
      </div>
      <!-- Displays the content in the middle containing a filter and list of results -->
      <div id="disp_saves" data-role="content">
        <!-- <p>Select a Route to View Directions</p> -->
        <form action="confirm.html" method="get">
          User Name: <input type="text" name="usr_name" placeholder="Enter a username" required> <br>
          Password: <input type="text" name="pass" required> <br>
          <a href="index.html" data-role="button" data-icon="delete">Login Now</a>
          <a href="#register" data-role="button" data-icon="delete">Register Now</a>
        </form>
      
      </div>
      <!-- The footer containing the remaining buttons with links to Routes, Stops etc. -->
      <div data-role="footer" data-position="fixed">
        <div data-role="navbar">
    <ul> 
      <li><a href="#all_routes" data-icon="home" data-transition="slideup">Routes</a></li>
      <li><a href="#saved_stops" data-icon="arrow-u" class="btn
      mystops" data-transition="slideup" >My Stops</a></li>
      <li><a href="#" data-icon="grid">Map</a></li>
      <li><a href="#" data-icon="info">Info</a></li>
    </ul>
        </div>
      </div>
    </div>



       <div id="register" data-role="page" data-theme="c">
      <!-- Header containing the routes title, and the login and search button -->
      <div data-role="header" data-position="fixed">
        <h1> Register Now </h1>
  <div class="ui-btn-right">
    <a href="#" data-role="button" data-icon="search">Me</a>
    <a href="#" data-role="button" data-icon="gear" >Login</a>
  </div>  
      </div>
      <!-- Displays the content in the middle containing a filter and list of results -->
      <div id="disp_saves" data-role="content">
        <!-- <p>Select a Route to View Directions</p> -->
        <form action="/itransBeans/add_acct.jsp" method="get">
          User Name: <input type="text" name="username" placeholder="Enter a username" required> <br>
          Password: <input type="text" name="password" required> <br>
          First Name: <input type="text" name="first_name" placeholder="Enter a username" required> <br>
          Last Name: <input type="text" name="last_name" placeholder="Enter a username" required> <br>
          User Name: <input type="text" name="username" placeholder="Enter a username" required> <br>
          Email: <input type="email" name="email" placeholder="Enter email id" required pattern="[^ @]*@[^ @]*" > <br>
          Phone Number: <input type="text" name="phone" placeholder="Enter a valid Phone number" required> <br>
         
          <a href="index.html" data-role="button" data-icon="delete">Login Now</a>
          <input type="submit" value="submit">
        </form>
      
      </div>
      <!-- The footer containing the remaining buttons with links to Routes, Stops etc. -->
      <div data-role="footer" data-position="fixed">
        <div data-role="navbar">
    <ul> 
      <li><a href="#all_routes" data-icon="home" data-transition="slideup">Routes</a></li>
      <li><a href="#saved_stops" data-icon="arrow-u" class="btn
      mystops" data-transition="slideup" >My Stops</a></li>
      <li><a href="#" data-icon="grid">Map</a></li>
      <li><a href="#" data-icon="info">Info</a></li>
    </ul>
        </div>
      </div>
    </div>

  </body>
</html>
