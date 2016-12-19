package services;

import java.util.ArrayList;
import java.util.List;

import models.Friends;
import models.User;

/**
 * Service for managing friend related actions for showing, following, accepting
 * and unfollowing Friends.
 * 
 * @author annadowling
 */
public class FriendUtils {

	/**
	 * Create a friend entry with boolean added set as false with userId and
	 * friends userId(friendId) as parameters.
	 * 
	 * @param userId,
	 *            friendId
	 * @return void
	 */
	public static void followFriend(Long userId, Long friendId) {
		Friends following = Friends.findById(userId, friendId);
		Friends f = null;
		if (following == null) {
			f = new Friends(userId, friendId);
			f.save();
		}
	}

	/**
	 * Retrieve list of Friends follow requests for a user where boolean added =
	 * false.
	 * 
	 * @param userId
	 * @return List<Friends>
	 */
	public static List<Friends> getAllPendingFollowRequests(Long userId) {
		Boolean added = false;
		List<Friends> pendingFollowRequests = Friends.findAllPendingFollowRequests(userId, added);
		return pendingFollowRequests;
	}

	/**
	 * Once the user has accepted the follow request update the Friends entry to
	 * set added = true.
	 * 
	 * @param userId,
	 *            friendId
	 * @return void
	 */
	public static void acceptFollowRequest(Long userId, Long friendId) {
		Friends followRequest = Friends.findById(friendId, userId);
		if (followRequest != null) {
			followRequest.added = true;
			followRequest.save();
		}
	}

	/**
	 * Delete the Friends entry for a currentUser and their friend.
	 * 
	 * @param userId,
	 *            friendId
	 * @return void
	 */
	public static void unfollowFriend(Long userId, Long friendId) {
		Friends thisFriendship = Friends.findById(userId, friendId);
		if (thisFriendship != null && thisFriendship.added.equals(true)) {
			thisFriendship.delete();
		}
	}

	/**
	 * Retrieve list of Friends as their associated User entries for the logged
	 * in user.
	 * 
	 * @param userId
	 * @return List<User>
	 */
	public static List<User> showfriends(Long userId) {
		List<Friends> allFriends = Friends.findAllFriends(userId);
		List<User> friendsOfCurrentUser = new ArrayList<User>();
		for (Friends friend : allFriends) {
			if (friend.added == true) {
				User user = User.findById(friend.friendId);
				friendsOfCurrentUser.add(user);
			}
		}
		return friendsOfCurrentUser;
	}

	/**
	 * Retrieve a User by their corresponding friendId
	 * 
	 * @param friendId
	 * @return User
	 */
	public static User findUserByfriendId(Long friendId) {
		User user = null;
		if (friendId != null) {
			user = User.findById(friendId);
		}
		return user;
	}
}
