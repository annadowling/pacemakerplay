package controllers;

import static parsers.JsonParser.renderUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;

import akka.event.slf4j.Logger;
import models.Activity;
import models.Friends;
import models.Location;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import services.FriendUtils;
import views.html.*;

/**
 * Controller manages the rendering of the feature pages throughout the application.
 * @author annadowling
 */
public class Dashboard extends Controller
{
	 
	 public User getCurrentUser(){
		 String email = session().get("email");;
		 User currentUser = User.findByEmail(email);
		 return currentUser;
		 
	 }
	
	/**
	 * Gets the currently logged in user from session() and renders their activities to the dashboard landing page.
	 * @return Result
	 */
  public Result index()
  {
    if(getCurrentUser() != null){
    return ok(dashboard_main.render(getCurrentUser().activities));
    }else{
    	return badRequest(login.render());
    }
  }
  
  /**
   * Renders the activity creation form page
   * @return Result
   */
  public Result uploadActivityForm()
  {
    return ok(dashboard_uploadactivity.render());
  }
  
  /**
   * Renders the google maps page
   * @return Result
   */
  public Result getMap(){
	  return ok(map.render()); 
  }
  
  /**
   * Renders the location management page for editing and deleting locations
   * @return Result
   */
  public Result manageLocations()
  {
    return ok(dashboard_managelocations.render());
  } 

  /**
   * Retrieves activity values from the form and creates the activity from these values.
   * Activity is then added to the currentUsers list of activities.
   * @return Result redirect to activity dashboard
   */
  public Result submitActivity()
  {
	Form<Activity> boundForm = Form.form(Activity.class).bindFromRequest();    
    Activity activity = boundForm.get();
    
    if(boundForm.hasErrors()) 
    {
      return badRequest();
    }

    getCurrentUser().activities.add(activity);
    getCurrentUser().save();
    return redirect (routes.Dashboard.index());
  }
  
  /**
   * Retrieves location values from the form and creates the location from these values.
   * Location is then added to the currently selected activity as a route.
   * @return Result redirect to activity dashboard
   */
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
 
  /**
   * Retrieves all users currently registered.
   * Renders the list of users to the show users page
   * @return Result 
   */
  public Result  getAllUsers()
  {
    List<User> users = User.findAll();
    return ok(show_users.render(users));
  }
  
  /**
   * Retrieves all users currently registered.
   * Adds an entry for the currentUser and the selected friend to the Friends model using the FriendUtils method: followFriend().
   * @param friendId
   * @return Result showFriendsPage();
   */
  public Result addFriend(Long friendId){
	  List<User> users = User.findAll();
	  if(getCurrentUser() != null){
		  FriendUtils.followFriend(getCurrentUser().id, friendId);
	  }
	  return showFriendsPage();
  }
  
  /**
   * Retrieves all pending friend requests for the logged in user using FriendUtils.getAllPendingFollowRequests()
   * Renders this list of users to the follow requests page.
   * @return Result 
   */
  public Result showPendingFollowRequests(){
	  List<Friends> pendingFriends = FriendUtils.getAllPendingFollowRequests(getCurrentUser().id);
	  List<User> usersToAccept = new ArrayList<User>();
	  for(Friends friendToAccept: pendingFriends){
		  User userToAdd = User.findById(friendToAccept.userId);
		  usersToAccept.add(userToAdd);
	  }
	  return ok(pending_follow_requests.render(usersToAccept));
  }
  
  /**
   * Accepts the selected follow request for the currently logged in user using FriendUtils.acceptFollowRequest().
   * @param friendId
   * @return Result 
   */
  public Result acceptFollowRequest(Long friendId){
	  if(getCurrentUser() != null){
		FriendUtils.acceptFollowRequest(getCurrentUser().id, friendId);  
	  }
	  return showPendingFollowRequests();
  }
  
  /**
   * Deletes the selected friendship entry using FriendUtils.unfollowFriend().
   * @param friendId
   * @return Result 
   */
  public Result unFriend(Long friendId){
	  if(getCurrentUser() != null){
		  FriendUtils.unfollowFriend(getCurrentUser().id, friendId);
	  }
	  return showFriendsPage();
  }
  
  /**
   * Renders the friends list for the currently logged in user using FriendUtils.showfriends().
   * @return Result 
   */
  public Result showFriendsPage()
  {
    List<User> friendsOfCurrentUser = FriendUtils.showfriends(getCurrentUser().id);
    return ok(show_friends.render(friendsOfCurrentUser));
  }
  
  /**
   * Renders the public profile of friends for the currently logged in user using FriendUtils.findUserByfriendId().
   * @param friendId
   * @return Result 
   */
  public Result showFriendsPublicProfile(Long friendId){
	  User user = null;
	  if(friendId != null){
		  user = FriendUtils.findUserByfriendId(friendId);
	  }
	  return ok(public_profile.render(user.id));
	  
  }
  
  /**
   * Renders the activity management page with a list of current users activities.
   * @return Result 
   */
  public Result renderManageActivitiesPage(){
	    if(getCurrentUser() != null){
	    return ok(manage_activities.render(getCurrentUser().activities));
	    }else{
	    	return badRequest(login.render());
	    }  
  }
  
  /**
   * Renders the activity edit form page for the selected activity.
   * @param activityId
   * @return Result 
   */
  public Result showEditActivitiesPage(Long activityId){
	  Activity activity = null;
	  if(activityId != null){
		  activity = Activity.findById(activityId);
	  }
	  return ok(edit_activity.render(activity.id));
	  
  }
 
  /**
   * Renders the location management page with a list of current users activities routes.
   * @return Result 
   */
  public Result renderManageLocationsPage(){
	    List<Location> routes = new ArrayList<Location>();
	    if(getCurrentUser() != null){
	    List<Activity> activities = getCurrentUser().activities;
	    for(Activity activity: activities){
	    	routes.addAll(activity.route);
	    }  
	    return ok(manage_locations.render(routes));
	    }else{
	    	return badRequest(login.render());
	    }  
  }
  
  /**
   * Renders the location edit form page for the selected location.
   * @param locationId
   * @return Result 
   */
  public Result showEditLocationsPage(Long locationId){
	  Location location = null;
	  if(locationId != null){
		  location = Location.findById(locationId);
	  }
	  return ok(edit_location.render(location.id));	  
  }
}
