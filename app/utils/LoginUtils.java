package utils;

public class LoginUtils {

	public static void SetPagingParams(forms.PageParams params, forms.QueryParams queryParams) {
		params.putString("pageIndex", String.valueOf(queryParams.getPageIndex()));
		params.putString("sortField", queryParams.getSortField());
		params.putString("sortDirection", queryParams.getSortDirection());
		params.putString("queryString", queryParams.getQueryString());
	}
}
