package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;

import com.avaje.ebean.Model;

/**
 * Class for Friends Model and finders.
 * 
 * @author annadowling
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "friends")
@EntityConcurrencyMode(ConcurrencyMode.NONE)
public class Friends extends Model {
	@Id
	@GeneratedValue
	public Long id;
	public Long userId;
	public Long friendId;
	public Boolean added = false;

	/**
	 * default constructor
	 */
	public Friends() {

	}

	/**
	 * Overloaded constructor for Friends
	 * 
	 * @param userId,
	 *            friendId
	 * @return Friends
	 */
	public Friends(Long userId, Long friendId) {
		this.userId = userId;
		this.friendId = friendId;
	}

	/**
	 * Find all Friends entries.
	 * 
	 * @return List<Friends>
	 */
	public static List<Friends> findAll() {
		return find.all();
	}

	/**
	 * Find all Friends entries by userId.
	 * 
	 * @param userId
	 * @return List<Friends>
	 */
	public static List<Friends> findAllFriends(Long userId) {
		return find.where().eq("userId", userId).findList();
	}

	/**
	 * Find all Friends entries by friendId and boolean added(false).
	 * 
	 * @param friendId,
	 *            boolean
	 * @return List<Friends>
	 */
	public static List<Friends> findAllPendingFollowRequests(Long friendId, Boolean added) {
		return find.where().eq("friendId", friendId).eq("added", added).findList();
	}

	/**
	 * Find Friends entry by userId and friendId.
	 * 
	 * @param userId,
	 *            friendId
	 * @return Friends
	 */
	public static Friends findById(Long userId, Long friendId) {
		return find.where().eq("friendId", friendId).eq("userId", userId).findUnique();
	}

	/**
	 * Finder for Friends Model.
	 * 
	 * @return Model.Finder
	 */
	public static Model.Finder<String, Friends> find = new Model.Finder<String, Friends>(String.class, Friends.class);
}
