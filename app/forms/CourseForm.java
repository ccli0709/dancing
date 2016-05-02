package forms;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.Maps;

public class CourseForm {

	public CourseForm() {
	}

	public CourseForm(models.Course course) {

		if (course == null)
			return;

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		this.id = String.format("%03d", course.getId());

		this.danceDivision = course.getDanceDivision();
		this.choreography = course.getChoreography();
		this.dayOfWeek = course.getDayOfWeek();
		this.period = course.getPeriod();
		this.beginTime = course.getBeginTime();
		this.endTime = course.getEndTime();

		this.beginDate = fmt.print(course.getBeginDate());
		this.endDate = fmt.print(course.getEndDate());

		this.level = course.getLevel();
		this.quantity = course.getQuantity() == null ? "0" : course.getQuantity().toString();
		this.location = course.getLocation();
	}

	public String id;
	public String danceDivision;
	public String choreography;
	public String dayOfWeek;
	public String period;
	public String beginTime;
	public String endTime;
	public String beginDate;
	public String endDate;
	public String level;
	public String quantity;
	public String location;

	public static Map<String, String> getHeaders() {
		Map<String, String> headers = Maps.newLinkedHashMap();
		headers.put("代碼", "id");
		headers.put("舞科", "dance_division");
		headers.put("舞碼", "choreography");
		headers.put("星期", "day_of_week");
		headers.put("時段", "period");
		headers.put("起始時間", "");
		headers.put("結束時間", "");
		headers.put("起始日期", "begin_date");
		headers.put("結束日期", "end_date");
		headers.put("課堂", "");
		headers.put("地點", "location");
		headers.put("級別", "level");
		return headers;
	}
}
