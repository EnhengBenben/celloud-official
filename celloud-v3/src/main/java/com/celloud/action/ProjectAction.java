package com.celloud.action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.constants.ConstantsData;
import com.celloud.model.Project;
import com.celloud.model.User;
import com.celloud.service.ProjectService;
import com.celloud.service.UserService;

/**
 * 项目操作类
 * 
 * @author lin
 * @date 2016-1-7 下午2:54:28
 */
@RequestMapping(value = "/project")
@Controller
public class ProjectAction {
    @Resource
    private ProjectService projectService;
    @Resource
    private UserService userService;

    /**
     * 修改项目
     * 
     * @param project
     * @return
     * @date 2016-1-8 下午1:43:28
     */
    @RequestMapping("update")
    @ResponseBody
    public Integer update(Project project) {
        return projectService.update(project);
    }

    /**
     * 删除项目
     * 
     * @param projectId
     * @return
     * @date 2016-1-8 下午3:24:30
     */
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
    @RequestMapping("shareProject")
    @ResponseBody
    public String share(String userNames, Integer projectId) {
        String error = "";
        String userIds = "";
        if (!StringUtils.isEmpty(userNames)) {
            String username[] = userNames.split(",");
            for (String uname : username) {
                User user = userService.getUserByName(uname);
                if (user == null) {
                    error += uname + ",";
                } else {
                    userIds += "" + user.getUserId() + ",";
                }
            }
            if (!StringUtils.isEmpty(error)) {
                error = error.substring(0, error.length() - 1);
                error = "用户" + error + "不存在！";
                return error;
            }
        }
        Integer userId = ConstantsData.getLoginUserId();
        projectService.deleteShareFromMe(userId, projectId);
        if (!StringUtils.isEmpty(userIds)) {
            projectService.addShare(userId, projectId, userIds);
        }
        return error;
    }

    /**
     * 删除共享来的项目
     * 
     * @param projectId
     * @return
     * @date 2016-1-7 下午5:01:59
     */
    @RequestMapping("deleteShare")
    @ResponseBody
    public boolean deleteShare(Integer projectId) {
        Integer userId = ConstantsData.getLoginUserId();
        // 删除一条共享记录，修改一条项目记录
        Integer num = projectService.deleteShareToMe(userId, projectId);
        return num == 2;
    }
}
