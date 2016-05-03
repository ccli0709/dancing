package utils;

import java.util.Map;

import com.google.common.collect.Maps;

public class CourseUtils {

	public static Map<String, String> getChoreographies() {
		Map<String, String> choreographies = Maps.newLinkedHashMap();
		choreographies.put("", "[尚未選擇舞碼]");
		choreographies.put("1", "華爾滋");
		choreographies.put("2", "森巴");
		choreographies.put("3", "倫巴");
		choreographies.put("4", "探戈");
		choreographies.put("5", "六步吉魯巴");
		return choreographies;
	}

	public static String getChoreographie(String key) {
		Map<String, String> choreographies = getChoreographies();

		String value = "---";
		if (choreographies.containsKey(key))
			value = choreographies.get(key);

		return value;
	}

	public static Map<String, String> getLocations() {
		Map<String, String> locations = Maps.newLinkedHashMap();
		locations.put("", "[尚未選擇位置]");
		locations.put("1", "文化中心至善廳");
		locations.put("2", "小港中鋼舞蹈教室");
		locations.put("3", "鳳山青年公園（婦幼館旁）");
		return locations;
	}

	public static String getLocation(String key) {
		Map<String, String> locations = getLocations();

		String value = "---";
		if (locations.containsKey(key))
			value = locations.get(key);

		return value;
	}
	
	public static Map<String, String> getLevels() {
		Map<String, String> levels = Maps.newLinkedHashMap();
		levels.put("", "[尚未選擇級別]");
		levels.put("1", "休閒");
		levels.put("2", "標準");
		levels.put("3", "困難");
		return levels;
	}
	
	public static String getLevel(String key) {
		Map<String, String> levels = getLevels();

		String value = "---";
		if (levels.containsKey(key))
			value = levels.get(key);

		return value;
	}

	public static Map<String, String> getDayOfWeek() {
		Map<String, String> dayOfWeek = Maps.newLinkedHashMap();
		dayOfWeek.put("", "[尚未選擇日別]");
		dayOfWeek.put("1", "周一");
		dayOfWeek.put("2", "周二");
		dayOfWeek.put("3", "周三");
		dayOfWeek.put("4", "周四");
		dayOfWeek.put("5", "周五");
		dayOfWeek.put("6", "周六");
		dayOfWeek.put("7", "周日");
		return dayOfWeek;
	}

	public static String getTheDayOfWeek(String key) {
		Map<String, String> dayOfWeek = getDayOfWeek();

		String value = "---";
		if (dayOfWeek.containsKey(key))
			value = dayOfWeek.get(key);

		return value;
	}
	
	public static Map<String, String> getPeriods() {
		Map<String, String> periods = Maps.newLinkedHashMap();
		periods.put("", "[尚未選擇時段]");
		periods.put("1", "早");
		periods.put("2", "中");
		periods.put("3", "晚");
		return periods;
	}
	
	public static String getPeriod(String key) {
		Map<String, String> periods = getPeriods();

		String value = "---";
		if (periods.containsKey(key))
			value = periods.get(key);

		return value;
	}
}
