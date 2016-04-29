package utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class StringUtils {

	public static String getStringValue(String input, String defaultValue) {
		String value;

		if (input == null) {
			value = defaultValue.trim();
		} else {
			value = input.trim();
		}

		return value;
	}

	public static int getIntValue(String input, int defaultValue) {
		int value;

		try {
			value = Integer.parseInt(input);
		} catch (Exception e) {
			value = defaultValue;
		}

		return value;
	}

	public static Long getLongValue(String input, Long defaultValue) {
		Long value;

		try {
			value = Long.parseLong(input);
		} catch (Exception e) {
			value = defaultValue;
		}

		return value;
	}

	public static DateTime getDateTimeValue(String input, DateTime defaultValue, String dateTimeFormat) {
		DateTime value;

		try {
			DateTimeFormatter formatter = DateTimeFormat.forPattern(dateTimeFormat);
			value = formatter.parseDateTime(input);
		} catch (Exception e) {
			value = defaultValue;
		}

		return value;
	}
}
