package controllers;

import java.util.List;
import java.util.Map;

import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.SettingUtils;
import utils.SecurityUtils;

public class CourseWeek extends Controller {

	// 用於全局的參數
	forms.PageParams params = new forms.PageParams();
	forms.QueryParams queryParams;

	List<Form<forms.CourseDateForm>> courseDateForms;

	public void beforeAction() {

		// 畫面
		params.putString("title", "每周上課清單");
		params.putMap("periods", utils.CourseUtils.getPeriods());

		// 登入
		SecurityUtils.SetLoginParams(params);

	}

	public Result afterAction() {
		return ok(views.html.course.week.render(params, courseDateForms));
	}

	public Result index() {

		beforeAction();

		Map<String, String> weekDays = Maps.newLinkedHashMap();
		weekDays.put("週日", "2016-05-15");
		weekDays.put("週一", "2016-05-16");
		weekDays.put("週二", "2016-05-17");
		weekDays.put("週三", "2016-05-18");
		weekDays.put("週四", "2016-05-19");
		weekDays.put("週五", "2016-05-20");
		weekDays.put("週六", "2016-05-21");
		params.putMap("weekDays", weekDays);

		List<models.CourseDate> courseDates = models.CourseDate.getOneWeekDates();
		courseDateForms = Lists.newArrayList();
		for (models.CourseDate item : courseDates) {
			Form<forms.CourseDateForm> form = Form.form(forms.CourseDateForm.class)
					.fill(new forms.CourseDateForm(item));
			courseDateForms.add(form);
		}

		return afterAction();
	}

}
