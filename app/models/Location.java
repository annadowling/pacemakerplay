package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import com.avaje.ebean.annotation.ConcurrencyMode;

/**
 * Class for Location Model and finders.
 * 
 * @author annadowling
 */
@Entity
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Table(name = "location")
public class Location extends Model {
	@Id
	@GeneratedValue
	public Long id;
	@Required
	public float latitude;
	@Required
	public float longitude;

	/**
	 * default constructor
	 */
	public Location() {
	}

	/**
	 * Overloaded constructor for Location
	 * 
	 * @param latitude,
	 *            longitude
	 * @return Location
	 */
	public Location(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * toString method for location and fields.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return "[" + this.latitude + ", " + this.longitude + "]";
	}

	/**
	 * equals method for location and fields.
	 * 
	 * @param Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Location location = (Location) o;

		if (Float.compare(location.latitude, latitude) != 0)
			return false;
		return Float.compare(location.longitude, longitude) == 0;

	}

	/**
	 * hashcode method for location and fields.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		int result = (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
		result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
		return result;
	}

	/**
	 * Find a location entry by id.
	 * 
	 * @param id
	 * @return Location
	 */
	public static Location findById(Long id) {
		return find.where().eq("id", id).findUnique();
	}

	/**
	 * Finder for Location Model.
	 * 
	 * @return Model.Finder
	 */
	public static Model.Finder<String, Location> find = new Model.Finder<String, Location>(String.class,
			Location.class);
}
