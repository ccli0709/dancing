package controllers;

import java.util.List;
import java.util.Map;

import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class WeekClass extends Controller {

	// 用於全局的參數
	Map<String, String> params;
	forms.QueryParams queryParams;

	// Master(List)
	PagedList<models.WeekClass> page;
	List<Form<forms.WeekClassForm>> rows;

	// Detail

	public void beforeAction() {
		params = Maps.newHashMap();

		// common
		params.put("title", "每周上課清單");
		params.put("connected", session("connected"));

		//
		queryParams = new forms.QueryParams(request());
		params.put("pageIndex", String.valueOf(queryParams.getPageIndex()));
		params.put("sortField", queryParams.getSortField());
		params.put("sortDirection", queryParams.getSortDirection());
		params.put("queryString", queryParams.getQueryString());
	}

	public Result afterAction() {
		return ok(views.html.weekClass.index.render(params, page, forms.WeekClassForm.getHeaders(), rows));
	}

	public Result list() {

		beforeAction();

		loadPage(true);

		return afterAction();
	}

	/**
	 * @param reload
	 *            決定是否重新由資料庫讀取，反之則是保持表單輸入內容。
	 */
	private void loadPage(boolean reload) {

		page = models.WeekClass.getPagedList(queryParams);
		rows = Lists.newArrayList();
		for (models.WeekClass item : page.getList()) {
			Form<forms.WeekClassForm> form = Form.form(forms.WeekClassForm.class).fill(new forms.WeekClassForm(item));
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
