package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;

@Entity
public class WeekClass extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	// 舞科
	protected String danceDivision;
	// 舞碼
	protected String choreography;

	protected String dayOfWeek;
	// 時段
	protected String period;

	protected String beginTime;

	protected String endTime;

	@Column(columnDefinition = "datetime")
	protected DateTime beginDate = new DateTime();

	@Column(columnDefinition = "datetime")
	protected DateTime endDate = new DateTime();

	protected String level;

	// 課堂數量
	protected Long quantity;

	// 上課地點
	protected String location;

	public static Finder<Long, WeekClass> find = new Finder<Long, WeekClass>(WeekClass.class);

	public static PagedList<WeekClass> getPagedList(forms.QueryParams queryParams) {
		ExpressionList<WeekClass> where = find.where();

		// if (queryParam.getQueryString().length() > 0) {
		// where.disjunction().add(Expr.contains("facebook_name",
		// queryParam.getQueryString()))
		// .add(Expr.contains("address", queryParam.getQueryString()))
		// .add(Expr.contains("chinese_name", queryParam.getQueryString()));
		// }
		//
		// // 搜尋結果排序
		where.orderBy(String.format("%s %s", queryParams.getSortField(), queryParams.getSortDirection()));
		return where.findPagedList(queryParams.getPageIndex() - 1, queryParams.getPageSize());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDanceDivision() {
		return danceDivision;
	}

	public void setDanceDivision(String danceDivision) {
		this.danceDivision = danceDivision;
	}

	public String getChoreography() {
		return choreography;
	}

	public void setChoreography(String choreography) {
		this.choreography = choreography;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public DateTime getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(DateTime beginDate) {
		this.beginDate = beginDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}