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
		models.Course course = courseDate.getCourse();
		this.courseId = String.format("%03d", course.getId());
		this.danceDivision = utils.CourseUtils.getDanceDivision(course.getDanceDivision());
		this.choreography = utils.CourseUtils.getChoreographie(course.getChoreography());
		this.level = utils.CourseUtils.getLevel(course.getLevel());
		this.period = utils.CourseUtils.getPeriod(course.getPeriod());
		this.location = utils.CourseUtils.getLocation(course.getLocation());
	}

	public String id;
	public String serial;
	public String courseDate;
	public String remark;

	// 課程資料
	public String courseId;
	public String danceDivision;
	public String choreography;
	public String level;
	public String period;
	public String location;

}
