package utils;

import java.util.Map;

import com.google.common.collect.Maps;

public class SettingUtils {
	public static Map<String, String> getTypes() {
		//List<models.Setting> settings = models.Setting.find.all();
		Map<String, String> types = Maps.newHashMap();
		types.put("H1","教師經歷");
		types.put("PA","舞科");
		types.put("PB","舞碼");
		types.put("PC","???");
		types.put("PD","上課地點");
		types.put("PE","星期");
		types.put("PF","時段");
		types.put("PG","堂數");
		types.put("PI","級別");
		types.put("NEWS","公告事項");

		return types;
	}

	public static String getType(String key) {
		Map<String, String> types = getTypes();

		String value = "---";
		if (types.containsKey(key))
			value = types.get(key);

		return value;
	}
}
