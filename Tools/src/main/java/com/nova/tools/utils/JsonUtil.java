package com.nova.tools.utils;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.celloud.mongo.sdo.Data;

public class JsonUtil {

    @SuppressWarnings("unchecked")
    public static Map<String, List<Data>> parseDataMap(String json) {
	Map<String, List<Data>> map = new HashMap<String, List<Data>>();
	JSONObject jsonObj = JSONObject.parseObject(json);
	Map<String, JSONArray> mstr = JSONObject.toJavaObject(jsonObj,
		Map.class);
	for (Map.Entry<String, JSONArray> entry : mstr.entrySet()) {
	    List<Map<String, Object>> jsonList = parseList(JSONArray
		    .toJSONString(entry.getValue()));
	    List<Data> list = new ArrayList<Data>();
	    for (Map<String, Object> m : jsonList) {
		Data data = mapToJavaBean(Data.class, m);
		list.add(data);
	    }
	    map.put(entry.getKey(), list);
	}
	return map;
    };

    /**
     * 
     * 将json数组处理返回list
     * 
     * @author lrc
     * @version 1.0
     * @date 2015年5月6日 下午3:43:06
     * @param json
     * @return
     */
    @SuppressWarnings({ "unchecked", "static-access" })
    public static List<Map<String, Object>> parseList(String json) {
	List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
	JSONArray jsonArry = JSONArray.parseArray(json);
	if (jsonArry == null || jsonArry.size() == 0) {
	    return jsonList;
	}
	for (int i = 0; i < jsonArry.size(); i++) {
	    JSONObject jsonObj = jsonArry.getJSONObject(i);
	    Map<String, Object> map = jsonObj.toJavaObject(jsonObj, Map.class);
	    jsonList.add(map);
	}
	return jsonList;
    }

    /**
     * 功能：将map转换成Javabean
     * 
     * @param c
     * @param dataMap
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> T mapToJavaBean(Class<T> c, Map<String, Object> dataMap) {
	try {
	    T bean = c.newInstance();
	    // 得到对象的所有方法
	    Method[] methods = c.getDeclaredMethods();
	    Method method;
	    String field = "";
	    // 参数类型
	    Class[] parameterTypes;
	    // 参数值
	    Object param = null;
	    String className;
	    String value;
	    // 遍历所有的方法
	    for (int i = 0, len = methods.length; i < len; i++) {
		method = methods[i];
		try {
		    field = method.getName();
		    // 找到set方法
		    if (field.startsWith("set")) {
			field = field.substring(field.indexOf("set") + 3);
			field = field.toLowerCase().charAt(0)
				+ field.substring(1);
			parameterTypes = method.getParameterTypes();
			if (dataMap.containsKey(field)) {
			    if (parameterTypes.length > 0) {
				value = dataMap.get(field).toString();
				className = parameterTypes[0].getName();
				if ("int".equals(className)) {
				    param = Integer.parseInt(value.trim());
				} else if ("java.lang.Integer"
					.equals(className)) {
				    param = new Integer(value.trim());
				} else if ("long".equals(className)) {
				    param = Long.parseLong(value.trim());
				} else if ("java.lang.Long".equals(className)) {
				    param = new Long(value.trim());
				} else if ("java.lang.String".equals(className)) {
				    param = new String(value);
				} else if ("java.sql.Timestamp"
					.equals(className)) {
				    param = new Timestamp(Long.parseLong(value
					    .trim()));
				} else if ("float".equals(className)) {
				    param = Float.parseFloat(value.trim());
				} else if ("java.lang.Float".equals(className)) {
				    param = new Float(value.trim());
				} else if ("java.util.Date".equals(className)) {
				    SimpleDateFormat format = new SimpleDateFormat(
					    "yyyy-MM-dd HH:mm:ss");
				    param = format.parse(value.trim());
				}
			    }
			    // 执行set方法并传入实参
			    // 这里会抛出一个异常IllegalArgumentException: argument type
			    // mismatch,不需处理
			    method.invoke(bean, new Object[] { param });
			}
		    }
		} catch (Exception e) {
		    // e.printStackTrace();
		}
	    }
	    return bean;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
}
