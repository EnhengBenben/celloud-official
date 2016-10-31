package com.celloud.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.Company;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.CompanyService;

@Controller
@RequestMapping("company")
public class CompanyAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyAction.class);

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
        Integer loginUserId = ConstantsData.getLoginUserId();
        LOGGER.info("医院管理员 {} 查看医院账号管理, 所属公司 {} ", ConstantsData.getLoginUserId(), ConstantsData.getLoginCompanyId());
        PageList<User> pageList = companyService.pageQueryUser(loginUserId, companyId, page);
        return pageList;
    }
    
    /**
     * 
     * @description 修改用户状态
     * @author miaoqi
     * @date 2016年10月27日下午4:57:40
     *
     * @param userId
     * @param state
     * @return
     */
    @RequestMapping("/updateUserState")
    public ResponseEntity<Void> updateUserState(Integer userId, Integer state) {
        LOGGER.info("医院管理员 {} 修改用户 {} 的状态为 {}", ConstantsData.getLoginUserId(), userId, state);
        User updateUser = new User();
        updateUser.setUserId(userId);
        updateUser.setState(state == 0 ? 1 : 0);
        Boolean flag = companyService.updateBySelective(updateUser);
        if(flag){
            LOGGER.info("医院管理员修改用户状态成功");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            LOGGER.info("医院管理员修改用户状态失败");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 
     * @description 获取医院基本信息
     * @author miaoqi
     * @date 2016年10月30日下午11:50:28
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Company> getCompanyInfo() {
        // 获取医院id
        Integer companyId = ConstantsData.getLoginCompanyId();
        Company company = this.companyService.getCompanyById(companyId);
        if (null == company) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(company);
    }

}
