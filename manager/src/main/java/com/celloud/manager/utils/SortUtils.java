package com.celloud.manager.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.celloud.manager.model.Tree;

/**
 * 排序
 * 
 * @author lin
 * @date 2016年11月15日 下午3:51:11
 */
public class SortUtils {

	public static <T extends Tree> List<T> listToTree(List<T> list) {
		Map<Integer, List<T>> map = new HashMap<>();
		for (T r : list) {
			if (map.containsKey(r.getParentId())) {
				map.get(r.getParentId()).add(r);
			} else {
				List<T> temp = new ArrayList<>();
				temp.add(r);
				map.put(r.getParentId(), temp);
			}
		}
		List<T> pageList = new ArrayList<>();
		recursionResource(map, pageList, 0);
		return pageList;
	}

	private static <T extends Tree> void recursionResource(Map<Integer, List<T>> map, List<T> pageList,
			Integer parentId) {
		for (T r : map.get(parentId)) {
			pageList.add(r);
			if (map.containsKey(r.getId())) {
				recursionResource(map, pageList, r.getId());
			}
		}
	}

}
