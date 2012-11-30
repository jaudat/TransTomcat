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

$('#all_routes').live('pageinit', function(event){
	$.getJSON(serviceURL_route,getRoutes);
});

$('#all_routes').live('pageshow', function(event){
  $('.usname').html("Logged In As "+localStorage.getItem("username"));
});
function getRoutes(data){
  disp_route=data.items;
  $('#list_routes').empty();
  $.each(disp_route, function(key,value) {
	  $('#list_routes').append('<li><a href="#route_runs" data-transition=\"slide\" class="btn runs" id="'+value.id+'" routedisp="'+value.display_name+'">' + value.display_name + '</a></li>');
  });
  $('#list_routes').listview('refresh');
}

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
