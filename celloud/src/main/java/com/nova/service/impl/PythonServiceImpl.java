package com.nova.service.impl;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.nova.constants.DataState;
import com.nova.constants.FileFormat;
import com.nova.email.EmailService;
import com.nova.sdo.Data;
import com.nova.sdo.User;
import com.nova.service.IPythonService;
import com.nova.utils.CheckFileTypeUtil;
import com.nova.utils.DataUtil;
import com.nova.utils.FileTools;
import com.nova.utils.PerlUtils;
import com.nova.utils.PropertiesUtil;
import com.nova.utils.SQLUtils;
import com.nova.utils.TemplateUtil;

public class PythonServiceImpl implements IPythonService {
	Logger log = Logger.getLogger(PythonServiceImpl.class);
	private String path = PropertiesUtil.bigFilePath;
	private CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();
	private SQLUtils sql = new SQLUtils();

	@Override
	public String login(final String username, String pwd) {
		log.info("Python客户端，用户：" + username + "尝试登陆");
		User user = sql.login(username, pwd);
		if (user == null) {
			log.info("Python客户端，用户：" + username + "登陆失败");
			return 0 + "";
		} else {
			log.info("Python客户端，用户：" + username + "登陆成功");
			Integer userId = user.getUserId();
			new Runnable() {
				public void run() {
					sql.addLog(username);
				}
			}.run();
			return userId + "";
		}
	}

	@Override
	public long getSize(int id) {
		return sql.getSize(id);
	}

	@Override
	public long getNumber(int id) {
		return sql.getMyOwnDataNum(id);
	}

	@Override
	public String getDataKey(Integer id, String fileName,String md5) {
		Data data = new Data();
		data.setUserId(id);
		// 只允许字母和数字
		String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(fileName);
		data.setFileName(m.replaceAll("").trim());
        if (md5 != null) {
            data.setMd5(md5);
        }
        int dataId = sql.addDataInfo(data);
        data.setFileId(dataId);
        String dataKey = DataUtil.getNewDataKey(dataId);
		String newName = dataKey + FileTools.getExtName(fileName);
		data.setDataKey(dataKey);
		data.setPath(path + newName);
        data.setState(DataState.ACTIVE);
        sql.updateDataInfoByFileId(data);
		log.info("为用户：" + id + "返回" + newName);
		return newName;
	}

	@Override
	public String uploaded(String dataKey) {
		String absPath = path + dataKey;// 文件的绝对路径
		File file = new File(absPath);
		if (!file.exists()) {
			return "false";
		}
		String key = dataKey.substring(0, dataKey.indexOf("."));
		Data data = new Data();
		data.setSize(FileTools.getFileSize(absPath));
		int fileFormat = checkFileType.checkFileType(dataKey);
		data.setFileFormat(fileFormat);
		if (fileFormat == FileFormat.BAM) {
			StringBuffer command = new StringBuffer();
			String outPath = System.getProperty("user.dir").replace("bin",
					"webapps/celloud")
					+ "/temp/" + dataKey;
			String perlPath = System.getProperty("user.dir").replace("bin",
					"webapps/celloud")
					+ "/plugins/getAliases.pl";
			command.append("perl ").append(perlPath).append(" ")
					.append(absPath).append(" ").append(outPath);
			PerlUtils.excutePerl(command.toString());
			data.setAnotherName(FileTools.getFirstLine(outPath));
			new File(outPath).delete();
		}
		data.setDataKey(key);
		sql.updateData(data);
		log.info("文件：" + dataKey + "上传成功");
		return "true";
	}

	@Override
	public String getClientVersion() {
		return sql.getClient().getVersion();
	}

	@Override
	public long getDataSize(int id, String dataKey) {
		return sql.getDataSize(id,dataKey);
	}

	@Override
	public long saveDataSize(String dataKey, long size) {
		return sql.updateData(dataKey, size);
	}

    @Override
    public void sendEmail(Integer userId, String fileName, String dataKey) {
        log.info("Python客户端，用户：" + userId + "上传文件" + fileName + "完成，发送邮件。");
        String email = sql.getEmail(userId);
        String context = TemplateUtil.readTemplate(8);
        context = context.replace("#dataName", fileName);
        EmailService.send(email, context, true);
    }
}
