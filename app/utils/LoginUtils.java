package utils;

import play.mvc.Http;
import play.mvc.Http.Session;

public class LoginUtils {

	public static void SetLoginParams(forms.PageParams params) {
		Session session = Http.Context.current().session();
		params.putString("loginUid", session.get("loginUid"));
		params.putString("loginEmail", session.get("loginEmail"));
		params.putString("loginName", session.get("loginName"));
	}

	public static void SetPagingParams(forms.PageParams params, forms.QueryParams queryParams) {
		params.putString("pageIndex", String.valueOf(queryParams.getPageIndex()));
		params.putString("sortField", queryParams.getSortField());
		params.putString("sortDirection", queryParams.getSortDirection());
		params.putString("queryString", queryParams.getQueryString());
	}
}
