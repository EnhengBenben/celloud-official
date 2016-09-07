package com.celloud.box.utils;

import java.text.DecimalFormat;

public class SpeedUtil {
	public static String formatSpead(long size, long timeMillis) {
		return formatSize(size * 1000 / timeMillis) + "/s";
	}

	public static String formatSize(double size) {
		double result = size / 1;
		String unit = "b";
		if (result > 1024) {
			result = result / 1024;
			unit = "kb";
		}
		if (result > 1024) {
			result = result / 1024;
			unit = "mb";
		}
		if (result > 1024) {
			result = result / 1024;
			unit = "gb";
		}
		return new DecimalFormat("#.00").format(result) + "" + unit;
	}

	public static String formatTime(long timeMillis) {
		if (timeMillis < 1000) {
			return "< 1s";
		}
		int ss = 0, mm = 0, hh = 0;
		ss = (int) (timeMillis / 1000);
		int radix = 60;
		if (ss > radix) {
			ss = ss % radix;
			mm = ss / radix;
			if (mm > radix) {
				mm = mm % radix;
				hh = mm / radix;
			}
		}
		String result = "";
		if (hh > 0) {
			result = hh + "h";
		}
		if (mm > 0) {
			result = result + mm + "m";
		}
		if (ss > 0) {
			result = result + ss + "s";
		}
		return result;
	}

}
