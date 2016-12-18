package services;

import java.util.ArrayList;
import java.util.List;

import models.Friends;
import models.User;

public class FriendUtils {


	public static void followFriend(Long userId, Long friendId) {
		Friends following = Friends.findById(userId, friendId);
		Friends f = null;
		if (following == null) {
			f = new Friends(userId, friendId);
			f.save();
		}
	}
	
	public static List<Friends> getAllPendingFollowRequests(Long userId){
		Boolean added = false;
		List<Friends> pendingFollowRequests = Friends.findAllPendingFollowRequests(userId, added);
		return pendingFollowRequests;	
	}
	
	public static void acceptFollowRequest(Long userId, Long friendId){
		Friends followRequest = Friends.findById(friendId, userId);
		if (followRequest != null) {
		followRequest.added = true;
		followRequest.save();
		}
	}
	

	
	public static void unfollowFriend(Long userId, Long friendId) {
		Friends thisFriendship = Friends.findById(userId, friendId);
		if (thisFriendship != null && thisFriendship.added.equals(true)) {
			thisFriendship.delete();
		}
	}
	
	public static List<User> showfriends(Long userId){
		List<Friends> allFriends = Friends.findAllFriends(userId);
		List<User> friendsOfCurrentUser = new ArrayList<User>();
		for(Friends friend: allFriends){
			if(friend.added == true){
			User user = User.findById(friend.friendId);
			friendsOfCurrentUser.add(user);
			}
		}
		return friendsOfCurrentUser;	
	}
	
	public static User findUserByfriendId(Long friendId){
		User user = null;
		if(friendId != null){
			user = User.findById(friendId);
		}
		return user;
	}
}
