package controllers;

import org.joda.time.DateTime;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	// 每周上課清單：http://richardshih.myasustor.com/danceClass/weekClass.php
	// 拉丁舞課程清單：http://richardshih.myasustor.com/danceClass/latinClass.php
	// 摩登舞上課清單：http://richardshih.myasustor.com/danceClass/modernClass.php
	// 每堂上課期間：http://richardshih.myasustor.com/danceClass/classDate.php?classNumber=21
	// classNumber：周日11、週一21、22、31、41、42、43、61、62、71
	// 提醒訊息：http://richardshih.myasustor.com/danceClass/message.php
	// 教師經歷：http://richardshih.myasustor.com/danceClass/duration.php

	// 用於全局的參數
	forms.PageParams params = new forms.PageParams();
	forms.QueryParams queryParams;

	// List<Form<AnniversaryForm>> formRows;
	// Form<AnniversaryForm> formData;

	// 輸入表單
	Form<forms.LoginForm> form;

	public void beforeAction() {
		params.putString("title", "登入畫面");
		params.putString("loginUid", session("loginUid"));
		params.putString("loginEmail", session("loginEmail"));
		params.putString("loginName", session("loginName"));
	}

	public Result afterAction() {
		return ok(views.html.index.render(params));
	}

	// 登入畫面
	public Result login() {

		beforeAction();
		form = Form.form(forms.LoginForm.class).fill(new forms.LoginForm());
		return ok(views.html.login.render(params, form));
	}

	// 登入驗證
	public Result validate() {
		beforeAction();
		form = Form.form(forms.LoginForm.class).bindFromRequest();
		if (form.hasErrors()) {
			flash("error", String.format("登入失敗。"));
			return ok(views.html.login.render(params, form));
		} else {
			session("loginUid", "1");
			session("loginEmail", "ccli0709@gmail.com");
			session("loginName", "李政忠");
			flash("success", String.format("登入成功。"));
			return redirect(routes.Application.index());
		}

	}

	// 登出後導向首頁
	public Result logout() {
		session().clear();
		return redirect(routes.Application.index());
	}

	public Result index() {

		beforeAction();

		loadPage(true);

		return afterAction();
	}

	/**
	 * 
	 * @param reload
	 *            決定是否重新由資料庫讀取，反之則是保持表單輸入內容。
	 */
	private void loadPage(boolean reload) {

	}

}
