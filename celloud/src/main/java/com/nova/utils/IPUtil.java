package com.nova.utils;

import java.io.IOException;
import java.nio.file.Paths;

import com.github.jarod.qqwry.IPZone;
import com.github.jarod.qqwry.QQWry;

public class IPUtil {
    /**
     * 根据ip地址获取所在地区
     * 
     * @param ip
     * @param fileDir
     * @return
     */
    public static String getAddreeByIp(String ip, String fileDir) {
	QQWry qqwry = null;
	try {
	    qqwry = new QQWry(Paths.get(fileDir + "/qqwry.dat"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	String myIP = "223.223.192.105";
	IPZone ipzone = qqwry.findIP(myIP);
	return ipzone.getMainInfo() + "-" + ipzone.getSubInfo();
    }
}