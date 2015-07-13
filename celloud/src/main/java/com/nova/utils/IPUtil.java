package com.nova.utils;

import com.handsome.ip.IPSeeker;

public class IPUtil {
	/**
	 * 根据ip地址获取所在地区
	 * @param ip
	 * @param fileDir
	 * @return
	 */
	public static String getAddreeByIp(String ip,String fileDir){
		IPSeeker ipSeeker = new IPSeeker("qqwry.dat", fileDir);
		return ipSeeker.getIPLocation(ip).getCountry()+"-" + ipSeeker.getIPLocation(ip).getArea();
	}
}
