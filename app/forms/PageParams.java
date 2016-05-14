package forms;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class PageParams {

	protected Map<String, Integer> integers = Maps.newHashMap();
	protected Map<String, String> strings = Maps.newHashMap();
	protected Map<String, Map<String, String>> maps = Maps.newHashMap();
	protected Map<String, List<String>> lists = Maps.newHashMap();

	public PageParams() {
	}

	public Integer getInteger(String key) {
		Integer value = integers.get(key);
		if (value == null)
			value = 0;
		return value;
	}

	public void putInteger(String key, Integer value) {
		integers.put(key, value);
	}

	public String getString(String key) {
		String value = strings.get(key);
		if (value == null)
			value = "";
		return value;
	}

	public void putString(String key, String value) {
		strings.put(key, value);
	}

	public List<String> getList(String key) {
		List<String> value = lists.get(key);
		if (value == null)
			value = Lists.newArrayList();
		return value;
	}

	public void putList(String key, List<String> value) {
		lists.put(key, value);
	}
	
	public Map<String, String> getMap(String key) {
		Map<String, String> value = maps.get(key);
		if (value == null)
			value = Maps.newLinkedHashMap();
		return value;
	}

	public void putMap(String key, Map<String, String> value) {
		maps.put(key, value);
	}

}
