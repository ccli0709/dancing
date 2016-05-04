package controllers;

import org.joda.time.DateTime;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class CourseDetail extends Controller {

	// 用於全局的參數
	forms.PageParams params = new forms.PageParams();
	forms.QueryParams queryParams;


	// Detail
	Form<forms.CourseForm> form;

	public void beforeAction() {

		// 畫面
		params.putString("title", "每周上課清單");
		// 登入
		utils.SecurityUtils.SetLoginParams(params);
		// 分頁
		queryParams = new forms.QueryParams(request());
		utils.LoginUtils.SetPagingParams(params, queryParams);
		// 自有
		params.putMap("choreographies", utils.CourseUtils.getChoreographies());
		params.putMap("locations", utils.CourseUtils.getLocations());
		params.putMap("levels", utils.CourseUtils.getLevels());
		params.putMap("dayOfWeek", utils.CourseUtils.getDayOfWeek());
		params.putMap("periods", utils.CourseUtils.getPeriods());
	}

	public Result afterAction() {
		return ok(views.html.course.detail.render(params, form));
	}

	@Security.Authenticated(utils.SecurityUtils.class)
	public Result index(Long id) {

		beforeAction();

		queryParams.setId(id);
		loadPage(true);

		return afterAction();
	}

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
					return redirect(controllers.routes.CourseList.index());
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
