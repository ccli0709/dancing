package controllers;

import java.util.List;

import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;

import forms.SettingForm;
import forms.SettingForms;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.SettingUtils;
import utils.SecurityUtils;

public class SettingList extends Controller {

	// 用於全局的參數
	forms.PageParams params = new forms.PageParams();
	forms.QueryParams queryParams;

	// Master(List)
	PagedList<models.Setting> page;
	List<Form<forms.SettingForm>> rows;

	public void beforeAction() {

		// 畫面
		params.putString("title", "系統參數設定");
		// 登入
		SecurityUtils.SetLoginParams(params);
		// 分頁
		queryParams = new forms.QueryParams(request());
		params.putString("pageIndex", String.valueOf(queryParams.getPageIndex()));
		params.putString("sortField", queryParams.getSortField());
		params.putString("sortDirection", queryParams.getSortDirection());
		params.putString("queryCondition", queryParams.getQueryCondition());

		// 自有
		params.putMap("types", utils.SettingUtils.getTypes());

		// 取得本次查詢用的參數
		List<String> allowedFields = Lists.newArrayList("type");
		for (String query : queryParams.getQueryCondition().split(",")) {

			String[] tokens = query.split(":");
			if (tokens.length != 2)
				continue;

			// 真搞不懂為何參數會自動在不同request之間保留住
			for (String field : allowedFields) {
				if (field.equals(tokens[0])) {
					params.putString("read_" + field, tokens[1]);
				} else {
					// 所以這裡做個重設參數的動作
					params.putString("read_" + field, "");
				}
			}
		}

	}

	public Result afterAction() {
		return ok(views.html.setting.list.render(params, page, forms.SettingForm.getHeaders(), rows));
	}

	public Result index() {

		beforeAction();

		// 記錄最後一次查詢路徑，做為編輯完畢導回用的
		session("LATEST_QUERY", request().uri());

		loadPage(true);

		return afterAction();
	}

	/**
	 * @param reload
	 *            決定是否重新由資料庫讀取，反之則是保持表單輸入內容。
	 */
	private void loadPage(boolean reload) {

		page = models.Setting.getPagedList(queryParams);
		rows = Lists.newArrayList();
		for (models.Setting item : page.getList()) {
			//
			// // 我是應該在view裡才做key/value的轉換
			// // 還是在form.fill時做key/value的轉換
			// // 還是根本不要用form呢?
			// // 若是在view裡做的話，遇到較複雜的運算仍是要回到controller做.
			// // formData本來就是希望做為view與controller之間用字串傳遞
			forms.SettingForm formData = new forms.SettingForm(item);
			// formData.danceDivision =
			// utils.CourseUtils.getDanceDivision(formData.danceDivision);
			// formData.choreography =
			// utils.CourseUtils.getChoreographie(formData.choreography);
			// formData.dayOfWeek =
			// utils.CourseUtils.getTheDayOfWeek(formData.dayOfWeek);
			// formData.level = utils.CourseUtils.getLevel(formData.level);
			// formData.location =
			// utils.CourseUtils.getLocation(formData.location);
			// formData.period = utils.CourseUtils.getPeriod(formData.period);
			//
			Form<forms.SettingForm> form = Form.form(forms.SettingForm.class).fill(formData);

			rows.add(form);
		}
		//
		// // List<Anniversary> anniversaries = Anniversary.findAll();
		// // formRows = Lists.newArrayList();
		// //
		// // for (Anniversary anniversary : anniversaries) {
		// // Form<AnniversaryForm> form =
		// // Form.form(AnniversaryForm.class).fill(new
		// // AnniversaryForm(anniversary));
		// // formRows.add(form);
		// // }
		// //
		// // if (reload) {
		// // formData = Form.form(AnniversaryForm.class).fill(new
		// // AnniversaryForm());
		// // }

	}

	public Result post() {
		beforeAction();

		DynamicForm data = Form.form().bindFromRequest();
		String action = data.get("action");

		// 收取多筆表單資料
		SettingForms forms = Form.form(SettingForms.class).bindFromRequest().get();
		List<Form<SettingForm>> rows = forms.getForms();

		// 檢查是否有選擇項目，且選擇項目是否有驗證錯誤
		boolean isSelectNothing = true;
		boolean hasErrors = false;
		for (Form<SettingForm> form : rows) {
			Logger.debug(form.data().get("selected"));
			if ("true".equals(form.data().get("selected")))
				continue;

			isSelectNothing = false;
			if (form.hasErrors())
				hasErrors = true;
		}

		// 新刪修時都必須選擇項目
		if (isSelectNothing) {
			flash("error", String.format("至少選擇一個項目。"));
		}

		// 新修時必須檢查輸入內容，刪則不必
		if (hasErrors) {
			flash("error", String.format("資料輸入錯誤，請檢查輸入內容。"));
		}

		// if ("create".equals(action)) {
		// Long createdId = create();
		// // 新增成功會取得新的ID，就以新的ID導到查詢頁面
		// if (createdId > 0)
		// return redirect(controllers.routes.CourseDetail.index(createdId));
		// } else if ("delete".equals(action)) {
		// Long deletedId = delete();
		// // 刪除成功會取得已刪除ID，否則便是刪除有錯
		// if (deletedId > 0) {
		// String laststQuery =
		// utils.StringUtils.getStringValue(session("LATEST_QUERY"), "");
		// if (laststQuery.length() == 0)
		// return redirect(controllers.routes.CourseList.index());
		// else
		// return redirect(laststQuery);
		// }
		// } else if ("update".equals(action)) {
		// Long updatedId = update();
		// }

		loadPage(true);

		return afterAction();
	}

}
