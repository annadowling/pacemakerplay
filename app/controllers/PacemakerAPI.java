package controllers;

import static parsers.JsonParser.*;
import play.mvc.*;

import java.util.*;

import models.*;

/**
 * API controller containing methods for interacting with CRUD activities on the
 * models.
 * 
 * @author annadowling
 */
public class PacemakerAPI extends Controller {

	/**
	 * Returns a list of all registered users.
	 * 
	 * @return Result
	 */
	public Result users() {
		List<User> users = User.findAll();
		return ok(renderUser(users));
	}

	/**
	 * Returns a user by id.
	 * 
	 * @param id
	 * @return Result
	 */
	public Result user(Long id) {
		User user = User.findById(id);
		return user == null ? notFound() : ok(renderUser(user));
	}

	/**
	 * Create a new user model entry and render
	 * 
	 * @return Result
	 */
	public Result createUser() {
		User user = renderUser(request().body().asJson().toString());
		user.save();
		return ok(renderUser(user));
	}

	/**
	 * Delete user model entry by id.
	 * 
	 * @param id
	 * @return Result
	 */
	public Result deleteUser(Long id) {
		Result result = notFound();
		User user = User.findById(id);
		if (user != null) {
			user.delete();
			result = ok();
		}
		return result;
	}

	/**
	 * Delete all user model entries.
	 * 
	 * @param id
	 * @return Result
	 */
	public Result deleteAllUsers() {
		User.deleteAll();
		return ok();
	}

	/**
	 * Delete user entry fields by id and render user.
	 * 
	 * @param id
	 * @return Result
	 */
	public Result updateUser(Long id) {
		Result result = notFound();
		User user = User.findById(id);
		if (user != null) {
			User updatedUser = renderUser(request().body().asJson().toString());
			user.update(updatedUser);
			user.save();
			result = ok(renderUser(user));
		}
		return result;
	}

	/**
	 * Show all activities for a user and render activity.
	 * 
	 * @param userId
	 * @return Result
	 */
	public Result activities(Long userId) {
		User user = User.findById(userId);
		return ok(renderActivity(user.activities));
	}

	/**
	 * Create activity entry for a user and render activity.
	 * 
	 * @param userId
	 * @return Result
	 */
	public Result createActivity(Long userId) {
		User user = User.findById(userId);
		Activity activity = renderActivity(request().body().asJson().toString());

		user.activities.add(activity);
		user.save();

		return ok(renderActivity(activity));
	}

	/**
	 * Find an activity entry for a user.
	 * 
	 * @param userId,
	 *            activityId
	 * @return Result
	 */
	public Result activity(Long userId, Long activityId) {
		User user = User.findById(userId);
		Activity activity = Activity.findById(activityId);

		if (activity == null) {
			return notFound();
		} else {
			return user.activities.contains(activity) ? ok(renderActivity(activity)) : badRequest();
		}
	}

	/**
	 * Delete an activity entry for a user.
	 * 
	 * @param userId,
	 *            activityId
	 * @return Result
	 */
	public Result deleteActivity(Long userId, Long activityId) {
		User user = User.findById(userId);
		Activity activity = Activity.findById(activityId);

		if (activity == null) {
			return notFound();
		} else {
			if (user.activities.contains(activity)) {
				user.activities.remove(activity);
				activity.delete();
				user.save();
				return ok();
			} else {
				return badRequest();
			}

		}
	}

	/**
	 * Update an activity entry for a user.
	 * 
	 * @param userId,
	 *            activityId
	 * @return Result
	 */
	public Result updateActivity(Long userId, Long activityId) {
		User user = User.findById(userId);
		Activity activity = Activity.findById(activityId);

		if (activity == null) {
			return notFound();
		} else {
			if (user.activities.contains(activity)) {
				Activity updatedActivity = renderActivity(request().body().asJson().toString());
				activity.distance = updatedActivity.distance;
				activity.location = updatedActivity.location;
				activity.activityType = updatedActivity.activityType;

				activity.save();
				return ok(renderActivity(updatedActivity));
			} else {
				return badRequest();
			}
		}
	}

	/**
	 * Show all locations for an activity and render location.
	 * 
	 * @param activityId
	 * @return Result
	 */
	public Result locations(Long activityId) {
		Activity activity = Activity.findById(activityId);
		return ok(renderLocation(activity.route));
	}

	/**
	 * Create location entry for activity and render location.
	 * 
	 * @param activityId,
	 *            locationId
	 * @return Result
	 */
	public Result createLocation(Long actvityId) {
		Activity activity = Activity.findById(actvityId);
		Location location = renderLocation(request().body().asJson().toString());

		activity.route.add(location);
		activity.save();

		return ok(renderLocation(location));
	}

	/**
	 * Find location entry for activity and render location.
	 * 
	 * @param activityId,
	 *            locationId
	 * @return Result
	 */
	public Result location(Long activityId, Long locationId) {
		Activity activity = Activity.findById(activityId);
		Location location = Location.findById(locationId);

		if (location == null) {
			return notFound();
		} else {
			return activity.route.contains(location) ? ok(renderLocation(location)) : badRequest();
		}
	}

	/**
	 * Delete location entry for activity.
	 * 
	 * @param activityId,
	 *            locationId
	 * @return Result
	 */
	public Result deleteLocation(Long activityId, Long locationId) {
		Activity activity = Activity.findById(activityId);
		Location location = Location.findById(locationId);

		if (location == null) {
			return notFound();
		} else {
			if (activity.route.contains(location)) {
				activity.route.remove(location);
				location.delete();
				activity.save();
				return ok();
			} else {
				return badRequest();
			}

		}
	}

	/**
	 * Update location entry for activity and render location.
	 * 
	 * @param activityId,
	 *            locationId
	 * @return Result
	 */
	public Result updateLocation(Long activityId, Long locationId) {
		Activity activity = Activity.findById(activityId);
		Location location = Location.findById(locationId);

		if (location == null) {
			return notFound();
		} else {
			if (activity.route.contains(location)) {
				Location updatedLocation = renderLocation(request().body().asJson().toString());
				location.latitude = updatedLocation.latitude;
				location.longitude = updatedLocation.longitude;
				location.save();
				return ok(renderLocation(updatedLocation));
			} else {
				return badRequest();
			}
		}
	}
}
