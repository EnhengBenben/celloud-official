package com.celloud.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliSubstitution;
import com.celloud.common.mq.entity.TaskTracingMessage;
import com.celloud.common.mq.entity.TrackStatus;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.constants.NoticeConstants;
import com.celloud.constants.TaskPeriod;
import com.celloud.mapper.AppMapper;
import com.celloud.mapper.DataFileMapper;
import com.celloud.mapper.TaskMapper;
import com.celloud.mapper.UserMapper;
import com.celloud.message.MessageUtils;
import com.celloud.message.category.MessageCategoryCode;
import com.celloud.message.category.MessageCategoryUtils;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Task;
import com.celloud.model.mysql.User;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.DataService;
import com.celloud.service.RunService;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.MD5Util;
import com.celloud.utils.PropertiesUtil;
import com.celloud.wechat.ParamFormat;
import com.celloud.wechat.ParamFormat.Param;
import com.celloud.wechat.constant.WechatParams;

@Service
public class TaskTracingServiceImpl implements TaskTracingService {
	private static Logger logger = LoggerFactory.getLogger(TaskTracingServiceImpl.class);
	@Resource
	private TaskMapper taskMapper;
	@Resource
	private AppMapper appMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private DataFileMapper dataMapper;
	@Resource
	private MessageCategoryUtils mcu;
	@Resource
	private DataService dataService;
	@Resource
	private RunService runService;

	@Override
	public boolean finish(TaskTracingMessage message) {
		Task task = taskMapper.selectByPrimaryKey(message.getTaskId());
		TrackStatus status = message.getStatus();
		boolean result = true;
		if (TrackStatus.ERR.equals(status)) {
			result = false;
			task.setPeriod(TaskPeriod.ERROR);
			task.setEndDate(new Date());
			task.setRemark("BatchCompute执行失败！");
			taskMapper.updateByPrimaryKeySelective(task);
		}
		Integer userId = task.getUserId();
		Integer appId = task.getAppId();
		String dataKey = task.getDataKey();
		App app = appMapper.selectByPrimaryKey(appId);
		String appName = app.getAppName();
		User user = userMapper.selectByPrimaryKey(userId);
		String username = user.getUsername();
		Integer projectId = task.getProjectId();
		String startDate = DateUtil.getDateToString(task.getStartDate(), DateUtil.YMDHMS);
		String endDate = DateUtil.getDateToString(task.getEndDate(), DateUtil.YMDHMS);
		DataFile dataFile = dataMapper.selectByDataKey(dataKey);
		String batch = dataFile.getBatch();
		Integer tagId = dataFile.getTagId();
		int nameIndex = dataFile.getFileName().lastIndexOf("R1");
		String tipsName = dataFile.getFileName().substring(0,
				nameIndex == -1 ? dataFile.getFileName().length() : nameIndex);
		// 构造桌面消息
		MessageUtils mu = MessageUtils.get().on(Constants.MESSAGE_USER_CHANNEL).send(NoticeConstants
				.createMessage("task", "运行完成", "文件【" + tipsName + "】运行应用【" + appName + "】" + (result ? "完成" : "失败")));
		AliEmail aliEmail = AliEmail.template(EmailType.RUN_OVER)
				.substitutionVars(AliSubstitution.sub().set(EmailParams.RUN_OVER.userName.name(), username)
						.set(EmailParams.RUN_OVER.home.name(), ConstantsData.getContextUrl())
						.set(EmailParams.RUN_OVER.projectName.name(), tipsName)
						.set(EmailParams.RUN_OVER.app.name(), appName).set(EmailParams.RUN_OVER.start.name(), startDate)
						.set(EmailParams.RUN_OVER.end.name(), endDate));
		// 构造微信消息
		Param params = ParamFormat.param()
				.set(WechatParams.RUN_OVER.first.name(), "您好，您的数据" + tipsName + " 运行" + (result ? "结束" : "失败"),
						"#222222")
				.set(WechatParams.RUN_OVER.keyword1.name(), appName, "#222222")
				.set(WechatParams.RUN_OVER.keyword2.name(), startDate, "#222222")
				.set(WechatParams.RUN_OVER.keyword3.name(), endDate, "#222222");
		String wechatUrl = null;
		if (appId.equals(123) && result) {// 华木兰要追加跳转页面
			params.set(WechatParams.RUN_OVER.remark.name(), "点击下方详情查看报告", "#222222");
			wechatUrl = ConstantsData.getContextUrl() + "wechat_rocky.html?projectId=" + projectId + "&dataKey="
					+ dataKey + "&appId=" + appId;
			logger.info("华木兰微信提醒wechatUrl：" + wechatUrl);
		}
		mcu.sendMessage(userId, MessageCategoryCode.REPORT, aliEmail, params, mu, wechatUrl);
		if (appId == 113) {
			String inPath = new StringBuffer().append(ConstantsData.getOfsPath()).append("output/").append(userId)
					.append("/").append(appId).append("/").append(dataKey).append("/").append("result/split/")
					.toString();
			HashSet<String> resultFiles = FileTools.getFiles(inPath);
			if (resultFiles != null) {
				Iterator<String> rFile = resultFiles.iterator();
				Long size = null;
				while (rFile.hasNext()) {
					String fstr = rFile.next();
					if (!fstr.equals("...tar.gz") && !fstr.equals("..tar.gz")) {
						String extName = fstr.substring(fstr.lastIndexOf(".tar.gz"));
						String resourcePath = inPath + fstr;
						size = new File(resourcePath).length();
						DataFile data = new DataFile();
						data.setUserId(userId);
						data.setFileName(fstr);
						data.setState(DataState.DEELTED);
						int dataId = dataService.addDataInfo(data);
						String new_dataKey = DataUtil.getNewDataKey(dataId);
						String folderByDay = PropertiesUtil.bigFilePath + userId + File.separator
								+ DateUtil.getDateToString("yyyyMMdd");
						File pf = new File(folderByDay);
						if (!pf.exists()) {
							pf.mkdirs();
						}
						String filePath = folderByDay + File.separatorChar + new_dataKey + extName;
						boolean state = FileTools.nioTransferCopy(new File(resourcePath), new File(filePath));
						if (state) {
							data.setFileId(dataId);
							data.setDataKey(new_dataKey);
							data.setAnotherName(tipsName);
							data.setSize(size);
							data.setPath(filePath);
							data.setFileFormat(FileFormat.FQ);
							data.setState(DataState.ACTIVE);
							data.setBatch(batch);
							data.setMd5(MD5Util.getFileMD5(filePath));
							dataService.updateDataInfoByFileIdAndTagId(data, tagId);
							// TODO 需要去掉写死的自动运行
							Integer bsiApp = Constants.bsiTags.get(tagId);
							if (bsiApp != null) {
								logger.info("bsi自动运行split分数据");
								runService.runSingle(userId, bsiApp, Arrays.asList(data));
							}
						}
					}
				}
			}
		}
		return true;
	}
}
