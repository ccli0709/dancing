package forms;

import com.google.common.base.Strings;

import play.mvc.Http.Request;

public class QueryParams {

	private Long id;

	private int pageIndex;
	private int pageSize;
	private String sortField;
	private String sortDirection;
	private String queryString;

	public QueryParams(Request request) {

		String id = request.getQueryString("id");
		try {
			this.setId(Long.parseLong(id));
		} catch (Exception e) {
			this.setId(0L);
		}

		this.setPageSize(10);

		try {
			int p = Integer.parseInt(request.getQueryString("p"));
			this.setPageIndex(p);
		} catch (Exception ex) {
			this.setPageIndex(1);
		}

		String s = request.getQueryString("s");
		if (Strings.isNullOrEmpty(s))
			this.setSortField("id");
		else
			this.setSortField(s.trim());

		String d = request.getQueryString("d");
		if (!"desc".equals(d))
			d = "asc";
		this.setSortDirection(d);

		String q = request.getQueryString("q");
		if (Strings.isNullOrEmpty(q))
			this.setQueryString("");
		else
			this.setQueryString(q.trim());
	}

	public String getQueryUrlParams() {
		return String.format("&p=%s&s=%s&d=%s&q=%s", pageIndex, sortField, sortDirection, queryString);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

}
