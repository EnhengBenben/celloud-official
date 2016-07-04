package com.celloud.wechat;

import java.util.HashMap;
import java.util.Map;

public class ParamFormat {

    public static Param param() {
        return new Param();
    }

    public static ParamAll paramAll() {
        return new ParamAll();
    }

    public static class Param {

        private Map<String, HashMap<String, String>> param = new HashMap<String, HashMap<String, String>>();

        public Param set(String name, String value) {
            HashMap<String, String> values = new HashMap<>();
            values.put("value", value);
            values.put("color", "#173177");
            param.put(name, values);
            return this;
        }

        public HashMap<String, String> get(String name) {
            return param.get(name);
        }

        protected Map<String, HashMap<String, String>> get() {
            return param;
        }
    }

    public static class ParamAll {
        private Map<String, Object> map = new HashMap<>();

        public ParamAll add(Param param, String openId, String templateId) {
            map.put("data", param);
            map.put("touser", openId);
            map.put("template_id", templateId);
            map.put("url", "http://weixin.qq.com/download");
            return this;
        }

        public Map<String, Object> toParamMap(Param param, String openId,
                String templateId) {
            map.put("data", param);
            map.put("touser", openId);
            map.put("template_id", templateId);
            map.put("url", "http://weixin.qq.com/download");
            return this.map;
        }
    }
}
