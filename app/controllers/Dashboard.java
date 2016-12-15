package controllers;

import static parsers.JsonParser.renderUser;

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
    return ok(dashboard_main.render(user.activities));
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
}
