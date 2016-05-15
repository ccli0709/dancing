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

		// 課程資料

		this.danceDivision = utils.CourseUtils.getDanceDivision(courseDate.getCourse().getDanceDivision());
		this.choreography = utils.CourseUtils.getChoreographie(courseDate.getCourse().getChoreography());
		this.level = utils.CourseUtils.getLevel(courseDate.getCourse().getLevel());
		this.period = utils.CourseUtils.getPeriod(courseDate.getCourse().getLocation());
		this.location = utils.CourseUtils.getLocation(courseDate.getCourse().getLocation());
	}

	public String id;
	public String serial;
	public String courseDate;
	public String remark;

	// 課程資料
	public String danceDivision;
	public String choreography;
	public String level;
	public String period;
	public String location;
	

}
