package controllers;

import java.util.List;
import java.util.Map;

import com.avaje.ebean.Expr;
import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class WeekClassMaster extends Controller {

	// 用於全局的參數
	forms.PageParams params = new forms.PageParams();
	forms.QueryParams queryParams;

	// Master(List)
	PagedList<models.WeekClass> page;
	List<Form<forms.WeekClassForm>> rows;

	// Detail

	public void beforeAction() {

		// common
		params.putString("title", "每周上課清單");
		params.putString("connected", session("connected"));

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

		// 取得本次查詢用的參數
		List<String> allowedFields = Lists.newArrayList("choreography", "location", "level");
		for (String query : queryParams.getQueryString().split(",")) {

			String[] tokens = query.split(":");
			if (tokens.length != 2)
				continue;

			for (String field : allowedFields) {
				if (field.equals(tokens[0])) {
					params.putString("read_" + field, tokens[1]);
				}
			}
		}

	}

	public Result afterAction() {
		return ok(views.html.weekClass.index.render(params, page, forms.WeekClassForm.getHeaders(), rows));
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
