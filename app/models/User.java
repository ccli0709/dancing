package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.codec.digest.DigestUtils;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;

import play.data.validation.Constraints;

@Entity
public class User extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Constraints.Required
	protected String email;
	@Constraints.Required
	protected String name;
	@Constraints.Required
	protected String password;

	public static Finder<Long, User> find = new Finder<Long, User>(User.class);

	public static boolean authenticate(String email, String password) {
		ExpressionList<User> where = find.where();
		where.eq("email", email).eq("password", DigestUtils.sha1Hex(password));
		List<User> users = where.findList();
		return users.size() > 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}