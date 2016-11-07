package com.celloud.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.celloud.service.ReportAPIService;
import com.celloud.utils.MD5Util;

@Service
public class ReportAPIServiceImpl implements ReportAPIService {

	private static final String celLoudId = "celloudId";
	private static final String celLoudSecret = "celloudSecret";

	@Override
	public Map<String, String> createAccount() {
		Map<String, String> map = new HashMap<>();
		String celloudId = getUUID();
		String celloudSecret = getCelLoudSecret();
		map.put(celLoudId, celloudId);
		map.put(celLoudSecret, celloudSecret);
		String token = getToken();
		System.out.println(getUUID().length());
		System.out.println(celloudId.length());
		System.out.println(celloudSecret.length());
		System.out.println(token.length());
		System.out.println(getRefreshToken().length());
		return map;
	}

	private String getObjectId() {
		return new ObjectId().toString();
	}

	private String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}


	private String getCelLoudSecret() {
		return DigestUtils.sha1Hex(DigestUtils.sha512Hex(getUUID()));
	}

	private String getToken() {
		String base = getUUID() + getCelLoudSecret();
		return DigestUtils.sha256Hex(DigestUtils.sha1Hex(base));
	}

	private String getRefreshToken() {
		String base = getObjectId() + getCelLoudSecret();
		return DigestUtils.sha1Hex(MD5Util.getMD5(base));
	}

}
