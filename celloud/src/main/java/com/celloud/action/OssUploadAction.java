package com.celloud.action;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.OSSConfig;
import com.celloud.service.DataService;
import com.celloud.service.OSSConfigService;
import com.celloud.utils.FileTools;

/**
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年11月8日下午2:29:01
 * @version Revision: 1.0
 */
@Controller
@RequestMapping("oss/upload")
public class OssUploadAction {
	@Resource
	private OSSConfigService service;
	@Resource
	private DataService dataService;

	@RequestMapping(value = "postPolicy", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> postPolicy(String name) throws UnsupportedEncodingException {
		Map<String, String> respMap = new LinkedHashMap<String, String>();
		String ext = FileTools.getExtName(name);
		OSSConfig config = service.getLatest();
		OSSClient client = new OSSClient(config.getEndpoint(), config.getKeyId(), config.getKeySecret());
		long expireEndTime = System.currentTimeMillis() + 1000 * 60 * 60 * 24;// policy有效时间：24小时
		Date expiration = new Date(expireEndTime);
		PolicyConditions policyConds = new PolicyConditions();
		String dir = "file/" + ConstantsData.getLoginUserId() + "/"
				+ new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);// 使用此policy上传的文件不能超过10GB
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);// 使用此policy上传的文件必须以dir开头
		String postPolicy = client.generatePostPolicy(expiration, policyConds);
		byte[] binaryData = postPolicy.getBytes("utf-8");
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);
		respMap.put("policy", encodedPolicy);
		String postSignature = client.calculatePostSignature(postPolicy);
		respMap.put("accessid", config.getKeyId());
		respMap.put("signature", postSignature);
		// respMap.put("expire", formatISO8601Date(expiration));
		respMap.put("dir", dir);
		respMap.put("host", config.getBucket() + "." + config.getEndpoint());
		respMap.put("expire", String.valueOf(expireEndTime / 1000));
		respMap.put("ext", ext);
		return respMap;
	}

	@RequestMapping(value = "newfile")
	public ResponseEntity<?> newfile(String objectKey) {
		int dataId = dataService.addAndRunFile(ConstantsData.getLoginUserId(), objectKey);
		return dataId == 0 ? ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null) : ResponseEntity.ok(null);
	}
}
