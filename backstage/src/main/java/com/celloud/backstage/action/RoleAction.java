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
import com.celloud.backstage.model.SecRole;
import com.celloud.backstage.model.User;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.SecRoleService;
import com.celloud.backstage.service.UserService;

/** 
 * @author MQ: 
 * @date 2016年7月19日 下午3:02:18 
 * @description 
 */
@Controller
@RequestMapping("role")
public class RoleAction {

    private Logger logger = LoggerFactory.getLogger(RoleAction.class);
    @Resource
    private SecRoleService roleService;
    @Resource
    private UserService userService;

    @RequestMapping("pageQuery")
    public ModelAndView pageQuery(Page page) {
        logger.info("用户{}查看角色列表", ConstantsData.getLoginUserName());
        ModelAndView mv = new ModelAndView("permission/role_main");
        PageList<SecRole> pageList = roleService.pageQuery(page);
        List<User> bigCustomers = userService.getBigCustomers();
        mv.addObject("pageList", pageList);
        mv.addObject("bigCustomers", bigCustomers);
        return mv;
    }

    @RequestMapping("add")
    @ResponseBody
    public int add(SecRole role) {
        logger.info("用户{}增加一个角色", ConstantsData.getLoginUserName());
        return roleService.addRole(role);
    }

    @RequestMapping("checkName")
    @ResponseBody
    public int checkName(String name, Integer id) {
        return roleService.checkNameRepeat(name, id);
    }

    @RequestMapping("checkCode")
    @ResponseBody
    public int checkCode(String code, Integer id) {
        return roleService.checkCodeRepeat(code, id);
    }

    @RequestMapping("findOne")
    @ResponseBody
    public SecRole findOne(Integer id) {
        return roleService.findById(id);
    }

    @RequestMapping("edit")
    @ResponseBody
    public int edit(SecRole role) {
        logger.info("用户{}修改了一条角色", ConstantsData.getLoginUserName());
        return roleService.editRole(role);
    }

    @RequestMapping("getBigCustomersByRole")
    @ResponseBody
    public List<User> getBigCustomersByRole(Integer roleId) {
        return roleService.getBigCustomersByRole(roleId);
    }

    @RequestMapping("distribute")
    @ResponseBody
    public int distribute(Integer roleId, Integer[] bigCustomerIds) {
        logger.info("用户{}为大客户分配了角色", ConstantsData.getLoginUserName());
        return roleService.distribute(roleId, bigCustomerIds);
    }

}
