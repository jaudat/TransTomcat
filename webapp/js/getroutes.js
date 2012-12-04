// https://mathlab.utsc.utoronto.ca/courses/cscc09f12/syedjaud/

/* Made changes by putting notworking validation in login
 * Changed to routedisp for storage in database
 * refresh button
 */

serviceURL_route="https://mathlab.utsc.utoronto.ca/courses/cscc09f12/rizvisy7/a2/routes.php?term=";
serviceURL_run="https://mathlab.utsc.utoronto.ca/courses/cscc09f12/rizvisy7/a2/getruns.php?id=";
serviceURL_stop="https://mathlab.utsc.utoronto.ca/courses/cscc09f12/rizvisy7/a2/stops.php"

serviceURL_predict = "http://www.utsc.utoronto.ca/~rosselet/cscc09f12/asn/services/getpredictions.php?id="
var disp_username="Alias";

var userid;
var stopRoutes = [];
var vehicleRoutes = [];


$('#all_routes').live('pageinit', function(event){
  geoLocation();
  $.getJSON(serviceURL_route,getRoutes);
});

$('#all_routes').live('pageshow', function(event){
  $('.usname').html("Logged In As "+localStorage.getItem("username"));
});
function getRoutes(data){
  disp_route=data.items;
  $('#list_routes').empty();
  $.each(disp_route, function(key,value) {
	  $('#list_routes').append('<li><a href="#route_runs" data-transition=\"slide\" class="btn runs" id="'+value.id+'" routedisp="'+value.display_name+'">' + value.display_name + '</a>'
	  + '<label> Map Stops <select name="stop-flip" class="stop-flip" data-role="slider">'
	  + '<option value="0;'+value.id+'">Off</option>'
	  + '<option value="1;'+value.id+'">On</option>'
      + '</select> </label>'
      + '<label> Map Vehicles <select name="vehicle-flip" class="vehicle-flip" data-role="slider">'
	  + '<option value="0;'+value.id+'">Off</option>'
	  + '<option value="1;'+value.id+'">On</option>'
      + '</select> </label></li>');
  });
  $('#list_routes').listview('refresh');
  $('.stop-flip').slider();
  $('.vehicle-flip').slider();
}


$('.stop-flip').live("change", function(event) { 
	var routeid = $(this).attr("value");
	var routelst = routeid.split(';');
	if (routelst[0] == 0) { 
		var index = stopRoutes.indexOf(routelst[1]);
		stopRoutes.splice(index,1);
		console.log("StopRoutes is "+ stopRoutes)
	}
	else {
		stopRoutes.push(routelst[1])
		console.log("StopRoutes is "+ stopRoutes)
	}
	//console.log("Value is" + routelst[0]);
	});
	
$('.vehicle-flip').live("change", function(event) { 
	var routeid = $(this).attr("value");
	var routelst = routeid.split(';');
	if (routelst[0] == 0) { 
		var index = vehicleRoutes.indexOf(routelst[1]);
		vehicleRoutes.splice(index,1);
		console.log("StopRoutes is "+ vehicleRoutes)
	}
	else {
		vehicleRoutes.push(routelst[1])
		console.log("StopRoutes is "+ vehicleRoutes)
	}
	//console.log("Value is" + routelst[0]);
	});
	
$('a.runs').live("click", function(event) {
  
  window['route_tag'] = $(this).attr('id'); 
  window['routedisp'] = $(this).attr('routedisp');

  $.getJSON(serviceURL_run+route_tag,getRuns); 
}); 

function getRuns(data){
  disp_branches=data.items;
  $('#runName').html("Route "+disp_branches[1].route_id)
  $('#list_runs').empty();
  $.each(disp_branches, function(key,value) {
    var createID = '?routeid='+value.route_id+'&runid='+value.id
    var runID = value.id
    var rundisp = value.display_name
    $('#list_runs').append('<li><a href="#run_stops" data-transition=\"slide\" class="btn stops" id="'+createID+'" runid="'+runID+'" rundisp="'+rundisp+'"route=\"'+value.route_id+'\" pole=\"'+value.direction_name+'\">'+value.display_name+'</a></li>');
  });
  $('#list_runs').listview('refresh'); 
}

