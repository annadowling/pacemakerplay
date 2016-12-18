package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.Activity;

public class ActivityFilterService {
	
	public static List<Activity> filterActivityByDate(List<Activity> unfilteredActivities){
		List<Activity> filteredActivities = new ArrayList<Activity>();
		Collections.sort(unfilteredActivities, (a1, a2) -> a1.date.compareTo(a2.date));
		filteredActivities.addAll(unfilteredActivities);
		return filteredActivities;
	}
	
	public static List<Activity> filterActivityByDuration(List<Activity> unfilteredActivities){
		List<Activity> filteredActivities = new ArrayList<Activity>();
		Collections.sort(unfilteredActivities, new Comparator<Activity>() {
		    @Override
		    public int compare(Activity a1, Activity a2) {
		        return Double.compare(a1.duration, a2.duration);
		    }
		});
		filteredActivities.addAll(unfilteredActivities);
		return filteredActivities;
	}
	
	public static List<Activity> filterActivityByDistance(List<Activity> unfilteredActivities){
		List<Activity> filteredActivities = new ArrayList<Activity>();
		Collections.sort(unfilteredActivities, new Comparator<Activity>() {
		    @Override
		    public int compare(Activity a1, Activity a2) {
		        return Double.compare(a1.distance, a2.distance);
		    }
		});
		filteredActivities.addAll(unfilteredActivities);
		return filteredActivities;
	}
	
	public static List<Activity> filterActivityByLocation(List<Activity> unfilteredActivities){
		List<Activity> filteredActivities = new ArrayList<Activity>();
		Collections.sort(unfilteredActivities, (a1, a2) -> a1.location.toLowerCase().compareTo(a2.location.toLowerCase()));
		filteredActivities.addAll(unfilteredActivities);
		return filteredActivities;
	}
	
	public static List<Activity> filterActivityByType(List<Activity> unfilteredActivities){
		List<Activity> filteredActivities = new ArrayList<Activity>();
		Collections.sort(unfilteredActivities, (a1, a2) -> a1.activityType.toLowerCase().compareTo(a2.activityType.toLowerCase()));
		filteredActivities.addAll(unfilteredActivities);
		return filteredActivities;
	}

}
