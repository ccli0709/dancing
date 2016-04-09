package forms;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import models.Anniversary;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

public class AnniversaryForm {

	protected Long id;

	@Constraints.Required
	protected String name;

	@Constraints.Required
	protected String date;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	protected String passedDays;

	public String getPassedDays() {
		return passedDays;
	}

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		try {
			DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd");
			f.parseDateTime(this.date);
		} catch (Exception e) {
			errors.add(new ValidationError("date", "輸入日期並非合法的日期，正確應為「2016-01-01」。"));
		}

		return errors.isEmpty() ? null : errors;
	}

	public AnniversaryForm() {
	}

	public AnniversaryForm(Anniversary anniversary) {
		this.id = anniversary.getId();
		this.name = anniversary.getName();

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		this.date = fmt.print(anniversary.getDate());

		Period period = new Period(anniversary.getDate(), new DateTime());
		this.passedDays = String.format("經過  %s 年 %s 月  %s 日，共計 %s 天。", period.getYears(), period.getMonths(),
				period.getDays(), period.toDurationFrom(null).getStandardDays());
	}

}
