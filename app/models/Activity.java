package models;

import static com.google.common.base.Objects.toStringHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Objects;
import javax.persistence.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import com.avaje.ebean.annotation.ConcurrencyMode;

/**
 * Class for Activity Model and finders.
 * 
 * @author annadowling
 */
@SuppressWarnings("serial")
@Entity
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Table(name = "activity")
public class Activity extends Model {
	@Id
	@GeneratedValue
	public Long id;
	public String location;
	public double distance;
	public Date date;
	public double duration;
	public String activityType;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Location> route = new ArrayList<Location>();

	/**
	 * default constructor
	 */
	public Activity() {
	}

	/**
	 * Overloaded constructor for Activity
	 * 
	 * @param String,
	 *            String, double, Date, double
	 * @return Activity
	 */
	public Activity(String activityType, String location, double distance, Date date, double duration) {
		this.activityType = activityType;
		this.location = location;
		this.distance = distance;
		this.date = date;
		this.duration = duration;
	}

	/**
	 * toString method for activity and fields.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return toStringHelper(this).addValue(id).addValue(location).addValue(distance).addValue(date).addValue(duration)
				.addValue(activityType).toString();
	}

	/**
	 * equals method for activity and fields.
	 * 
	 * @param Object
	 * @return boolean
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Activity) {
			final Activity other = (Activity) obj;
			return Objects.equal(activityType, other.activityType) && Objects.equal(location, other.location)
					&& Objects.equal(distance, other.distance) && Objects.equal(date, other.date)
					&& Objects.equal(duration, other.duration);
		} else {
			return false;
		}
	}

	/**
	 * hashcode method for activity and fields.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.activityType, this.location, this.distance, this.date, this.duration);
	}

	/**
	 * Find all activity entries.
	 * 
	 * @return List<Activity>
	 */
	public static List<Activity> findAll() {
		return find.all();
	}

	/**
	 * Find an activity entry by id.
	 * 
	 * @param id
	 * @return Activity
	 */
	public static Activity findById(Long id) {
		return find.where().eq("id", id).findUnique();
	}

	/**
	 * Finder for activity Model.
	 * 
	 * @return Model.Finder
	 */
	public static Model.Finder<String, Activity> find = new Model.Finder<String, Activity>(String.class,Activity.class);
}
