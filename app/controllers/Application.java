package controllers;

import java.util.List;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import forms.AnniversaryForm;
import models.Anniversary;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	List<Form<AnniversaryForm>> formRows;
	Form<AnniversaryForm> formData;
	Map<String, String> systemData;

	public void beforeAction() {
		systemData = Maps.newHashMap();
		systemData.put("title", "我的紀念日");
		systemData.put("connected", session("connected"));
	}

	public Result afterAction() {
		return ok(views.html.index.render(systemData, formRows, formData));
	}

	public Result login() {

		session("connected", "ccli0709@gmail.com");

		beforeAction();

		loadPage(true);

		return afterAction();
	}

	public Result logout() {

		session().remove("connected");

		beforeAction();

		loadPage(true);

		return afterAction();
	}

	public Result index() {

		beforeAction();

		loadPage(true);

		return afterAction();
	}

	public Result post() {

		beforeAction();

		DynamicForm postData = Form.form().bindFromRequest();
		String action = postData.get("_action");

		if ("create".equals(action))
			create();
		else if ("delete".equals(action))
			delete();

		return afterAction();
	}

	public void create() {

		formData = Form.form(AnniversaryForm.class).bindFromRequest();

		if (formData.hasErrors()) {
			loadPage(false);
		} else {
			AnniversaryForm anniversaryForm = formData.get();

			models.Anniversary anniversary = new Anniversary();
			anniversary.setName(anniversaryForm.getName());

			DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd");
			anniversary.setDate(f.parseDateTime(anniversaryForm.getDate()));

			anniversary.save();
			loadPage(true);
		}

	}

	public void delete() {

		DynamicForm postData = Form.form().bindFromRequest();
		Long id;
		try {
			id = Long.parseLong(postData.get("id"));
		} catch (Exception e) {
			id = -1L;
		}
		models.Anniversary.deleteById(id);

		loadPage(true);
	}

	/**
	 * 
	 * @param reload
	 *            決定是否重新由資料庫讀取，反之則是保持表單輸入內容。
	 */
	private void loadPage(boolean reload) {
		List<Anniversary> anniversaries = Anniversary.findAll();
		formRows = Lists.newArrayList();

		for (Anniversary anniversary : anniversaries) {
			Form<AnniversaryForm> form = Form.form(AnniversaryForm.class).fill(new AnniversaryForm(anniversary));
			formRows.add(form);
		}

		if (reload) {
			formData = Form.form(AnniversaryForm.class).fill(new AnniversaryForm());
		}

	}

}
