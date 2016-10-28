package com.celloud.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Description:map根据value值排序，返回字符串
 * @author lin
 * @date 2014-11-5 下午8:17:53
 */
public class MapSort {
	/**
	 * @param k
	 *            :要排序的Map<String,Integer>
	 * @return
	 */
	public static String sort(Map<String, Integer> k) {
		StringBuffer sb = new StringBuffer();
		ArrayList<Entry<String, Integer>> l = new ArrayList<Entry<String, Integer>>(k.entrySet());

		Collections.sort(l, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});

		for (Entry<String, Integer> e : l) {
			sb.append(e.getKey()).append("\t").append(e.getValue())
					.append("\n");
		}
		return sb.toString();
	}
}