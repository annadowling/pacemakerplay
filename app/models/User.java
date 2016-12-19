package models;

import java.util.ArrayList;
import java.util.List;
import static com.google.common.base.MoreObjects.toStringHelper;
import com.google.common.base.Objects;

import javax.persistence.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import com.avaje.ebean.annotation.ConcurrencyMode;

/**
 * Class for User Model and finders.
 * 
 * @author annadowling
 */
@Entity
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Table(name = "my_user")
public class User extends Model {
	@Id
	@GeneratedValue
	public Long id;
	public String firstname;
	public String lastname;
	public String email;
	public String password;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Activity> activities = new ArrayList<Activity>();

	public static Find<String, User> find = new Find<String, User>() {
	};

	/**
	 * default constructor
	 */
	public User() {
	}

	/**
	 * Overloaded constructor for User
	 * 
	 * @param String,
	 *            String, email, password
	 * @return User
	 */
	public User(String firstname, String lastname, String email, String password) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
	}

	/**
	 * Update user entry fields
	 * 
	 * @param User
	 * @return void
	 */
	public void update(User user) {
		this.firstname = user.firstname;
		this.lastname = user.lastname;
		this.email = user.email;
		this.password = user.password;
	}

	/**
	 * toString method for User and fields.
	 * 
	 * @return String
	 */
	public String toString() {
		return toStringHelper(this).add("Id", id).add("Firstname", firstname).add("Lastname", lastname)
				.add("Email", email).add("Password", password).toString();
	}

	/**
	 * equals method for User and fields.
	 * 
	 * @param Object
	 * @return boolean
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof User) {
			final User other = (User) obj;
			return Objects.equal(firstname, other.firstname) && Objects.equal(lastname, other.lastname)
					&& Objects.equal(email, other.email);
		} else {
			return false;
		}
	}

	/**
	 * Find a user by an email entry
	 * 
	 * @param email
	 * @return User
	 */
	public static User findByEmail(String email) {
		return User.find.where().eq("email", email).findUnique();
	}

	/**
	 * Find a user by an id.
	 * 
	 * @param id
	 * @return User
	 */
	public static User findById(Long id) {
		return find.where().eq("id", id).findUnique();
	}

	/**
	 * Find all User entries.
	 * 
	 * @return List<User>
	 */
	public static List<User> findAll() {
		return find.all();
	}

	/**
	 * Delete all User entries.
	 * 
	 * @return void
	 */
	public static void deleteAll() {
		for (User user : User.findAll()) {
			user.delete();
		}
	}
}
