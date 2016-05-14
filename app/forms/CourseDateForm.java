package forms;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CourseDateForm {

	public CourseDateForm() {
	}

	public CourseDateForm(models.CourseDate courseDate) {

		if (courseDate == null)
			return;

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		this.id = String.format("%03d", courseDate.getId());
		this.serial = String.format("%03d", courseDate.getSerial());
		this.courseDate = fmt.print(courseDate.getCourseDate());
		this.remark = courseDate.getRemark();
	}

	public String id;
	public String serial;
	public String courseDate;
	public String remark;

}
