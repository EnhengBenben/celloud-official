package com.celloud.backstage.action;

import java.io.File;
import java.io.IOException;

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

import com.celloud.backstage.constants.IconConstants;
import com.celloud.backstage.model.Dept;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.CompanyService;
import com.celloud.backstage.service.DeptService;

/**
 * 
 *
 * @author han
 * @date 2016年1月25日 下午1:54:27
 */
@Controller
public class DeptAction {
    Logger logger=LoggerFactory.getLogger(DeptAction.class);
    @Resource
    private DeptService deptService;
    @Resource
    private CompanyService companyService;
    @RequestMapping("dept/deptList")
    public ModelAndView getDeptByPage(@RequestParam(defaultValue = "1") int currentPage,
             @RequestParam(defaultValue = "10") int size,@RequestParam("companyId") int companyId){
         ModelAndView mv=new ModelAndView("dept/dept_main");
         Page page = new Page(currentPage, size);
         PageList<Dept> pageList=deptService.getDeptByPage(companyId,page);
         mv.addObject("pageList",pageList);
         mv.addObject("company",companyService.getCompanyById(companyId));
         return mv;
     }
    
    @RequestMapping("dept/deptEdit")
    public ModelAndView toDeptEdit(HttpServletRequest request){
        ModelAndView mv=new ModelAndView("dept/dept_edit");
        String deptId=request.getParameter("deptId");
        if(StringUtils.isNotBlank(deptId)){
            Dept dept=deptService.getDeptById(Integer.parseInt(deptId));
            mv.addObject("dept", dept);
        }
        mv.addObject("companyList", companyService.getAllCompany());
        mv.addObject("companyId", request.getParameter("companyId"));
        return mv;
    }
    
    
    @RequestMapping(value = "dept/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") CommonsMultipartFile file, HttpSession session) {
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
		File targetFile = new File(IconConstants.getTempPath(new ObjectId().toString() + type));
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("部门logo上传失败：{}", fileName, e);
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
    @RequestMapping(value = "dept/icon/temp", method = RequestMethod.GET)
    public ResponseEntity<byte[]> deptIconTemp(String file) throws IOException {
		String path = IconConstants.getTempPath(file);
        File targetFile = new File(path);
        logger.info("部门logo临时目录的绝对路径{}",targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
    
    /**
     * 获取已保存的部门logo
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "dept/icon", method = RequestMethod.GET)
    public ResponseEntity<byte[]> deptIcon(String file) throws IOException {
		String path = IconConstants.getDeptPath(file);
        File targetFile = new File(path);
        logger.info("部门logo目录的绝对路径{}",targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
    
    @RequestMapping("dept/deptDelete" )
    @ResponseBody
    public int deleteDept(@RequestParam("deptId") int deptId) {
        return deptService.deleteDept(deptId);
    }
    
    @RequestMapping("dept/save" )
    @ResponseBody
    public int saveDept(Dept dept){
        if(dept.getDeptId()!=null){
           return deptService.updateDept(dept);
        }else{
           return deptService.addDept(dept);
        }
        
    }
}