$('a.stops').live("click", function(event) {
  var tag = $(this).attr('id');
  window['routez'] = $(this).attr('route');
 
  window['pole'] = $(this).attr('pole');
  $('#stopName').html('Route'+' '+routez+' '+pole)
  $.getJSON(serviceURL_stop+tag,getStops);
  //FoR ATHER- RUNS
  window['runid'] = $(this).attr('runid');
  window['rundisp'] = $(this).attr('rundisp');
  //console.log(window.runid)
  //console.log(window.rundisp)
  //// 
}); 

function getStops(data) {
  disp_stops=data.items;
  $('#list_stops').empty();

  $.each(disp_stops, function(key,value) {
    $('#list_stops').append('<li><label>'+value.display_name+'</label> <br/> <div class="ui-btn-left"><a href="#" class=\"btn save\" id=\"'+value.id+'\" stname=\"'+value.display_name+'\" stlong=\"'+value.longitude+'\" stlat=\"'+value.latitude+'\" branch=\"'+window.routez+'\" data-role="button" data-icon="star">Save</a></div><div class="ui-btn-right"><a data-transition=\"slide\" href="#stop_predictions" data-role="button" class="btn predict" id=\"'+value.id+' \" data-icon=\"arrow-r\">Predict Arrivals</a></div></li>'); 
  });
  $('#list_stops').listview('refresh'); 
  $('a.save').button();
  $('a.predict').button();
}

$('a.predict').live("click", function(event) {
	
  window['predictid'] = $(this).attr('id');
  
  $.getJSON(serviceURL_predict+window.predictid,getPredictions); 
}); 

$('#repredict').live("click", function(event) {

	$.getJSON(serviceURL_predict+window.predictid,getPredictions); 
}); 
function getPredictions(data) {
  disp_predictions=data.items;
  $('#list_predictions').empty();
  $.each(disp_predictions, function(key,value) {
    $('#list_predictions').append('<li><label>'+value.minutes+' minutes</label><font color=\"grey\"> '+value.run_id+'</font></li>'); 
  });
  $('#list_predictions').listview('refresh');
}

$('a.save').live("click",function(event) {
  var stopID = $(this).attr('id');
  var stopName = $(this).attr('stname');
  var stopLat = $(this).attr('stlat');
  var stopLong = $(this).attr('stlong');
  var routeID = $(this).attr('branch');
  
  
  $.getJSON("savestops.jsp?routeid="+routeID +"&run_title="+window.rundisp+"&runid="+window.runid+"&stopid="+stopID+"&stoptitle="+stopName+"&routetitle="+window.routedisp);
  });

function getLocalDict(key) {
  var localD = localStorage[key];
  if (localD == null) {
    localD = {}; //return empty localD
  }
  else {
    localD = JSON.parse(localD); //retrieve the id in JSON format from //local storage
  };
  return localD;
}

$('#saved_stops').live('pageshow',function(event) {
 
  getSaves();
}); 

function getSaves() {
 
  if (localStorage.getItem("userid") <= 0) {
    $.mobile.loadPage("index.html");
    $.mobile.changePage("#login");
  }
  else {
    console.log("entered getSaves");
    $('#list_saves').empty();
    $.getJSON("getstops.jsp",function(data){
        var stops=data.stops;
       
      $.each(stops, function(key, disp_stop) {
         
         $('#list_saves').append('<li><label>'+disp_stop.stop_title+'</label><br/><div class="ui-btn-left"><a data-transition=\"slide\" href="#stop_predictions" data-role="button" class="btn predict" data-icon="arrow-r" id=\"'+disp_stop.stopid+' \">Predictions</a></div><div class="ui-btn-left"><a href=\"#\" data-icon=\"delete\" data-theme=\"b\" class=\"btn delete\" data-role=\"button\" stopid=\"'+disp_stop.stopid+' \">delete</a></div></li>')
      });
    });
    $('#list_saves').listview('refresh');
    $('a.predict').button();
    $('a.delete').button();
  } 
}

$('a.delete').live("click", function(event) {
  var stopid=$(this).attr('stopid');
  console.log(stopid);
  $.getJSON("delete.jsp?stopid="+stopid);


  $(this).closest('li.ui-li').remove();
  $('#list_saves').listview('refresh');
})

$('#signin').live("click", function(event) {
 
		$.getJSON("login.jsp?username="+username.value+"&password="+password.value,function(data){
		  var login = data.login;

		  console.log((login[0].username+" "+login[0].userid));

		  userid = login[0].userid;
		  disp_username=login[0].username;

		  localStorage.setItem("userid", userid);
		  localStorage.setItem("username", disp_username);

		  if (userid>0){

			$('.login').html("LogOut");
      $('a.login').addClass('logout');
      $('a.logout').removeClass('login');
			$.mobile.loadPage("index.html");
			$.mobile.changePage("#all_routes");

		  }else if((userid==0)) {
			$('#status').html('<h3 style="color:red">Login Failed, Please Try Again</h3>');
		  }

		}); 
	
}); 

