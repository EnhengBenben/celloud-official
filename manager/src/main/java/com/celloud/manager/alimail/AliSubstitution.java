package com.celloud.manager.alimail;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件参数
 *
 */
public class AliSubstitution {

	public static Sub sub() {
		return new Sub();
	}

	public static class Sub {

		private Map<String, String> sub = new HashMap<String, String>();

		public Sub set(String name, String value) {
			sub.put(name, value);
			return this;
		}

		public String get(String name) {
			return sub.get(name);
		}

		protected Map<String, String> get() {
			return sub;
		}
	}

}
