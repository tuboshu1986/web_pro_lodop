package util;

import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TemplateServiceUtil {

	public static JSONObject setVal(JSONObject json, Map<String, String> map){
		JSONArray array = new JSONArray();
		JSONObject tmp = (JSONObject)json.get("elems");
		
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			array.add(((JSONObject)tmp.get(key)).element("val", map.get(key)));
		}
		tmp = new JSONObject();
		tmp.element("fontSize", json.get("fontSize"));
		tmp.element("fontName", json.get("fontName"));
		tmp.element("elems", array);
		
		return tmp;
	}
	
}
