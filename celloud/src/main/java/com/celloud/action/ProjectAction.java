package com.celloud.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.NoticeConstants;
import com.celloud.message.MessageUtils;
import com.celloud.message.category.MessageCategoryCode;
import com.celloud.message.category.MessageCategoryUtils;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.User;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.AppService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.TaskService;
import com.celloud.service.UserService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.Response;
import com.celloud.wechat.ParamFormat;
import com.celloud.wechat.ParamFormat.Param;
import com.celloud.wechat.constant.WechatParams;

/**
 * 项目操作类
 * 
 * @author lin
 * @date 2016-1-7 下午2:54:28
 */
@RequestMapping(value = "/project")
@Controller
public class ProjectAction {
    Logger logger = LoggerFactory.getLogger(ProjectAction.class);
    @Resource
    private ProjectService projectService;
    @Resource
    private UserService userService;
    @Resource
    private ReportService reportService;
    @Resource
    private TaskService taskService;
    @Resource
    private AppService appService;
	@Resource
	private MessageCategoryUtils mcu;

    /**
     * 修改项目
     * 
     * @param project
     * @return
     * @date 2016-1-8 下午1:43:28
     */
    @ActionLog(value = "修改项目信息（项目名）", button = "修改项目")
    @RequestMapping("update")
    @ResponseBody
	public Response update(Project project) {
		return projectService.update(project) == 1 ? Response.SUCCESS_UPDATE() : Response.FAIL();
    }

    /**
     * 删除项目
     * 
     * @param projectId
     * @return
     * @date 2016-1-8 下午3:24:30
     */
    @ActionLog(value = "项目软删除", button = "删除项目")
    @RequestMapping("deleteByState")
    @ResponseBody
    public Integer deleteByState(Integer projectId) {
        return projectService.deleteByState(projectId);
    }

    /**
     * 共享项目
     * 
     * @param userNames
     *            ：要共享的用户名
     * @param projectId
     *            ：要共享的项目
     * @return
     * @date 2016-1-7 下午7:00:10
     */
    @ActionLog(value = "将项目共享给其他用户", button = "共享项目")
	@RequestMapping(value = "shareProject")
    @ResponseBody
	public Response share(String userNames, Integer projectId) {
		String loginUser = ConstantsData.getLoginUserName().toLowerCase();
		if (loginUser.equals(userNames) || userNames.indexOf(loginUser) > -1) {
			return new Response("项目不能共享给自己！");
		}
        String error = "";
        String userIds = "";
        if (StringUtils.isNotEmpty(userNames)) {
            String username[] = userNames.split(",");
            for (String uname : username) {
                User user = userService.getUserByName(uname);
                if (user == null) {
                    error += uname + ",";
                } else {
                    userIds += "" + user.getUserId() + ",";
                }
            }
            if (StringUtils.isNotEmpty(error)) {
                error = error.substring(0, error.length() - 1);
                error = "用户" + error + "不存在！";
				return new Response(error);
            }
        }
        Integer userId = ConstantsData.getLoginUserId();
        projectService.deleteShareFromMe(userId, projectId);
		if (StringUtils.isNotEmpty(userIds)) {
			projectService.addShare(userId, projectId, userIds);
			Project p = projectService.selectByPrimaryKey(projectId);
			String projectName = p.getProjectName();
			String projectID = p.getProjectId().toString();
			User user = userService.selectUserById(userId);
			String shareUserName = user.getUsername();
			List<User> userList = userService.selectUserByIds(userIds.substring(0, userIds.length() - 1));
			for (int i = 0; i < userList.size(); i++) {
				User shareTo = userList.get(i);
				//构造桌面消息
				MessageUtils mu = MessageUtils.get().on(Constants.MESSAGE_USER_CHANNEL).send(NoticeConstants
						.createMessage("share", "项目共享", "收到" + shareUserName + "共享的项目【" + projectName + "】"));
				//构造邮件内容
				AliEmail aliEmail = AliEmail.template(EmailType.PROJECT_SHARE).substitutionVars(
						AliSubstitution.sub().set(EmailParams.PROJECT_SHARE.shareUserName.name(), shareUserName)
								.set(EmailParams.PROJECT_SHARE.home.name(), ConstantsData.getContextUrl())
								.set(EmailParams.PROJECT_SHARE.userName.name(), shareTo.getUsername())
								.set(EmailParams.PROJECT_SHARE.dataName.name(), projectName)
								.set(EmailParams.PROJECT_SHARE.dataKey.name(), projectID));
				//构造微信发送消息
				Param params = ParamFormat.param()
						.set(WechatParams.SHARE.first.name(), "您好，用户 " + shareUserName + " 分享给您一个项目", "#222222")
						.set(WechatParams.SHARE.keyword1.name(), projectName, null)
						.set(WechatParams.SHARE.keyword2.name(), projectID, null)
						.set(WechatParams.SHARE.remark.name(), "您可以登录平台查看项目报告", "#222222");
				mcu.sendMessage(shareTo.getUserId(), MessageCategoryCode.SHARE, aliEmail, params, mu, null);
			}
		}
		return Response.SUCCESS();
    }

    /**
     * 删除共享来的项目
     * 
     * @param projectId
     * @return
     * @date 2016-1-7 下午5:01:59
     */
    @ActionLog(value = "删除一条共享来的项目记录，项目本身不删除", button = "删除共享项目")
    @RequestMapping("deleteShare")
    @ResponseBody
	public Response deleteShare(Integer projectId) {
        Integer userId = ConstantsData.getLoginUserId();
        // 删除一条共享记录，修改一条项目记录
        Integer num = projectService.deleteShareToMe(userId, projectId);
		return num == 2 ? Response.SUCCESS_DELETE() : Response.FAIL();
    }

    /**
     * 查询项目共享给了哪些用户
     * 
     * @param projectId
     * @return
     * @author lin
     * @date 2016年1月25日下午3:24:00
     */
    @ActionLog(value = "查询项目共享给了哪些用户", button = "共享项目")
    @RequestMapping("getShareTo")
    @ResponseBody
    public List<Map<String, Object>> getShareTo(Integer projectId) {
        Integer userId = ConstantsData.getLoginUserId();
        return projectService.getShareTo(userId, projectId);
    }
}
