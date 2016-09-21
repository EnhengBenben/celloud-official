package com.celloud.backstage.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.constants.IconConstants;
import com.celloud.backstage.model.Company;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.AppService;
import com.celloud.backstage.service.CompanyService;
import com.celloud.backstage.utils.CityUtils;
import com.celloud.backstage.utils.PropertiesUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 *
 * @author han
 * @date 2016年1月25日 下午1:54:27
 */
@Controller
@RequestMapping("company")
public class CompanyAction {
    Logger logger = LoggerFactory.getLogger(CompanyAction.class);
    @Resource
    private CompanyService companyService;
    @Resource
    private AppService appService;

    @RequestMapping("companyMain")
    public ModelAndView getCompanyByPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam("keyword") String keyword) {
        ModelAndView mv = new ModelAndView("company/company_main");
        Page page = new Page(currentPage, size);
        PageList<Company> pageList = companyService.getCompanyByPage(page,
                keyword != null ? keyword.trim() : keyword);
        mv.addObject("pageList", pageList);
        mv.addObject("keyword", keyword);
        return mv;
    }

    @RequestMapping("companyEdit")
    public ModelAndView toCompanyEdit(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("company/company_edit");
        String companyId = request.getParameter("companyId");
        if (StringUtils.isNotBlank(companyId)) {
            Company company = companyService
                    .getCompanyById(Integer.parseInt(companyId));
            mv.addObject("company", company);
            if (company != null
                    && StringUtils.isNotBlank(company.getProvince())) {
                mv.addObject("citys", getCityByProvince(company.getProvince()));
            }
        }
        mv.addObject("provinces", getCityByProvince(null));
        return mv;
    }

    @RequestMapping("getcity")
    @ResponseBody
    public List<String> getCityByParent(@RequestParam("pCity") String pCity) {
        return getCityByProvince(pCity);
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") CommonsMultipartFile file,
            HttpSession session) {
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
		File targetFile = new File(IconConstants.getTempPath(),
                new ObjectId().toString() + type);
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
    public ResponseEntity<byte[]> companyIconTemp(String file)
            throws IOException {
		String path = IconConstants.getTempPath(file);
        File targetFile = new File(path);
        logger.info("医院logo临时目录的绝对路径{}", targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(
                FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
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
		String path = IconConstants.getCompanyPath(file);
        File targetFile = new File(path);
        logger.info("医院logo绝对路径{}", targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(
                FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }

    @RequestMapping("companyDelete")
    @ResponseBody
    public int deleteCompany(@RequestParam("companyId") int companyId) {
        return companyService.deleteCompany(companyId);
    }

    @RequestMapping("save")
    @ResponseBody
    public int saveCompany(Company company) {
        if (company.getCompanyId() != null) {
            return companyService.updateCompany(company);
        } else {
            return companyService.addCompany(company);
        }

    }

    @RequestMapping("toUploadPdf")
    public ModelAndView toUploadPdf() {
        ModelAndView mv = new ModelAndView("company/company_upload");
        mv.addObject("cList", companyService.getAllCompany());
        mv.addObject("appList", appService.getAllApp());
        return mv;
    }

    @RequestMapping(value = "uploadPdf", method = RequestMethod.POST)
    @ResponseBody
    public String uploadPdf(@RequestParam("file") CommonsMultipartFile file,
            String name, String newName, Integer chunk, Integer chunks,
            IdsList ids, HttpServletRequest request) {
		String tmpPath = PropertiesUtil.reportTemplatePath + "tmpPath";
        File f = new File(tmpPath);
        if (!f.exists()) {
            boolean isTrue = f.mkdir();
            if (!isTrue) {
                logger.error("路径创建失败：{}", tmpPath);
            }
        }
        String fileName = tmpPath + File.separatorChar + name;
        File localFile = new File(fileName);
        this.copy(file, localFile);
        if (chunk.equals(chunks) || chunk.equals(chunks - 1)) {
            if (ids.getIds() != null) {
                for (Integer cid : ids.getIds()) {
					String realPath = PropertiesUtil.reportTemplatePath + cid;
                    File f1 = new File(realPath);
                    if (!f1.exists()) {
                        boolean isTrue = f1.mkdir();
                        if (!isTrue) {
                            logger.error("路径创建失败：{}", realPath);
                        }
                    }
                    fileName = realPath + File.separatorChar + name;
                    File realFile = new File(fileName);
                    try {
                        FileCopyUtils.copy(localFile, realFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "1";
        }
        return "0";
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
            if (pCity.equals("北京") || pCity.equals("上海") || pCity.equals("天津")
                    || pCity.equals("重庆")) {
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

    private void copy(MultipartFile file, File dst) {
        InputStream in = null;
        OutputStream out = null;
        try {
            if (dst.exists()) {
                out = new BufferedOutputStream(new FileOutputStream(dst, true),
                        2048);
                in = new BufferedInputStream(file.getInputStream(), 2048);
                byte[] buffer = new byte[2048];
                int len = 0;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.flush();
            } else {
                file.transferTo(dst);
            }
        } catch (Exception e) {
            logger.error("将文件写入磁盘出错！", e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("上传文件关闭输入流异常！", e);
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("上传文件关闭输出流异常！", e);
                }
            }
        }
    }
}

class IdsList {
    List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}