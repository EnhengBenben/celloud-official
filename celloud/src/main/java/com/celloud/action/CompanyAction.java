package com.celloud.action;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
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
import com.celloud.constants.IconConstants;
import com.celloud.model.mysql.Company;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.CompanyService;
import com.celloud.service.UserService;
import com.celloud.utils.Base64Util;

@Controller
@RequestMapping("company")
public class CompanyAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyAction.class);

    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;

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
        Boolean flag = userService.updateBySelective(updateUser);
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
        LOGGER.info("医院管理员 {} 查询医院信息",ConstantsData.getLoginUserId());
        // 获取医院id
        Integer companyId = ConstantsData.getLoginCompanyId();
        Company company = this.companyService.getCompanyById(companyId);
        if (null == company) {
            LOGGER.info("医院管理员 {} 查询医院信息失败",ConstantsData.getLoginUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        String iconName = company.getCompanyIcon();
        if (StringUtils.isNotEmpty(iconName)) {
            company.setCompanyIcon(Base64Util.imageToStr(IconConstants.getCompanyPath(iconName)));
        }
        LOGGER.info("医院管理员 {} 查询医院信息成功",ConstantsData.getLoginUserId());
        return ResponseEntity.ok(company);
    }
    
    /**
     * 
     * @description 修改医院基本信息
     * @author miaoqi
     * @date 2016年10月31日下午3:35:05
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> updateCompanyInfo(String companyName, String englishName, String province, String city,
            String district, String address, String tel, String zipCode, String companyIcon) {
        LOGGER.info(
                "医院管理员 {} 修改公司信息, companyName = {}, englishName = {}, provice = {}, city = {}, district = {}, address = {}, tel = {}, zipCode = {}",
                ConstantsData.getLoginUserId(), companyName, englishName, province, city, district, address, tel,
                zipCode);
        // 参数不合法
        if (StringUtils.isEmpty(companyName) || StringUtils.isEmpty(englishName)) {
            LOGGER.info("医院管理员 {} 修改公司信息参数为空 companyName = {}, englishName = {}", ConstantsData.getLoginUserId(),
                    companyName, englishName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String filename = null;
        if (StringUtils.isNotEmpty(companyIcon)) {
            filename = Base64Util.strToImage(companyIcon,
                    IconConstants.getCompanyPath(new ObjectId().toString()));
            if (StringUtils.isEmpty(filename)) {
                // 上传图片失败
                LOGGER.info("医院管理员 {} 修改公司信息上传图片失败", ConstantsData.getLoginUserId());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        Company updateCompany = new Company();
        updateCompany.setCompanyId(ConstantsData.getLoginCompanyId());
        updateCompany.setCompanyName(companyName);
        updateCompany.setEnglishName(englishName);
        updateCompany.setProvince(province);
        updateCompany.setCity(city);
        updateCompany.setDistrict(district);
        updateCompany.setAddress(address);
        updateCompany.setTel(tel);
        updateCompany.setZipCode(zipCode);
        updateCompany.setCompanyIcon(filename);
        Boolean flag = companyService.updateByPrimaryKeySelective(updateCompany);
        if (flag) {
            LOGGER.info("医院管理员 {} 修改公司信息成功", ConstantsData.getLoginUserId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        LOGGER.info("医院管理员 {} 修改公司信息失败", ConstantsData.getLoginUserId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
