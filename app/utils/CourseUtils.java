package utils;

import java.util.Map;

import com.google.common.collect.Maps;

public class CourseUtils {


	

	public static Map<String, String> getDanceDivisions() {
		Map<String, String> danceDivisions = models.Setting.findMapByType("PA");
		return danceDivisions;
	}

	public static String getDanceDivision(String key) {
		Map<String, String> danceDivisions = getDanceDivisions();

		String value = "---";
		if (danceDivisions.containsKey(key))
			value = danceDivisions.get(key);

		return value;
	}
	
	public static Map<String, String> getQuantities() {
		Map<String, String> quantities = models.Setting.findMapByType("PG");
		return quantities;
	}

	public static String getQuantity(String key) {
		Map<String, String> quantities = getQuantities();

		String value = "---";
		if (quantities.containsKey(key))
			value = quantities.get(key);

		return value;
	}

	
	public static Map<String, String> getChoreographies() {
		Map<String, String> choreographies = models.Setting.findMapByType("PC");
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
		Map<String, String> locations = models.Setting.findMapByType("PD");
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
		Map<String, String> levels = models.Setting.findMapByType("PI");
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
		Map<String, String> dayOfWeek = models.Setting.findMapByType("PE");
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
		Map<String, String> periods = models.Setting.findMapByType("PF");
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
