package com.celloud.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliEmailUtils;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.User;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.ActionLogService;
import com.celloud.service.ClientService;
import com.celloud.service.DataService;
import com.celloud.service.PythonService;
import com.celloud.service.UploadService;
import com.celloud.service.UserService;
import com.celloud.utils.CheckFileTypeUtil;
import com.celloud.utils.DataUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.MD5Util;
import com.celloud.utils.PerlUtils;
import com.celloud.utils.PropertiesUtil;


@Service("pythonServiceImpl")
public class PythonServiceImpl implements PythonService {
    Logger log = LoggerFactory.getLogger(PythonServiceImpl.class);
    private String path = PropertiesUtil.bigFilePath;
    @Resource
	private AliEmailUtils emailUtils;
    @Resource
    private UserService userService;
    @Resource
    private ActionLogService logService;
    @Resource
    private DataService dataService;
    @Resource
    private ClientService clientService;
    @Resource
    private UploadService uploadService;

    @Override
    public String login(String username, String pwd) {
        User user = new User();
        user.setPassword(MD5Util.getMD5(pwd));
        user.setUsername(username);
        User loginUser = userService.login(user);
        if (loginUser == null) {
            log.info("Python客户端，用户：" + username + "登陆失败");
            return 0 + "";
        }
        log.info("Python客户端，用户：" + username + "登陆成功");
        return loginUser.getUserId() + "";
    }

    @Override
    public Long getSize(Integer userId) {
        return dataService.sumData(userId);
    }

    @Override
    public Long getNumber(Integer userId) {
        // TODO int 转 long
        return Long.valueOf(String.valueOf(dataService.countData(userId)));
    }

    @Override
    public String getClientVersion() {
        return clientService.getLast().getVersion();
    }

    @Override
    public String getDataKey(Integer userId, String fileName, String md5) {
        DataFile data = new DataFile();
        data.setUserId(userId);
        // 只允许字母和数字
        String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(fileName);
        // replaceAll()将中文标号替换成英文标号
        data.setFileName(m.replaceAll("").trim());
        data.setMd5(md5);
        data.setState(DataState.DEELTED);
        int dataId = dataService.addDataInfo(data);
        String dataKey = DataUtil.getNewDataKey(dataId);
        String newName = dataKey + FileTools.getExtName(fileName);
        data.setFileId(dataId);
        data.setDataKey(dataKey);
        dataService.updateByPrimaryKeySelective(data);
        return newName;
    }

    @Override
    public Integer threadUpdate(Integer userId, String dataKey, long num, long position) {
        int flag = uploadService.uploadUpdate(userId, dataKey, num, position);
        if (flag == 0) {
            flag = uploadService.uploadInsert(userId, dataKey, num, position);
        }
        return flag;
    }

    @Override
    public String threadRead(Integer userId, String dataKey) {
        // TODO 排序实现有问题，先存list，再存数组，然后排序，最后转字符串
        List<Long> list = uploadService.getUploadList(userId, dataKey);
        if (list == null || list.size() == 0) {
            return null;
        }
        Long[] pos = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            pos[i] = list.get(i);
        }
        Arrays.sort(pos);
        StringBuffer sb = new StringBuffer();
        for (Long l : pos) {
            sb.append(l).append(",");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    @Override
    public String uploaded(String dataKey) {
		String celloudPath = this.getClass().getResource("").getPath().split("WEB-INF")[0];
        String absPath = path + dataKey;// 文件的绝对路径
        File file = new File(absPath);
        if (!file.exists()) {
            return "false";
        }
        String key = dataKey.substring(0, dataKey.indexOf("."));
        DataFile data = dataService.getDataByKey(key);
        data.setSize(FileTools.getFileSize(absPath));
        CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();
		int fileFormat = checkFileType.checkFileType(dataKey, path);
        data.setFileFormat(fileFormat);
        if (fileFormat == FileFormat.BAM) {
            StringBuffer command = new StringBuffer();
			String outPath = celloudPath + "temp/" + dataKey;
			String perlPath = celloudPath + "resources/plugins/getAliases.pl";
            command.append("perl ").append(perlPath).append(" ").append(absPath).append(" ").append(outPath);
            PerlUtils.excutePerl(command.toString());
            data.setAnotherName(FileTools.getFirstLine(outPath));
            new File(outPath).delete();
        }
        data.setDataKey(key);
        data.setState(DataState.ACTIVE);
        data.setPath(absPath);
        dataService.updateDataInfoByFileId(data);
        log.info("文件：" + dataKey + "上传成功");
        return "true";
    }

    @Override
	public void sendEmail(Integer userId, String fileName, String dataKey) {
		log.info("Python客户端，用户：" + userId + "上传文件" + fileName + "完成，发送邮件。");
		User user = userService.selectUserById(userId);

		AliEmail aliEmail = AliEmail.template(EmailType.UPLOAD_OVER)
				.substitutionVars(AliSubstitution.sub().set(EmailParams.UPLOAD_OVER.dataName.name(), fileName)
						.set(EmailParams.UPLOAD_OVER.home.name(), ConstantsData.getContextUrl())
						.set(EmailParams.UPLOAD_OVER.userName.name(), user.getUsername()));
		emailUtils.simpleSend(aliEmail, user.getEmail());
	}

    @Override
    public long saveDataSize(String dataKey, long size) {
        DataFile data = dataService.getDataByKey(dataKey);
        data.setSize(size);
        return dataService.updateDataInfoByFileId(data);
    }

    @Override
    public long getDataSize(Integer userId, String dataKey) {
        return dataService.getDataByKey(dataKey).getSize();
    }

}
