package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;

import com.avaje.ebean.Model;


@SuppressWarnings("serial")
@Entity
@Table(name="friends")
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
	
	
	public Friends(Long userId, Long friendId) {
		this.userId = userId;
		this.friendId = friendId;
	}
	
	
	public static List<Friends> findAll() {
		return find.all();
	}
	
	
	public static List<Friends> findPendingFriendRequestsToUser(Long userId) {
		return find.where().eq("friendId", userId).eq("added", false).findList();
	}
	
	
	public static List<Friends> findPendingFriendRequestsFromUser(Long userId) {
		return find.where().eq("userId", userId).eq("added", false).findList();
	}
	
	
	public static List<Friends> findAllAcceptedFriendReuqests(Long userId) {
		return find.where().eq("userId", userId).eq("added", true).findList();
	}
	
	public static List<Friends> findAllFriends(Long userId) {
		return find.where().eq("userId", userId).findList();
	}

	
	
	public static Friends findById(Long userId, Long friendId) {
		return find.where().eq("friendId", friendId).eq("userId", userId).findUnique();
	}
	
	
	
	public static Model.Finder<String, Friends> find = new Model.Finder<String, Friends>(String.class, Friends.class);
}
