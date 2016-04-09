package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;

@Entity
public class Anniversary extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	protected String name;

	@ManyToOne
	public User user;

	@Column(columnDefinition = "datetime")
	protected DateTime date = new DateTime();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public static Finder<Long, Anniversary> find = new Finder<Long, Anniversary>(Anniversary.class);

	public static void deleteById(Long id) {
		models.Anniversary anniversary = models.Anniversary.find.byId(id);
		anniversary.delete();
	}

	public static List<Anniversary> findAll() {
		ExpressionList<Anniversary> where = find.where();
		where.order("date");
		return where.findList();
	}

}