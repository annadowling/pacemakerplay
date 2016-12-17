package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import play.*;
import play.mvc.*;
import play.mvc.Controller;
import models.Activity;
import models.Friends;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
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
		String email = session().get("email");
		User currentUser = User.findByEmail(email);
		List<Activity> usersActivities = currentUser.activities;
		Activity activityToDelete = Activity.findById(activityId);
		if (activityToDelete != null) {
			for(Activity activity: usersActivities){
				if(activity == activityToDelete){
					usersActivities.remove(activity);
				}
			}
			activityToDelete.delete();
		}
		return ok(manage_activities.render(currentUser.activities));
	}

}
