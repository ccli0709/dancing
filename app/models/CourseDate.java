package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;

import play.Logger;

@Entity
public class CourseDate extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	// 課程基本資料
	@ManyToOne
	protected Course course;

	// 堂數
	protected int serial;

	// 上課日期
	@Column(columnDefinition = "datetime")
	protected DateTime courseDate = new DateTime();

	// 備註-停課/延期/加課
	protected String remark;

	public static Finder<Long, CourseDate> find = new Finder<Long, CourseDate>(CourseDate.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public DateTime getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(DateTime courseDate) {
		this.courseDate = courseDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static List<CourseDate> getOneWeekDates() {
		ExpressionList<CourseDate> where = find.where();
		// 取得當週的星期日
		DateTime sunday = DateTime.now().minusDays(DateTime.now().dayOfWeek().get());
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		Logger.debug("fmt.print(sunday):{}", fmt.print(sunday));
		where.add(Expr.ge("courseDate", fmt.print(sunday)));
		return where.findList();
	}

}