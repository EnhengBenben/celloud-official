package com.celloud.manager.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.CompanyConstants;
import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.model.App;
import com.celloud.manager.model.Company;
import com.celloud.manager.model.User;
import com.celloud.manager.service.CompanyService;
import com.celloud.manager.utils.FileTools;

/**
 * 医院统计
 *
 * @author han
 * @date 2016年3月16日 下午3:07:02
 */
@Controller
@RequestMapping("company")
public class CompanyAction {
    @Resource
    private CompanyService companyService;

    @RequestMapping("guide")
    public ModelAndView companyGuideCount() {
        ModelAndView mv = new ModelAndView("company/company_guide");
        User user = ConstantsData.getLoginUser();
        Map<String, Object> map = null;
        if (user != null) {
            Integer role = user.getRole();
            if (UserRole.ADMINISTRATOR.equals(role)) {// 超级管理员
                map = companyService.companyGuideCount(null);
            }
            if (UserRole.BIG_CUSTOMER.equals(role)) {// 大客户
                map = companyService.companyGuideCount(user.getCompanyId());
            }
        }
        mv.addObject("resultMap", map);
        return mv;
    }

    @ResponseBody
    @RequestMapping("guideData")
    public Object getCompanyGuideCount() {
        User user = ConstantsData.getLoginUser();
        Map<String, Object> resultMap = null;
        if (user != null) {
            Integer role = user.getRole();
            if (UserRole.ADMINISTRATOR.equals(role)) {// 超级管理员
                resultMap = companyService.getCompanyGuideData(null);
                resultMap.put("adminData",
                        companyService.getBigCustomerUserCountByMon());
            }
            if (UserRole.BIG_CUSTOMER.equals(role)) {// 大客户
                resultMap = companyService
                        .getCompanyGuideData(user.getCompanyId());
            }
        }
        return resultMap;
    }

    @RequestMapping("baseInfo")
    public ModelAndView companyBaseInfo() {
        ModelAndView mv = new ModelAndView("company/company_baseInfo");
        User user = ConstantsData.getLoginUser();
        List<Company> list = null;
        if (user != null) {
            Integer role = user.getRole();
            if (UserRole.ADMINISTRATOR.equals(role)) {// 超级管理员
                list = companyService.getCompany(null);
            }
            if (UserRole.BIG_CUSTOMER.equals(role)) {// 大客户
                list = companyService.getCompany(user.getCompanyId());
            }
        }
        mv.addObject("companyList", list);
        return mv;
    }

    @RequestMapping("reportCount")
    public ModelAndView companyReportCount() {
        ModelAndView mv = new ModelAndView("company/company_report");
        User user = ConstantsData.getLoginUser();
        List<App> list = null;
        List<Map<String, Object>> dataList = null;
        if (user != null) {
            Integer role = user.getRole();
            if (UserRole.ADMINISTRATOR.equals(role)) {// 超级管理员
                list = companyService.getAppOfBigCustomer(null);
                dataList = companyService.getCompanyReport(null);
            }
            if (UserRole.BIG_CUSTOMER.equals(role)) {// 大客户
                list = companyService.getAppOfBigCustomer(user.getCompanyId());
                dataList = companyService.getCompanyReport(null);
            }
        }
        mv.addObject("appList", list);
        mv.addObject("dataList", dataList);
        return mv;
    }

    @RequestMapping("bigCustomer")
    public ModelAndView companyBigCustomerCount() {
        ModelAndView mv = new ModelAndView("company/company_bigCustomer");
        User user = ConstantsData.getLoginUser();
        List<Map<String, Object>> dataList = null;
        if (user != null) {
            Integer role = user.getRole();
            if (UserRole.ADMINISTRATOR.equals(role)) {// 超级管理员
                dataList = companyService.bigCustomerDataCount();
            }
        }
        mv.addObject("dataList", dataList);
        return mv;
    }

    @ResponseBody
    @RequestMapping("companyNum")
    public Object getCompanyNumCount() {
        User user = ConstantsData.getLoginUser();
        List<Map<String, Object>> resultMap = null;
        if (user != null) {
            Integer role = user.getRole();
            if (UserRole.ADMINISTRATOR.equals(role)) {// 超级管理员
                resultMap = companyService.getCompanyNumCount();
            }
        }
        return resultMap;
    }

    @RequestMapping("getCompanyById")
    public ModelAndView getCompanyDetailById(Integer companyId) {
        ModelAndView mv = new ModelAndView("company/company_detail");
        Company company = companyService.getCompanyById(companyId);
        mv.addObject("company", company);
        // XXX 需修改为流读取PDF文件
        String path = this.getClass().getResource("").getPath()
                .split("WEB-INF")[0] + "resources/templates/report/"
                + companyId;
        HashSet<String> pdfPathList = FileTools.getFiles(path, ".pdf");
        mv.addObject("pdfPathList", pdfPathList);
        return mv;
    }

    @RequestMapping("printReport.pdf")
    public void reportPdf(HttpServletResponse response, Integer companyId,
            String pdfName) {
        response.setContentType("application/pdf");
        String path = CompanyConstants.getReportTemplatePath() + File.separator
                + companyId + File.separator + pdfName;
        FileInputStream in = null;
        ServletOutputStream out = null;
        try {
            in = new FileInputStream(new File(path));
            out = response.getOutputStream();
            byte[] b = new byte[1024 * 1024];
            int length = 0;
            while ((length = in.read(b)) != -1) {
                out.write(b, 0, length);
            }
            in.close();
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取已保存的医院logo
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "icon", method = RequestMethod.GET)
    public ResponseEntity<byte[]> companyIcon(String file) throws IOException {
        String path = CompanyConstants.getCompanyIconPath() + File.separator
                + file;
        File targetFile = new File(path);
        return new ResponseEntity<byte[]>(
                FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
}
