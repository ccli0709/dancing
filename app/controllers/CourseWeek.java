package controllers;

import java.util.List;

import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.SettingUtils;
import utils.SecurityUtils;

public class CourseWeek extends Controller {

	// 用於全局的參數
	forms.PageParams params = new forms.PageParams();
	forms.QueryParams queryParams;

	public void beforeAction() {

		// 畫面
		params.putString("title", "每周上課清單");
		// 登入
		SecurityUtils.SetLoginParams(params);

	}

	public Result afterAction() {
		return ok(views.html.course.week.render(params));
	}

	public Result index() {

		beforeAction();

		return afterAction();
	}

}
