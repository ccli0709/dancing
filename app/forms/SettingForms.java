package forms;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import play.data.Form;
import play.libs.Json;

public class SettingForms {

	// 收取輸入多筆資料的類別
	public String action;
	public ArrayList<SettingForm> rows = Lists.newArrayList();

	// 把收取資料的類別再包裝成Play的Form格式
	public ArrayList<Form<SettingForm>> getForms() {

		ArrayList<Form<SettingForm>> result = Lists.newArrayList();

		// 必須使用bind或bindFromRequest才能觸發驗證，使用fill是不行的。
		// 也可以解釋成fill就是讓開發者自由填入資料用的。
		for (SettingForm row : rows) {
			Form<SettingForm> form = Form.form(SettingForm.class);
			form = form.bind(Json.toJson(row));
			result.add(form);
		}

		return result;
	}

}