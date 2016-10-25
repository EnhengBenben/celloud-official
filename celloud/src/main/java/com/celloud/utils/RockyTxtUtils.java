package com.celloud.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RockyTxtUtils {
	public static void main(String[] args) throws Exception {
		File file = new File("/Users/sun8wd/Documents/rocky/variation_clinical_significance_20161014.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		String title = "";
		String value = "";
		Map<String, String> map = new HashMap<>();
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() == 0) {
				System.out.println("===");
				continue;
			}
			String temp = parseTitle(line);
			if (temp != null) {
				map.put(title, value);
				value = "";
				title = temp;
			} else {
				value += line.trim() + "\n";
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		// mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.writeValue(new File("/Users/sun8wd/Documents/rocky/Rocky.txt"), map);
		reader.close();
		// with open('data.json', 'r') as f:
		// data = json.load(f)

	}

	public static String parseTitle(String line) {
		String[] temps = line.trim().split("_");
		if (temps.length != 4 || !isNumber(temps[0].trim()) || !"VariationID".equals(temps[2].trim())
				|| !isNumber(temps[3].trim())) {
		}
		if (temps.length != 4) {
			return null;
		}
		if (!isNumber(temps[0].trim())) {
			return null;
		}
		if (!temps[1].trim().toLowerCase().equals("brca1") && !temps[1].trim().toLowerCase().equals("brca2")) {
			return null;
		}
		if (!"VariationID".equals(temps[2].trim())) {
			return null;
		}
		if (!isNumber(temps[3].trim())) {
			return null;
		}
		return temps[1].trim() + "-" + temps[3].trim();
	}

	public static boolean isNumber(String temp) {
		try {
			Integer.parseInt(temp);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
