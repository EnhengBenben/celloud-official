package com.celloud.manager.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.CompanyConstants;
import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.model.App;
import com.celloud.manager.model.Company;
import com.celloud.manager.model.User;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.CompanyService;
import com.celloud.manager.utils.CityUtils;
import com.celloud.manager.utils.EmailUtils;
import com.celloud.manager.utils.FileTools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 医院统计
 *
 * @author han
 * @date 2016年3月16日 下午3:07:02
 */
@Controller
@RequestMapping("company")
public class CompanyAction {
    Logger logger = LoggerFactory.getLogger(CompanyAction.class);
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
                resultMap.put("adminData", companyService.getBigCustomerUserCountByMon());
            }
            if (UserRole.BIG_CUSTOMER.equals(role)) {// 大客户
                resultMap = companyService.getCompanyGuideData(user.getCompanyId());
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
                dataList = companyService.getCompanyReport(user.getCompanyId());
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
        String path = this.getClass().getResource("").getPath().split("WEB-INF")[0] + "resources/templates/report/"
                + companyId;
        HashSet<String> pdfPathList = FileTools.getFiles(path, ".pdf");
        mv.addObject("pdfPathList", pdfPathList);
        return mv;
    }

    @RequestMapping("printReport.pdf")
    public void reportPdf(HttpServletResponse response, Integer companyId, String pdfName) {
        response.setContentType("application/pdf");
        String path = CompanyConstants.getReportTemplatePath() + File.separator + companyId + File.separator + pdfName;
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
     * 发送新的公司名字
     */
    @RequestMapping("sendName")
    @ResponseBody
    public int sendName(String currentName, String newName, String reason) {
        String[] emails = new String[] { "miaoqi@celloud.cn" };
        String content = "旧名称:" + currentName + "<br/>新名称:" + newName + "<br/>原因:" + reason;
        EmailUtils.sendWithTitle("公司名称有异", content, emails);
        return 1;
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
        String path = CompanyConstants.getCompanyIconPath() + File.separator + file;
        File targetFile = new File(path);
        logger.info("医院logo绝对路径{}", targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);

    }

    @RequestMapping("getAllToSelect")
    @ResponseBody
    public List<Map<String, String>> getAllToSelect() {
        return companyService.getAllToSelect();
    }

    @RequestMapping("companyMain")
    public ModelAndView getCompanyByPage(@RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size, @RequestParam("keyword") String keyword) {
        ModelAndView mv = new ModelAndView("company/company_main");
        Page page = new Page(currentPage, size);
        PageList<Company> pageList = companyService.getCompanyByPage(page, keyword != null ? keyword.trim() : keyword);
        mv.addObject("pageList", pageList);
        mv.addObject("keyword", keyword);
        return mv;
    }

    @RequestMapping("getcity")
    @ResponseBody
    public List<String> getCityByParent(@RequestParam("pCity") String pCity) {
        return getCityByProvince(pCity);
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") CommonsMultipartFile file, HttpSession session) {
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
        File targetFile = new File(CompanyConstants.getCompanyIconTempPath(), new ObjectId().toString() + type);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("医院logo上传失败：{}", fileName, e);
        }
        return targetFile.getName();
    }

    /**
     * 获取已上传未保存的临时医院logo
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "icon/temp", method = RequestMethod.GET)
    public ResponseEntity<byte[]> companyIconTemp(String file) throws IOException {
        String path = CompanyConstants.getCompanyIconTempPath() + File.separator + file;
        File targetFile = new File(path);
        logger.info("医院logo临时目录的绝对路径{}", targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }

    @RequestMapping("toEditName")
    public ModelAndView toEditName(String currentName) {
        ModelAndView mv = new ModelAndView("company/company_editName");
        mv.addObject("currentName", currentName);
        return mv;
    }

    private List<String> getCityByProvince(String pCity) {
        JSONObject json = JSONObject.fromObject(CityUtils.CITY_CENTER);
        List<String> list = new ArrayList<String>();
        if (StringUtils.isBlank(pCity)) {
            for (Object obj : json.getJSONArray("municipalities")) {
                JSONObject jobj = JSONObject.fromObject(obj);
                list.add(jobj.getString("n"));
            }
            for (Object obj : json.getJSONArray("provinces")) {
                JSONObject jobj = JSONObject.fromObject(obj);
                list.add(jobj.getString("n"));
            }
        } else {
            if (pCity.equals("北京") || pCity.equals("上海") || pCity.equals("天津") || pCity.equals("重庆")) {
                JSONArray muni = json.getJSONArray("municipalities");
                for (Object obj : muni) {
                    JSONObject jobj = JSONObject.fromObject(obj);
                    if (jobj.get("n").equals(pCity)) {
                        list.add(jobj.getString("n"));
                    }
                }
            } else {
                for (Object obj : json.getJSONArray("provinces")) {
                    JSONObject jobj = JSONObject.fromObject(obj);
                    if (jobj.get("n").equals(pCity)) {
                        for (Object c : jobj.getJSONArray("cities")) {
                            JSONObject cobj = JSONObject.fromObject(c);
                            list.add(cobj.getString("n"));
                        }
                    }
                }
            }
        }
        return list;
    }
}
