package com.celloud.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @Description:
 * @author lin
 * @date 2014-7-20 下午7:36:34
 */
public class ScreeningUtil {

    /**
     * 本方法适用于TB、EGFR、KRAS的筛选
     * 
     * @param path
     * @return
     */
    public static String screen(String path) {
	if (!new File(path).exists()) {
	    return "";
	}
	StringBuffer pos = new StringBuffer();
	StringBuffer res = new StringBuffer();
	BufferedReader br = null;
	try {
	    br = new BufferedReader(new FileReader(path));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	String line = null;
	try {
	    while ((line = br.readLine()) != null) {
		String l[] = line.split("\t");
		if (l.length < 4) {
		    continue;
		}
		int len = l[3].indexOf("-");
		int d = l[3].indexOf(",");
		int k = l[3].indexOf(")");
		if (len == -1) {
		    pos.append(l[1]).append("、");
		    res.append(l[l.length - 1]).append("、");
		} else {
		    if (len > 1) {
			String before = l[3].substring(len - 2, len - 1);
			String after = l[3].substring(len + 1, len + 3);
			if (before.trim().equals(after.trim())) {
			    if (d > -1 && k > -1) {
				double result = Double.parseDouble(l[3]
					.substring(d + 1, k));
				if (result < 5) {
				    pos.append(l[1]).append("、");
				    res.append(l[l.length - 1]).append("、");
				}
			    } else {
				pos.append(l[1]).append("、");
				res.append(l[l.length - 1]).append("、");
			    }
			} else {
			    pos.append(l[1]).append("、");
			    res.append(l[l.length - 1]).append("、");
			}
		    }
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (br != null) {
		    br.close();
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	String p = pos.toString();
	if (p.length() > 0) {
	    p = p.substring(0, p.length() - 1);
	} else {
	    p = "no result";
	}
	String r = res.toString();
	if (r.length() > 0) {
	    r = r.substring(0, r.length() - 1);
	} else {
	    r = "no result";
	}
	return p + "\t" + r;
    }

    /**
     * 本方法适用于TBINH
     * 
     * @param path
     * @return
     */
    public static String unitColumn(String path, boolean isKatG) {
	if (!new File(path).exists()) {
	    return "no result";
	}
	StringBuffer pos = new StringBuffer();
	BufferedReader br = null;
	try {
	    br = new BufferedReader(new FileReader(path));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	String line = null;
	try {
	    while ((line = br.readLine()) != null) {
		String l[] = line.split("\t");
		if (isKatG) {
		    if (l.length < 5) {
			continue;
		    }
		    String result = dealLine(line, 4);
		    if (!StringUtils.isEmpty(result)) {
			String regEx = "[^0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(result);
			pos.append(m.replaceAll("").trim()).append("、");
		    }
		} else {
		    if (l.length < 3) {
			continue;
		    }
		    String result = dealLine(line, 2);
		    if (!StringUtils.isEmpty(result)) {
			pos.append(result).append("、");
		    }
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (br != null) {
		    br.close();
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	String p = pos.toString();
	if (p.length() > 0) {
	    p = p.substring(0, p.length() - 1);
	} else {
	    p = "no result";
	}
	return p;
    }

    private static String dealLine(String line, int num) {
	StringBuffer pos = new StringBuffer();
	String l[] = line.split("\t");
	if (l.length < num + 1) {
	    return null;
	}
	int len = l[3].indexOf("-");
	int d = l[3].indexOf(",");
	int k = l[3].indexOf(")");
	if (len == -1) {
	    pos.append(l[num]);
	} else {
	    String before = l[3].substring(len - 2, len - 1);
	    String after = l[3].substring(len + 1, len + 3);
	    if (before.trim().equals(after.trim())) {
		if (d > -1 && k > -1) {
		    double result = Double
			    .parseDouble(l[3].substring(d + 1, k));
		    if (result < 5) {
			pos.append(l[num]);
		    }
		} else {
		    pos.append(l[num]);
		}
	    } else {
		pos.append(l[num]);
	    }
	}
	return pos.toString();
    }
}