$('.logout').live("click", function(event) {

  localStorage.setItem("username","Alias");
  localStorage.setItem("userid","0");
  $.mobile.loadPage("index.html");
  $.mobile.changePage("#all_routes");
  $.getJSON("logout.jsp");
  $('.logout').html("Login");
  $('a.logout').addClass('login');
  $('a.login').removeClass('logout');     

}); 

// Asst 3 map-page functionality 
var lat, lon = null;

/* Use HTML5 geolocation capability to provide location-based service */
function geoLocation() {
    if (navigator.geolocation) {  // attempt to get user's geoLocation
        navigator.geolocation.getCurrentPosition (function(position) {
          lat = position.coords.latitude;
          lon = position.coords.longitude;
        });
    }
    else {  // centre on UTSC if user has no geolocation or declines to reveal it
          lat = 43.78646;
          lon = -79.1884399;
    }
    if (lat==null || lon==null) {  // wait until geoLocation determined
	setTimeout(geoLocation, 500);
    }
    else {   // got geoLocation, now draw the map
	drawMap();
    }
}

var map = null;  // google map object
var stopsNearMe = [];  // stops within 1km of user's geolocation
var route_icons = ["img/blue.gif", "img/red.gif", "img/green.gif",
		"img/yellow.gif", "img/purple.gif", "img/cyan.gif",
		"img/black.gif", "img/orange.gif", "img/white.gif"];
// use dictionary for bus icons, so can look up by run direction 
var bus_icons = {"North": "img/bus_north.jpg", "East": "img/bus_east.jpg",
		"South": "img/bus_south.jpg", "West": "img/bus_west.jpg"}; 
    
var PHP_URL = "https://www.utsc.utoronto.ca/~rosselet/cscc09f12/asn/services/";

