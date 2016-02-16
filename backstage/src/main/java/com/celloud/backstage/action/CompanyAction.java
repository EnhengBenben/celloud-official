package com.celloud.backstage.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

import com.celloud.backstage.constants.CompanyConstants;
import com.celloud.backstage.model.Company;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.CompanyService;
import com.celloud.backstage.utils.CityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 *
 * @author han
 * @date 2016年1月25日 下午1:54:27
 */
@Controller
public class CompanyAction {
    Logger logger=LoggerFactory.getLogger(CompanyAction.class);
    @Resource
    private CompanyService companyService;
    @RequestMapping("company/companyMain")
    public ModelAndView getCompanyByPage(@RequestParam(defaultValue = "1") int currentPage,
             @RequestParam(defaultValue = "10") int size,@RequestParam("keyword") String keyword){
         ModelAndView mv=new ModelAndView("company/company_main");
         Page page = new Page(currentPage, size);
         PageList<Company> pageList=companyService.getCompanyByPage(page,keyword);
         mv.addObject("pageList",pageList);
         mv.addObject("keyword",keyword);
         return mv;
     }
    
    @RequestMapping("company/companyEdit")
    public ModelAndView toCompanyEdit(HttpServletRequest request){
        ModelAndView mv=new ModelAndView("company/company_edit");
        String companyId=request.getParameter("companyId");
        if(StringUtils.isNotBlank(companyId)){
            Company company=companyService.getCompanyById(Integer.parseInt(companyId));
            mv.addObject("company", company);
            if(company!=null&&StringUtils.isNotBlank(company.getProvince())){
                mv.addObject("citys", getCityByProvince(company.getProvince()));
            }
        }
        mv.addObject("provinces", getCityByProvince(null));
        return mv;
    }
    
    @RequestMapping("company/getcity" )
    @ResponseBody
    public List<String> getCityByParent(@RequestParam("pCity") String pCity) {
        return getCityByProvince(pCity);
    }
    
    @RequestMapping(value = "company/upload", method = RequestMethod.POST)
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
    @RequestMapping(value = "company/icon/temp", method = RequestMethod.GET)
    public ResponseEntity<byte[]> companyIconTemp(String file) throws IOException {
        String path = CompanyConstants.getCompanyIconTempPath() + File.separator + file;
        File targetFile = new File(path);
        logger.info("医院logo临时目录的绝对路径{}",targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
    
    /**
     * 获取已保存的医院logo
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "company/icon", method = RequestMethod.GET)
    public ResponseEntity<byte[]> companyIcon(String file) throws IOException {
        String path = CompanyConstants.getCompanyIconPath() + File.separator + file;
        File targetFile = new File(path);
        logger.info("医院logo临时目录的绝对路径{}",targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
    
    @RequestMapping("company/companyDelete" )
    @ResponseBody
    public int deleteCompany(@RequestParam("companyId") int companyId) {
        return companyService.deleteCompany(companyId);
    }
    
    @RequestMapping("company/save" )
    @ResponseBody
    public int saveCompany(Company company){
        if(company.getCompanyId()!=null){
           return companyService.updateCompany(company);
        }else{
           return companyService.addCompany(company);
        }
        
    }
    
    private List<String> getCityByProvince(String pCity){
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
