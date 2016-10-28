package com.celloud.wechat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public class ParamFormat {

    public static Param param() {
        return new Param();
    }

    public static ParamAll paramAll() {
        return new ParamAll();
    }

    public static class Param {

        private Map<String, HashMap<String, String>> param = new HashMap<String, HashMap<String, String>>();

        public Param set(String name, String value, String color) {
            HashMap<String, String> values = new HashMap<>();
            values.put("value", value);
            values.put("color", StringUtils.isEmpty(color) ? "#173177" : color);
            param.put(name, values);
            return this;
        }

        public HashMap<String, String> get(String name) {
            return this.param.get(name);
        }

        protected Map<String, HashMap<String, String>> get() {
            return this.param;
        }
    }

    public static class ParamAll {
        private Map<String, Object> map = new HashMap<>();

        public ParamAll template(String templateId) {
            map.put("template_id", templateId);
            return this;
        }

        public ParamAll openId(String openId) {
            map.put("touser", openId);
            return this;
        }

        public ParamAll url(String url) {
            map.put("url", StringUtils.isEmpty(url)
                    ? "https://www.celloud.cn/login" : url);
            return this;
        }

        public ParamAll data(Param param) {
            map.put("data", param.get());
            return this;
        }

        public Map<String, Object> get() {
            return this.map;
        }
    }
}
