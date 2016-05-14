package forms;

import java.util.ArrayList;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import play.data.Form;
import play.libs.Json;

public class CourseDateForms {

	// 收取輸入多筆資料的類別
	public String action;
	
	// 在view裡不得使用rows做為變數,會掛掉
	public ArrayList<CourseDateForm> courseDateRows = Lists.newArrayList();

	// 把收取資料的類別再包裝成Play的Form格式
	public ArrayList<Form<CourseDateForm>> getRows() {

		ArrayList<Form<CourseDateForm>> result = Lists.newArrayList();

		// 必須使用bind或bindFromRequest才能觸發驗證，使用fill是不行的。
		// 也可以解釋成fill就是讓開發者自由填入資料用的。
		for (CourseDateForm row : courseDateRows) {
			Form<CourseDateForm> form = Form.form(CourseDateForm.class);
			form = form.bind(Json.toJson(row));
			result.add(form);
		}

		return result;
	}
}