/* draw the map, and add overlay markers for user-selected information */
function drawMap() {
    map = new google.maps.Map(document.getElementById('map'), {
      zoom: 15,
      center: new google.maps.LatLng(lat, lon),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    mapStops(stopsNearMe);  // add markers for stops near user
    //var stopRoutes = [510, 509];
    mapRouteStops(stopRoutes);  // add markers for user-selected route stops
    //var vehicleRoutes = [7, 501];
    mapVehicles(vehicleRoutes);  // add markers for user-selected route vehicles
    // refresh vehicle markers every 30 seconds to show updated vehicle positions
    setInterval(function() { mapVehicles(vehicleRoutes); }, 30000); 
};

/* add map markers for parameter list of stops with geolocation near the user */
function mapStops(stopList) {
    var infowindow = new google.maps.InfoWindow();
	$.each(stopList, function(index, stop) {
            var i;
	    /* place map markers for parameter stops - make them clickable to
	       obtain predicted arrival times for clicked stop */
	    text = stop.display_name + ', route: ' 
			+ stop.routeid + ', direction: ' + stop.run_display_name
			+ ', distance: ' + (stop.distance*1.61*1000).toFixed(0) + 'm';
            var marker = new google.maps.Marker({
                position: new google.maps.LatLng(stop.latitude, stop.longitude),
            	map: map,
                title: text,
		icon: "img/me.gif"  // use a distinct icon for nearby stops
            });
    
            google.maps.event.addListener(marker, 'click', (function(marker) {
                return function() {
		  // Obtain real-time prediction data for user-clicked stop
		  $.getJSON(PHP_URL + 'getpredruns.php?id=' + stop.id, function(data) {
		      // load content of map info-window that opens on click
                      var predList = '<span>' + stop.display_name
                            + '<br/>Vehicles arriving in: ';
                      $.each(data.items, function(index, prediction) {
	    	          var dirName;  // name to display for vehicle direction
	    	          // some vehicles have no run display_name, or their run # has no defined name
	    	          if (prediction.run_name == null || prediction.run_name == undefined) {
	    		      dirName = "#"+prediction.run_id;
	    	          }
	    	          else {  // have a run name, so display it
	    		      dirName = prediction.run_name; 
	    	          };
                          predList += prediction.minutes + ' (' + dirName + '), ';
                      });
                      predList += ' minutes</span>';
                      infowindow.setContent(predList);
                      infowindow.open(map, marker);
                  });       // getJSON prediction data
               }
           })(marker));
       });
};

/* add markers for stops on the routes in parameter routeList */
function mapRouteStops(routeList) {
    var infowindow = new google.maps.InfoWindow();
    // iterate through each route in routeList
    $.each(routeList, function(index, route) {
      // for each route, retrieve its run-list
      $.getJSON(PHP_URL + "getruns.php?id=" + route, function(data) {
        $.each(data.items, function(runidx, run) {
	  // for each run, retrieve its stop-list
	  $.getJSON(PHP_URL + "getstops.php?routeid=" + route + "&runid=" 
						+ run.id, function(data) {
	    // for each stop, create a marker with a run-coded icon
            $.each(data.items, function(index, stop) {
              var i;
	      /* place markers on map for nearby stops - make them clickable to
	         obtain predicted arrival times for clicked stop */
	      var icon = route_icons[runidx];
	      text = "Stop: " + stop.display_name + ', Route: ' 
			+ route + ', Direction: ' + run.display_name;
              var marker = new google.maps.Marker({
                position: new google.maps.LatLng(stop.latitude, stop.longitude),
            	map: map,
                title: text,
		icon: icon
              });
    
              google.maps.event.addListener(marker, 'click', (function(marker) {
                return function() {
		  // Obtain real-time prediction data for user-clicked stop
		  $.getJSON(PHP_URL + 'getpredruns.php?id=' + stop.id, function(data) {
		    if (data.items != null) {
		      // load content of map info-window that opens on click
                      var predList = '<span>' + stop.display_name
                            + '<br/>Vehicles arriving in: ';
                      $.each(data.items, function(index, prediction) {
                          predList += 
                            prediction.minutes + ' (' + prediction.run_name + '), ';
                      });
                      predList += ' minutes</span>';
                      infowindow.setContent(predList);
		    }
		    else {
			infowindow.setContent("Sorry, no prediction data available at this time; please try again later");
		    }
                    infowindow.open(map, marker);
                  });       // getJSON prediction data
                }
             })(marker));
         });  // each stop
       });  // getJSON stops
     });  // each run
   });  // getJSON runs
 });  // each route
};

var markerArray = [];  // remember vehicle markers, so we can remove/refresh them

/* add markers for vehicles on the routes in parameter routeList */
function mapVehicles(routeList) {
    /* clear current vehicle markers before updating with new ones, else end
	up with vehicle markers in old and new locations. */
    while (markerArray.length > 0) {
          var m =  markerArray.pop();
          m.setMap(null);
    }
    /* add vehicle markers for user-selected routeList */
    $.each(routeList, function(index, route) {
      /* retrieve location data for vehicles on this route */
      $.getJSON(PHP_URL + "getvehruns.php?id=" + route, function(data) {
        var vehList = data.items;  // extract the list of vehicles
	/* create a marker for each vehicle */
	$.each(vehList, function(index, vehicle) {
            var marker;
	    var dirName;  // name to display for vehicle direction
	    // some vehicles have no run names, or their run # has no defined name
	    if (vehicle.run_name == null || vehicle.run_name == undefined) {
		dirName = "#"+vehicle.run_id;
	    }
	    else {  // have a run name, so display it
		dirName = vehicle.run_name; 
	    };
	    text = 'direction: ' + dirName + ', vehicle#: ' + vehicle.id;
    	    marker = new google.maps.Marker({
                position: new google.maps.LatLng(vehicle.latitude, vehicle.longitude),
                map: map,
                title: text,
		icon: bus_icons[vehicle.dir_name] 
            });
	    marker.setMap(map);
	    markerArray.push(marker);
	});
      });
    });
};

var MY_URL = "https://mathlab.utsc.utoronto.ca/courses/cscc09f12/syedjaud/";

$('a.map_me').live("click", function(event) {
    /* stopsnearme.php extracts stops near user geolocation from Stops table
	and Runs table (run_display_name) */
    $.getJSON(MY_URL + "nearme.php?lat=" + lat + "&lon=" + lon, function(data) {
        stopsNearMe = data.items;  // extract the list of nearby stops
	mapStops(stopsNearMe);
    });
    $.mobile.changePage('#mapPage');
});

$('#mapPage').live("pageshow", function(event) {
    drawMap();  // redraw the map when page is shown
});

