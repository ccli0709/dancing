package forms;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.Maps;

public class WeekClassForm {

	public WeekClassForm() {
	}

	public WeekClassForm(models.WeekClass weekClass) {

		if (weekClass == null)
			return;

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		this.id = String.format("%03d", weekClass.getId());

		this.danceDivision = weekClass.getDanceDivision();
		this.choreography = weekClass.getChoreography();
		this.dayOfWeek = weekClass.getDayOfWeek();
		this.period = weekClass.getPeriod();
		this.beginTime = weekClass.getBeginTime();
		this.endTime = weekClass.getEndTime();

		this.beginDate = fmt.print(weekClass.getBeginDate());
		this.endDate = fmt.print(weekClass.getEndDate());

		this.level = weekClass.getLevel();
		this.quantity = weekClass.getQuantity() == null ? "0" : weekClass.getQuantity().toString();
		this.location = weekClass.getLocation();
	}

	public String id;
	public String danceDivision;
	public String choreography;
	public String dayOfWeek;
	public String period;
	public String beginTime;
	public String endTime;
	protected String beginDate;
	protected String endDate;
	public String level;
	protected String quantity;
	public String location;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd");
		String value = DateTime.now().toString(f);

		try {
			DateTime d = f.parseDateTime(beginDate);
			value = d.toString(f);
		} catch (Exception e) {
		}
		this.beginDate = value;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd");
		String value = DateTime.now().toString(f);

		try {
			DateTime d = f.parseDateTime(endDate);
			value = d.toString(f);
		} catch (Exception e) {
		}
		this.endDate = value;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {

		Long value = 0L;
		try {
			value = Long.parseLong(quantity);
		} catch (Exception e) {
		}
		value = Math.max(value, 0L);

		this.quantity = value.toString();
	}

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
