package models;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Entity
public class Setting extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	protected String type;
	protected String value1;
	protected String value2;
	protected String value3;
	protected String value4;

	@Column(columnDefinition = "datetime")
	protected DateTime createdTime = new DateTime();

	@Column(columnDefinition = "datetime")
	protected DateTime updatedTime = new DateTime();

	@Column(columnDefinition = "datetime")
	protected DateTime deletedTime = new DateTime();

	public static Finder<Long, Setting> find = new Finder<Long, Setting>(Setting.class);

	public static Setting getTeacherExperience() {
		Setting result = new Setting();
		List<Setting> results = find.where().eq("type", "H1").findList();
		if (results.size() > 0)
			result = results.get(0);

		return result;
	}

	public static List<Setting> getLatestNews() {
		return find.where().eq("type", "NEWS").findList();
	}	

	public static Map<String, String> findMapByType(String type) {
		Map<String, String> result = Maps.newLinkedHashMap();

		List<Setting> settings = find.where().eq("type", type).orderBy("value2").findList();
		for (Setting setting : settings) {
			String key = setting.getValue1();
			String value = setting.getValue2();

			if (result.containsKey(key))
				continue;

			result.put(key, value);
		}
		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public DateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(DateTime createdTime) {
		this.createdTime = createdTime;
	}

	public DateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(DateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public DateTime getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(DateTime deletedTime) {
		this.deletedTime = deletedTime;
	}

	public static String getNextValue1(String type) {
		String value1 = "1";
		ExpressionList<Setting> where = find.where();
		where.add(Expr.eq("type", type)).orderBy(String.format("%s desc", "value1"));
		List<Setting> settings = where.findList();
		if (settings.size() > 0) {
			Integer _value1 = Integer.parseInt(settings.get(0).value1);
			value1 = String.format("%d", _value1 + 1);
		}
		
		return value1;
	}

	public static PagedList<Setting> getPagedList(forms.QueryParams queryParams) {
		ExpressionList<Setting> where = find.where();

		if (queryParams.getQueryCondition().length() > 0) {

			List<String> allowedFields = Lists.newArrayList("type");
			for (String query : queryParams.getQueryCondition().split(",")) {

				String[] tokens = query.split(":");
				if (tokens.length != 2)
					continue;

				for (String field : allowedFields) {
					if (field.equals(tokens[0])) {
						where.add(Expr.contains(field, tokens[1]));
					}
				}
			}

			// where.disjunction().add(Expr.contains("facebook_name",
			// queryParam.getQueryString()))
			// .add(Expr.contains("address", queryParam.getQueryString()))
			// .add(Expr.contains("chinese_name", queryParam.getQueryString()));
		}
		//
		// // 搜尋結果排序
		// where.orderBy(String.format("%s %s", queryParams.getSortField(),
		// queryParams.getSortDirection()));
		where.orderBy(String.format("%s asc,%s asc", "type", "value1"));
		return where.findPagedList(queryParams.getPageIndex() - 1, queryParams.getPageSize());
	}

}