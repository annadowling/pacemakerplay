package controllers;

import java.util.Date;

import akka.event.slf4j.Logger;
import models.Activity;
import models.Location;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
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
			//Logger.(new Date() + " Invalid format for location " + latitude + ", " + longitude);
			result = redirect(routes.Dashboard.index());
		}
		return result;
  }
}
