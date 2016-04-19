package forms;

import java.util.Map;

import com.google.common.collect.Maps;

public class PageParams {

	protected Map<String, String> strings = Maps.newHashMap();
	protected Map<String, Map<String, String>> maps = Maps.newHashMap();

	public PageParams() {
	}

	public String getString(String key) {
		String value = strings.get(key);
		if (value == null)
			value = "";
		return value;
	}

	public Map<String, String> getMap(String key) {
		Map<String, String> value = maps.get(key);
		if (value == null)
			value = Maps.newLinkedHashMap();
		return value;
	}

	public void putString(String key, String value) {
		strings.put(key, value);
	}

	public void putMap(String key, Map<String, String> value) {
		maps.put(key, value);
	}

}
