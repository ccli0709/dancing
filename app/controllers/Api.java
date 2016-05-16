package controllers;

import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.Lists;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Api extends Controller {

	public Result CoursesV1() {

		List<CoursesV1Format> data = Lists.newArrayList();
		for (models.Course c : models.Course.find.all()) {
			data.add(new CoursesV1Format(c));
		}
		return ok(Json.toJson(data));
	}

	class CoursesV1Format {
		CoursesV1Format(models.Course c) {
			DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

			this.danceDivision = utils.CourseUtils.getDanceDivision(c.getDanceDivision());
			this.choreography = utils.CourseUtils.getChoreographie(c.getChoreography());
			this.beginDate = fmt.print(c.getBeginDate());
			this.endDate = fmt.print(c.getEndDate());
			this.dayOfWeek = utils.CourseUtils.getTheDayOfWeek(c.getDayOfWeek());
			this.period = utils.CourseUtils.getPeriod(c.getPeriod());
			this.level = utils.CourseUtils.getLevel(c.getLevel());
			this.quantity = utils.CourseUtils.getQuantity(c.getQuantity().toString());
			this.location = utils.CourseUtils.getLocation(c.getLocation());
		}

		public String danceDivision;
		public String choreography;
		public String beginDate;
		public String endDate;
		public String dayOfWeek;
		public String period;
		public String level;
		public String quantity;
		public String location;

	}
}
