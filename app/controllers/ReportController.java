package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Activity;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import services.ActivityFilterService;
import views.html.*;

public class ReportController extends Controller{
	String email = session().get("email");
    User user = User.findByEmail(email);
	
	public Result renderActivityReportPage()
	  {
		    if(user != null){
		    return ok(reports.render(user.activities));
		    }else{
		    	return badRequest(login.render());
		    }
	  }
	
	public Result filterByDate()
	  {
		List<Activity> filteredActivities = ActivityFilterService.filterActivityByDate(user.activities);
	    return ok(reports.render(filteredActivities));
	  }
	
	public Result filterByDuration()
	  {
		List<Activity> filteredActivities = ActivityFilterService.filterActivityByDuration(user.activities);
	    return ok(reports.render(filteredActivities));
	  }
	
	public Result filterByDistance()
	  {
		List<Activity> filteredActivities = ActivityFilterService.filterActivityByDistance(user.activities);
	    return ok(reports.render(filteredActivities));
	  }
	
	public Result filterByType()
	  {
		List<Activity> filteredActivities = ActivityFilterService.filterActivityByType(user.activities);
	    return ok(reports.render(filteredActivities));
	  }
	
	public Result filterByLocation()
	  {
		List<Activity> filteredActivities = ActivityFilterService.filterActivityByLocation(user.activities);
	    return ok(reports.render(filteredActivities));
	  }

}
