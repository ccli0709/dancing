package controllers;

import java.util.List;

import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.SettingUtils;
import utils.SecurityUtils;

public class CourseList extends Controller {

	// 用於全局的參數
	forms.PageParams params = new forms.PageParams();
	forms.QueryParams queryParams;

	// Master(List)
	PagedList<models.Course> page;
	List<Form<forms.CourseForm>> rows;

	public void beforeAction() {

		// 畫面
		params.putString("title", "每周上課清單");
		// 登入
		SecurityUtils.SetLoginParams(params);
		// 分頁
		queryParams = new forms.QueryParams(request());
		params.putString("pageIndex", String.valueOf(queryParams.getPageIndex()));
		params.putString("sortField", queryParams.getSortField());
		params.putString("sortDirection", queryParams.getSortDirection());
		params.putString("queryCondition", queryParams.getQueryCondition());

		// 自有
		params.putMap("choreographies", utils.CourseUtils.getChoreographies());
		params.putMap("locations", utils.CourseUtils.getLocations());
		params.putMap("levels", utils.CourseUtils.getLevels());
		params.putMap("dayOfWeek", utils.CourseUtils.getDayOfWeek());

		// 取得本次查詢用的參數
		List<String> allowedFields = Lists.newArrayList("choreography", "location", "level");
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
		return ok(views.html.course.list.render(params, page, forms.CourseForm.getHeaders(), rows));
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

		page = models.Course.getPagedList(queryParams);
		rows = Lists.newArrayList();
		for (models.Course item : page.getList()) {

			// 我是應該在view裡才做key/value的轉換
			// 還是在form.fill時做key/value的轉換
			// 還是根本不要用form呢?
			// 若是在view裡做的話，遇到較複雜的運算仍是要回到controller做.
			// formData本來就是希望做為view與controller之間用字串傳遞
			forms.CourseForm formData = new forms.CourseForm(item);
			formData.danceDivision = utils.CourseUtils.getDanceDivision(formData.danceDivision);
			formData.choreography = utils.CourseUtils.getChoreographie(formData.choreography);
			formData.dayOfWeek = utils.CourseUtils.getTheDayOfWeek(formData.dayOfWeek);
			formData.level = utils.CourseUtils.getLevel(formData.level);
			formData.location = utils.CourseUtils.getLocation(formData.location);
			formData.period = utils.CourseUtils.getPeriod(formData.period);

			Form<forms.CourseForm> form = Form.form(forms.CourseForm.class).fill(formData);

			rows.add(form);
		}

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
