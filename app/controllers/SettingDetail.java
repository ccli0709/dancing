package controllers;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class SettingDetail extends Controller {

	// 用於全局的參數
	forms.PageParams params = new forms.PageParams();
	forms.QueryParams queryParams;

	// Detail
	Form<forms.SettingForm> form;

	public void beforeAction() {

		// common
		params.putString("title", "每周上課清單");
		params.putString("connected", session("connected"));

		// 分頁相關參數
		queryParams = new forms.QueryParams(request());
		params.putString("pageIndex", String.valueOf(queryParams.getPageIndex()));
		params.putString("sortField", queryParams.getSortField());
		params.putString("sortDirection", queryParams.getSortDirection());
		params.putString("queryString", queryParams.getQueryString());

	}

	public Result afterAction() {
		return ok(views.html.setting.detail.render(params, form));
	}

	public Result post(Long id) {
		beforeAction();

		DynamicForm data = Form.form().bindFromRequest();
		String action = data.get("_action");

		if ("create".equals(action)) {
			Long createdId = create();
			// 新增成功會取得新的ID，就以新的ID導到查詢頁面
			if (createdId > 0)
				return redirect(controllers.routes.WeekClassDetail.index(createdId));
		} else if ("delete".equals(action)) {
			Long deletedId = delete();
			// 刪除成功會取得已刪除ID，否則便是刪除有錯
			if (deletedId > 0) {
				String laststQuery = utils.StringUtils.getStringValue(session("LATEST_QUERY"), "");
				if (laststQuery.length() == 0)
					return redirect(controllers.routes.WeekClassMaster.index());
				else
					return redirect(laststQuery);
			}
		} else if ("update".equals(action)) {
			Long updatedId = update();
		}
		return afterAction();
	}

	private Long create() {
		Long createdId = 0L;

		form = Form.form(forms.SettingForm.class).bindFromRequest();
		// 這裡先不用FORM的VALIDATION
		if (form.hasErrors()) {
			loadPage(false);
			flash("error", String.format("表單輸入內容有誤，更新失敗。"));
		} else {
			forms.SettingForm formData = form.get();
			models.Setting announcement = new models.Setting();
			announcement.setType(formData.type);
			announcement.setValue1(formData.value1);
			announcement.setValue2(formData.value2);
			announcement.setValue3(formData.value3);
			announcement.setValue4(formData.value4);
			announcement.setCreatedTime(DateTime.now());
			announcement.save();
			createdId = announcement.getId();
		}

		return createdId;
	}

	private Long update() {
		Long updatedId = 0L;

		form = Form.form(forms.SettingForm.class).bindFromRequest();
		// 這裡先不用FORM的VALIDATION
		if (form.hasErrors()) {
			loadPage(false);
			flash("error", String.format("表單輸入內容有誤，更新失敗。"));
		} else {
			forms.SettingForm formData = form.get();

			updatedId = utils.StringUtils.getLongValue(formData.id, 0L);

			models.Setting announcement = models.Setting.find.byId(updatedId);
			announcement.setValue1(formData.value1);
			announcement.setValue2(formData.value2);
			announcement.setValue3(formData.value3);
			announcement.setValue4(formData.value4);
			announcement.setUpdatedTime(DateTime.now());
			announcement.update();

			queryParams.setId(updatedId);
			loadPage(true);
			flash("success", String.format("課程資料更新完成（編號%s）。", updatedId));
		}

		return updatedId;
	}

	private Long delete() {
		Long deletedId = 0L;

		form = Form.form(forms.SettingForm.class).bindFromRequest();
		// 這裡先不用FORM的VALIDATION
		if (form.hasErrors()) {
			loadPage(false);
			flash("error", String.format("表單輸入內容有誤，更新失敗。"));
		} else {

			forms.SettingForm formData = form.get();
			deletedId = utils.StringUtils.getLongValue(formData.id, 0L);
			models.Setting setting = models.Setting.find.byId(deletedId);
			setting.delete();

			flash("success", String.format("課程資料刪除完成（編號%s）。", deletedId));
		}

		return deletedId;
	}

	public Result aaa() {
		return index(0L);
	}

	public Result index(Long id) {

		beforeAction();

		queryParams.setId(id);
		loadPage(true);

		return afterAction();
	}

	/**
	 * @param reload
	 *            決定是否重新由資料庫讀取，反之則是保持表單輸入內容。
	 */
	private void loadPage(boolean reload) {

		// 多筆，目前都先包成Form
		// page = models.WeekClass.getPagedList(queryParams);
		// rows = Lists.newArrayList();
		// for (models.WeekClass item : page.getList()) {
		// Form<forms.WeekClassForm> form =
		// Form.form(forms.WeekClassForm.class).fill(new
		// forms.WeekClassForm(item));
		// rows.add(form);
		// }

		// 單筆，目前都先包成Form
		models.Setting item = models.Setting.find.byId(queryParams.getId());
		form = Form.form(forms.SettingForm.class).fill(new forms.SettingForm(item));

		// List<Anniversary> anniversaries = Anniversary.findAll();
		// formRows = Lists.newArrayList();
		//
		// for (Anniversary anniversary : anniversaries) {
		// Form<AnniversaryForm> form =
		// Form.form(AnniversaryForm.class).fill(new
		// AnniversaryForm(anniversary));
		// formRows.add(form);
		// }
		//
		// if (reload) {
		// formData = Form.form(AnniversaryForm.class).fill(new
		// AnniversaryForm());
		// }

	}

}
