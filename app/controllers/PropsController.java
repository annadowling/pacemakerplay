package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.*;
import play.mvc.*;
import play.mvc.Controller;
import models.Activity;
import models.Friends;
import models.Location;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import utils.FriendUtils;
import views.html.*;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Inject;

public class PropsController extends Controller{
	
	public Result editActivity(Long activityId) {
		Result result = null;
		DynamicForm requestData = Form.form().bindFromRequest();
		String location = requestData.get("location");
		String activityType = requestData.get("activityType");
		String distance = requestData.get("distance");
		String date = requestData.get("date");
		String duration = requestData.get("duration");
		Activity activityToEdit = Activity.findById(activityId);
        if(location != null && !location.equals("")){
        	activityToEdit.location = location;
        }
        if(activityType != null & !activityType.equals("")){
        	activityToEdit.activityType = activityType;
        }
        if(distance != null && !distance.equals("")){
        	activityToEdit.distance = Double.parseDouble(distance);
        }
        if(date != null && !date.equals("")){
        	try {
        		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            	Date parsedDate = (Date)formatter.parse(date); 
            	activityToEdit.date = parsedDate;
        	} catch (ParseException e) {
        	   e.printStackTrace();
        	  }
        }
        if(duration != null && !duration.equals("")){
        	activityToEdit.duration = Double.parseDouble(duration);
        }
        activityToEdit.save();
        result = ok(edit_activity.render(activityToEdit.id));
        return result;
	}
	

	public Result deleteActivity(Long activityId) {
		Activity activityToDelete = Activity.findById(activityId);
		if (activityToDelete != null) {
			activityToDelete.delete();
		}
		return showActivitiesPage();
	}
	
	public Result deleteLocation(Long locationId) {
		Location locationToDelete = Location.findById(locationId);
		if (locationToDelete != null) {
			locationToDelete.delete();
		}
		return showLocationsPage();
	}
	
	public Result showActivitiesPage()
	  {
	    String email = session().get("email");
	    User currentUser = User.findByEmail(email);
	    List<Activity> usersActivities = currentUser.activities;
	    return ok(manage_activities.render(usersActivities));
	  }
	
	public Result showLocationsPage()
	  {
	    String email = session().get("email");
	    User currentUser = User.findByEmail(email);
	    List<Activity> usersActivities = currentUser.activities;
	    List<Location> routes = new ArrayList<Location>();
	    for(Activity activity: usersActivities){
	    	routes.addAll(activity.route);
	    } 
	    return ok(manage_locations.render(routes));
	  }
	
	public Result editLocation(Long locationId)
	  {
		    Result result = null;
			DynamicForm requestData = Form.form().bindFromRequest();
			Location location = Location.findById(locationId);
			String latitude = requestData.get("latitude");
			String longitude = requestData.get("longitude");
			if (latitude.matches("\\-?(?:\\d+\\.?\\d*|\\d*\\.?\\d+)")
					&& longitude.matches("\\-?(?:\\d+\\.?\\d*|\\d*\\.?\\d+)")) {
				if(location != null){
					location.latitude = Float.parseFloat(latitude);
					location.longitude = Float.parseFloat(longitude);	
				}
				location.save();
			} 
			result = ok(edit_location.render(location.id));
			return result;
	  }

}
