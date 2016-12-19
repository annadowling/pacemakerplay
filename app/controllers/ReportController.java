package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Activity;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import services.ActivityFilterService;
import views.html.*;

/**
 * Controller used for generating activity reports filtered by various
 * categories.
 * 
 * @author annadowling
 *
 */
public class ReportController extends Controller {
	String email = session().get("email");
	User user = User.findByEmail(email);

	/**
	 * Render Activity report view containing a list of all user activities.
	 * 
	 * @return Result
	 */
	public Result renderActivityReportPage() {
		if (user != null) {
			return ok(reports.render(user.activities));
		} else {
			return badRequest(login.render());
		}
	}

	/**
	 * Render Activity report view containing a list of all user activities
	 * filtered by date. Using method
	 * ActivityFilterService.filterActivityByDate()
	 * 
	 * @return Result
	 */
	public Result filterByDate() {
		List<Activity> filteredActivities = ActivityFilterService.filterActivityByDate(user.activities);
		return ok(reports.render(filteredActivities));
	}

	/**
	 * Render Activity report view containing a list of all user activities
	 * filtered by duration. Using method
	 * ActivityFilterService.filterActivityByDuration()
	 * 
	 * @return Result
	 */
	public Result filterByDuration() {
		List<Activity> filteredActivities = ActivityFilterService.filterActivityByDuration(user.activities);
		return ok(reports.render(filteredActivities));
	}

	/**
	 * Render Activity report view containing a list of all user activities
	 * filtered by distance. Using method
	 * ActivityFilterService.filterActivityByDistance()
	 * 
	 * @return Result
	 */
	public Result filterByDistance() {
		List<Activity> filteredActivities = ActivityFilterService.filterActivityByDistance(user.activities);
		return ok(reports.render(filteredActivities));
	}

	/**
	 * Render Activity report view containing a list of all user activities
	 * filtered by type. Using method
	 * ActivityFilterService.filterActivityByType()
	 * 
	 * @return Result
	 */
	public Result filterByType() {
		List<Activity> filteredActivities = ActivityFilterService.filterActivityByType(user.activities);
		return ok(reports.render(filteredActivities));
	}

	/**
	 * Render Activity report view containing a list of all user activities
	 * filtered by location. Using method
	 * ActivityFilterService.filterActivityByLocation()
	 * 
	 * @return Result
	 */
	public Result filterByLocation() {
		List<Activity> filteredActivities = ActivityFilterService.filterActivityByLocation(user.activities);
		return ok(reports.render(filteredActivities));
	}

}
