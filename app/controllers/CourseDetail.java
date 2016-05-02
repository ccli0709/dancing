package controllers;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.avaje.ebean.PagedList;
import com.google.common.collect.Maps;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class CourseDetail extends Controller {

	// 用於全局的參數
	forms.PageParams params = new forms.PageParams();
	forms.QueryParams queryParams;

	// Master(List)
	PagedList<models.Course> page;
	List<Form<forms.CourseForm>> rows;

	// Detail
	Form<forms.CourseForm> form;

	public void beforeAction() {

		// common
		params.putString("title", "每周上課清單");
		params.putString("loginUid", session("loginUid"));
		params.putString("loginEmail", session("loginEmail"));
		params.putString("loginName", session("loginName"));

		// 舞碼下拉選單
		Map<String, String> choreographies = Maps.newLinkedHashMap();
		choreographies.put("", "選擇舞碼");
		choreographies.put("華爾滋", "華爾滋");
		choreographies.put("森巴", "森巴");
		choreographies.put("倫巴", "倫巴");
		choreographies.put("探戈", "探戈");
		choreographies.put("六步吉魯巴", "六步吉魯巴");
		params.putMap("choreographies", choreographies);

		// 位置下拉選單
		Map<String, String> locations = Maps.newLinkedHashMap();
		locations.put("", "選擇位置");
		locations.put("文化中心至善廳", "文化中心至善廳");
		locations.put("小港中鋼舞蹈教室", "小港中鋼舞蹈教室");
		locations.put("鳳山青年公園（婦幼館旁）", "鳳山青年公園（婦幼館旁）");
		params.putMap("locations", locations);

		// 位置下拉選單
		Map<String, String> levels = Maps.newLinkedHashMap();
		levels.put("", "選擇級別");
		levels.put("休閒", "休閒");
		levels.put("標準", "標準");
		levels.put("困難", "困難");
		params.putMap("levels", levels);

		// 分頁相關參數
		queryParams = new forms.QueryParams(request());
		params.putString("pageIndex", String.valueOf(queryParams.getPageIndex()));
		params.putString("sortField", queryParams.getSortField());
		params.putString("sortDirection", queryParams.getSortDirection());
		params.putString("queryString", queryParams.getQueryString());

	}

	public Result afterAction() {
		return ok(views.html.course.detail.render(params, form));
	}

	@Security.Authenticated(Secured.class)
	public Result index(Long id) {

		beforeAction();

		queryParams.setId(id);
		loadPage(true);

		return afterAction();
	}

	@Security.Authenticated(Secured.class)
	public Result post(Long id) {
		beforeAction();

		DynamicForm data = Form.form().bindFromRequest();
		String action = data.get("_action");

		if ("create".equals(action)) {
			Long createdId = create();
			// 新增成功會取得新的ID，就以新的ID導到查詢頁面
			if (createdId > 0)
				return redirect(controllers.routes.CourseDetail.index(createdId));
		} else if ("delete".equals(action)) {
			Long deletedId = delete();
			// 刪除成功會取得已刪除ID，否則便是刪除有錯
			if (deletedId > 0) {
				String laststQuery = utils.StringUtils.getStringValue(session("LATEST_QUERY"), "");
				if (laststQuery.length() == 0)
					return redirect(controllers.routes.CourseMaster.index());
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

		form = Form.form(forms.CourseForm.class).bindFromRequest();
		// 這裡先不用FORM的VALIDATION
		if (form.hasErrors()) {
			loadPage(false);
			flash("error", String.format("表單輸入內容有誤，更新失敗。"));
		} else {
			forms.CourseForm formData = form.get();
			models.Course weekClass = new models.Course();
			weekClass.setDanceDivision(formData.danceDivision);
			weekClass.setChoreography(formData.choreography);
			weekClass.setDayOfWeek(formData.dayOfWeek);
			weekClass.setPeriod(formData.period);
			weekClass.setBeginTime(formData.beginTime);
			weekClass.setEndTime(formData.endTime);
			weekClass
					.setBeginDate(utils.StringUtils.getDateTimeValue(formData.beginDate, DateTime.now(), "yyyy-MM-dd"));
			weekClass.setEndDate(utils.StringUtils.getDateTimeValue(formData.endDate, DateTime.now(), "yyyy-MM-dd"));
			weekClass.setLevel(formData.level);
			weekClass.setQuantity(utils.StringUtils.getLongValue(formData.quantity, 0L));
			weekClass.setLocation(formData.location);
			weekClass.save();

			createdId = weekClass.getId();
		}

		return createdId;
	}

	private Long update() {
		Long updatedId = 0L;

		form = Form.form(forms.CourseForm.class).bindFromRequest();
		// 這裡先不用FORM的VALIDATION
		if (form.hasErrors()) {
			loadPage(false);
			flash("error", String.format("表單輸入內容有誤，更新失敗。"));
		} else {
			forms.CourseForm formData = form.get();

			updatedId = utils.StringUtils.getLongValue(formData.id, 0L);

			models.Course weekClass = models.Course.find.byId(updatedId);
			weekClass.setDanceDivision(formData.danceDivision);
			weekClass.setChoreography(formData.choreography);
			weekClass.setDayOfWeek(formData.dayOfWeek);
			weekClass.setPeriod(formData.period);
			weekClass.setBeginTime(formData.beginTime);
			weekClass.setEndTime(formData.endTime);
			weekClass
					.setBeginDate(utils.StringUtils.getDateTimeValue(formData.beginDate, DateTime.now(), "yyyy-MM-dd"));
			weekClass.setEndDate(utils.StringUtils.getDateTimeValue(formData.endDate, DateTime.now(), "yyyy-MM-dd"));
			weekClass.setLevel(formData.level);
			weekClass.setQuantity(utils.StringUtils.getLongValue(formData.quantity, 0L));
			weekClass.setLocation(formData.location);
			weekClass.update();

			queryParams.setId(updatedId);
			loadPage(true);
			flash("success", String.format("課程資料更新完成（編號%s）。", updatedId));
		}

		return updatedId;
	}

	private Long delete() {
		Long deletedId = 0L;

		form = Form.form(forms.CourseForm.class).bindFromRequest();
		// 這裡先不用FORM的VALIDATION
		if (form.hasErrors()) {
			loadPage(false);
			flash("error", String.format("表單輸入內容有誤，更新失敗。"));
		} else {

			forms.CourseForm formData = form.get();
			deletedId = utils.StringUtils.getLongValue(formData.id, 0L);
			models.Course weekClass = models.Course.find.byId(deletedId);
			weekClass.delete();

			flash("success", String.format("課程資料刪除完成（編號%s）。", deletedId));
		}

		return deletedId;
	}

	public Result aaa() {
		return index(0L);
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
		models.Course item = models.Course.find.byId(queryParams.getId());
		form = Form.form(forms.CourseForm.class).fill(new forms.CourseForm(item));

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
