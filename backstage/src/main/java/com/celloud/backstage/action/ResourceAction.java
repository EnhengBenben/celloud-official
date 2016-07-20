package com.celloud.backstage.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.constants.ConstantsData;
import com.celloud.backstage.model.SecResource;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.SecResourceService;

/**
 * @author MQ:
 * @date 2016年7月15日 下午2:09:13
 * @description 资源管理
 */
@Controller
@RequestMapping("resource")
public class ResourceAction {

    private Logger logger = LoggerFactory.getLogger(ResourceAction.class);

    @Resource
    private SecResourceService resourceService;
    
    /**
     * @author MQ
     * @date 2016年7月18日下午3:03:53
     * @description 分页查询
     *
     */
    @RequestMapping("pageQuery")
    public ModelAndView pageQuery(Page page, String keyword) {
        logger.info("用户{}查看资源列表", ConstantsData.getLoginUserName());
        ModelAndView mv = new ModelAndView("permission/resource_main");
        PageList<SecResource> pageList = resourceService.pageQuery(page, keyword);
        mv.addObject("pageList", pageList);
        mv.addObject("keyword", keyword);
        return mv;
    }
    
    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:03:43
     * @description 查询所有资源
     *
     */
    @RequestMapping("findAll")
    @ResponseBody()
    public List<SecResource> findAll() {
        return resourceService.findAll();
    }

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:03:19
     * @description 校验名称是否重复
     *
     */
    @RequestMapping("checkName")
    @ResponseBody
    public int checkName(String name, Integer id) {
        return resourceService.checkNameRepeat(name, id);
    }

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:03:05
     * @description 校验表达式是否重复
     *
     */
    @RequestMapping("checkPermission")
    @ResponseBody
    public int checkPermission(String permission, Integer id) {
        return resourceService.checkPermissionRepeat(permission, id);
    }

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:31:49
     * @description 校验优先级是否重复
     *
     */
    @RequestMapping("checkPriority")
    @ResponseBody
    public int checkPriority(Integer priority, Integer id) {
        return resourceService.checkPriorityRepeat(priority, id);
    }

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:04:06
     * @description 添加资源
     *
     */
    @RequestMapping("add")
    @ResponseBody
    public int add(SecResource resource) {
        logger.info("用户{}查看增加一条资源", ConstantsData.getLoginUserName());
        return resourceService.addResource(resource);
    }

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午4:53:56
     * @description 编辑资源
     *
     */
    @RequestMapping("edit")
    @ResponseBody
    public int edit(SecResource resource) {
        logger.info("用户{}修改一条资源", ConstantsData.getLoginUserName());
        return resourceService.editResource(resource);
    }

    @RequestMapping("findOne")
    @ResponseBody
    public SecResource findOne(Integer id) {
        return resourceService.findById(id);
    }

    @RequestMapping("moveUp")
    @ResponseBody
    public int moveUp(Integer id, Integer parentId, Integer priority) {
        logger.info("用户{}查看上移一条资源", ConstantsData.getLoginUserName());
        return resourceService.moveUp(id, parentId, priority);
    }

    @RequestMapping("moveDown")
    @ResponseBody
    public int moveDown(Integer id, Integer parentId, Integer priority) {
        logger.info("用户{}查看下移一条资源", ConstantsData.getLoginUserName());
        return resourceService.moveDown(id, parentId, priority);
    }

}
