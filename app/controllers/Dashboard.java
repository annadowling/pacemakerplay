package controllers;

import static parsers.JsonParser.renderUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import akka.event.slf4j.Logger;
import models.Activity;
import models.Location;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import utils.FriendUtils;
import views.html.*;

public class Dashboard extends Controller
{
	
  public Result index()
  {
    String email = session().get("email");
    User user = User.findByEmail(email);
    if(user != null){
    return ok(dashboard_main.render(user.activities));
    }else{
    	return badRequest(login.render());
    }
  }
  
  public Result uploadActivityForm()
  {
    return ok(dashboard_uploadactivity.render());
  }
  
  public Result getMap(){
	  return ok(map.render()); 
  }
  
  public Result manageLocations()
  {
    return ok(dashboard_managelocations.render());
  } 

  public Result submitActivity()
  {
	Form<Activity> boundForm = Form.form(Activity.class).bindFromRequest();    
    Activity activity = boundForm.get();
    
    if(boundForm.hasErrors()) 
    {
      return badRequest();
    }

    String email = session().get("email");
    User user = User.findByEmail(email);

    user.activities.add(activity);
    user.save();
    return redirect (routes.Dashboard.index());
  }
  
  public Result submitLocation(Long activityId)
  {
	    Result result = null;
		DynamicForm requestData = Form.form().bindFromRequest();
		String latitude = requestData.get("latitude");
		String longitude = requestData.get("longitude");
		if (latitude.matches("\\-?(?:\\d+\\.?\\d*|\\d*\\.?\\d+)")
				&& longitude.matches("\\-?(?:\\d+\\.?\\d*|\\d*\\.?\\d+)")) {
			float parsedLatitude = Float.parseFloat(latitude);
			float parsedLongitude = Float.parseFloat(longitude);
			Location location = new Location(parsedLatitude, parsedLongitude);
			Activity activity = Activity.findById(activityId);
			activity.route.add(location);
			activity.save();
			result = redirect(routes.Dashboard.index());
		} else {
			result = redirect(routes.Dashboard.index());
		}
		return result;
  }
  
  public Result  getAllUsers()
  {
    List<User> users = User.findAll();
    return ok(show_users.render(users));
  }
  
  public Result addFriend(Long friendId){
	  List<User> users = User.findAll();
	  String email = session().get("email");
	  User currentUser = User.findByEmail(email);
	  if(currentUser != null){
		  FriendUtils.followFriend(currentUser.id, friendId);
	  }
	  return showFriendsPage();
  }
  
  public Result unFriend(Long friendId){
	  String email = session().get("email");
	  User currentUser = User.findByEmail(email);
	  if(currentUser != null){
		  FriendUtils.unfollowFriend(currentUser.id, friendId);
	  }
	  return showFriendsPage();
  }
  
  public Result showFriendsPage()
  {
    String email = session().get("email");
    User currentUser = User.findByEmail(email);
    List<User> friendsOfCurrentUser = FriendUtils.showfriends(currentUser.id);
    return ok(show_friends.render(friendsOfCurrentUser));
  }
  
  public Result showFriendsPublicProfile(Long friendId){
	  User user = null;
	  if(friendId != null){
		  user = FriendUtils.findUserByfriendId(friendId);
	  }
	  return ok(public_profile.render(user.id));
	  
  }
  
  public Result renderManageActivitiesPage(){
	  String email = session().get("email");
	    User user = User.findByEmail(email);
	    if(user != null){
	    return ok(manage_activities.render(user.activities));
	    }else{
	    	return badRequest(login.render());
	    }  
  }
  
  public Result showEditActivitiesPage(Long activityId){
	  Activity activity = null;
	  if(activityId != null){
		  activity = Activity.findById(activityId);
	  }
	  return ok(edit_activity.render(activity.id));
	  
  }
  
  public Result renderManageLocationsPage(){
	  String email = session().get("email");
	    User user = User.findByEmail(email);
	    List<Location> routes = new ArrayList<Location>();
	    if(user != null){
	    List<Activity> activities = user.activities;
	    for(Activity activity: activities){
	    	routes.addAll(activity.route);
	    }  
	    return ok(manage_locations.render(routes));
	    }else{
	    	return badRequest(login.render());
	    }  
  }
  
  public Result showEditLocationsPage(Long locationId){
	  Location location = null;
	  if(locationId != null){
		  location = Location.findById(locationId);
	  }
	  return ok(edit_location.render(location.id));	  
  }
}
