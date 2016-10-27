package com.celloud.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.CompanyService;

@Controller
@RequestMapping("company")
public class CompanyAction {

    @Autowired
    private CompanyService companyService;

    /**
     * 
     * @description 根据当前登录用户的companyId分页查询用户
     * @author miaoqi
     * @date 2016年10月27日上午11:03:10
     *
     * @param page
     *            分页对象
     *
     * @return
     */
    @RequestMapping("/userInfo")
    @ResponseBody
    public PageList<User> userInfoByCompanyId(Page page) {
        Integer companyId = ConstantsData.getLoginCompanyId();
        PageList<User> pageList = companyService.pageQueryUser(companyId, page);
        return pageList;
    }
    
}
