package com.nova.tools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:获取进度阶段工具类
 * @author lin
 * @date 2013-10-30 上午11:44:19
 */
public class StepMapUtil {
    public static Map<String, String> getStep() {
	Map<String, String> map = new HashMap<String, String>();
	String path = StepMapUtil.class.getResource("/Step.txt").getPath();
	try {
	    BufferedReader br = new BufferedReader(new FileReader(
		    new File(path)));
	    String line = "";
	    String id = "";
	    List<String> list = null;
	    int count = 0;
	    while ((line = br.readLine()) != null) {
		count++;
		if ("".equals(line.trim())) {
		    continue;
		} else if (line.startsWith("##")) {
		    if (count > 1) {
			map.put(id,
				list.toString().replace("[", "")
					.replace("]", ""));
		    }
		    id = br.readLine();
		    list = new ArrayList<String>();
		    continue;
		} else {
		    list.add(line);
		}
	    }
	    map.put(id, list.toString().replace("[", "").replace("]", ""));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return map;
    }

    public static void main(String[] args) {
	Map<String, String> map = getStep();
	String l1 = map.get("9");
	System.out.println(l1);
    }
}
