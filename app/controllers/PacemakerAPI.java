package controllers;

import static parsers.JsonParser.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class PacemakerAPI extends Controller
{  

  public Result  users()
  {
    List<User> users = User.findAll();
    return ok(renderUser(users));
  }

  public Result user(Long id)
  {
    User user = User.findById(id);  
    return user==null? notFound() : ok(renderUser(user)); 
  }

  public Result createUser()
  {
    User user = renderUser(request().body().asJson().toString());
    user.save();
    return ok(renderUser(user));
  }

  public Result deleteUser(Long id)
  {
    Result result = notFound();
    User user = User.findById(id);
    if (user != null)
    {
      user.delete();
      result = ok();
    }
    return result;
  }

  public Result deleteAllUsers()
  {
    User.deleteAll();
    return ok();
  }

  public Result updateUser(Long id)
  {
    Result result = notFound();
    User user = User.findById(id);
    if (user != null)
    {
      User updatedUser = renderUser(request().body().asJson().toString());
      user.update(updatedUser);
      user.save();
      result = ok(renderUser(user));
    }
    return result;
  }
  
  public Result activities (Long userId)
  {  
    User user = User.findById(userId);
    return ok(renderActivity(user.activities));
  }

  public Result createActivity (Long userId)
  { 
    User     user     = User.findById(userId);
    Activity activity = renderActivity(request().body().asJson().toString());  

    user.activities.add(activity);
    user.save();

    return ok(renderActivity(activity));
  }

  public Result activity (Long userId, Long activityId)
  {  
    User     user     = User.findById(userId);
    Activity activity = Activity.findById(activityId);

    if (activity == null)
    {
      return notFound();
    }
    else
    {
      return user.activities.contains(activity)? ok(renderActivity(activity)): badRequest();
    }
  }  

  public Result deleteActivity (Long userId, Long activityId)
  {  
    User     user     = User.findById(userId);
    Activity activity = Activity.findById(activityId);

    if (activity == null)
    {
      return notFound();
    }
    else
    {
      if (user.activities.contains(activity))
      {
        user.activities.remove(activity);
        activity.delete();
        user.save();
        return ok();
      }
      else
      {
        return badRequest();
      }

    }
  }  

  public Result updateActivity (Long userId, Long activityId)
  {
    User     user     = User.findById(userId);
    Activity activity = Activity.findById(activityId);

    if (activity == null)
    {
      return notFound();
    }
    else
    {
      if (user.activities.contains(activity))
      {
        Activity updatedActivity = renderActivity(request().body().asJson().toString());
        activity.distance = updatedActivity.distance;
        activity.location = updatedActivity.location;
        activity.activityType     = updatedActivity.activityType;

        activity.save();
        return ok(renderActivity(updatedActivity));
      }
      else
      {
        return badRequest();
      }
    }
  }
  
  public Result locations (Long activityId)
  {  
    Activity activity = Activity.findById(activityId);
    return ok(renderLocation(activity.route));
  }
  
  public Result createLocation (Long actvityId)
  { 
	 Activity activity = Activity.findById(actvityId);
    Location location = renderLocation(request().body().asJson().toString());  

    activity.route.add(location);
    activity.save();

    return ok(renderLocation(location));
  }
  
  public Result location (Long activityId, Long locationId)
  {  
    Activity activity = Activity.findById(activityId);
    Location location = Location.findById(locationId);

    if (location == null)
    {
      return notFound();
    }
    else
    {
      return activity.route.contains(location)? ok(renderLocation(location)): badRequest();
    }
  } 
  
  public Result deleteLocation (Long activityId, Long locationId)
  {  
	  Activity activity = Activity.findById(activityId);
	  Location location = Location.findById(locationId);

    if (location == null)
    {
      return notFound();
    }
    else
    {
      if (activity.route.contains(location))
      {
    	activity.route.remove(location);
        location.delete();
        activity.save();
        return ok();
      }
      else
      {
        return badRequest();
      }

    }
  } 
  
  public Result updateLocation (Long activityId, Long locationId)
  {
	  Activity activity = Activity.findById(activityId);
	  Location location = Location.findById(locationId);

    if (location == null)
    {
      return notFound();
    }
    else
    {
      if (activity.route.contains(location))
      {
        Location updatedLocation = renderLocation(request().body().asJson().toString());
        location.latitude = updatedLocation.latitude;
        location.longitude = updatedLocation.longitude;
        location.save();
        return ok(renderLocation(updatedLocation));
      }
      else
      {
        return badRequest();
      }
    }
  }
  
  public static Result followFriend(Long userId, Long friendId) {
	  Friends friendExists = Friends.findById(userId, friendId);
		Friends friend = null;
		if (friendExists == null) {
			friend = new Friends(userId, friendId);
			friend.save();
		}
		User user = new User();
		return ok(renderUser(user));
	}
  
	public static Result unfollowFriend(Long userId, Long friendId) {
		Friends currentUser = Friends.findById(userId, friendId);
		Friends friend = Friends.findById(friendId, userId);
		String result = "Not Deleted";
		if (currentUser != null && friend != null && currentUser.added == true) {
			currentUser.delete();
			friend.delete();
			result = "Deleted";
		}
		return ok(result);
	}
}
