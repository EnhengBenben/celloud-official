package com.celloud.backstage.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.CompanyConstants;
import com.celloud.backstage.constants.DataState;
import com.celloud.backstage.mapper.CompanyMapper;
import com.celloud.backstage.model.Company;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.CompanyService;

@Service("companyServiceImpl")
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private CompanyMapper companyMapper;

    @Override
    public PageList<Company> getCompanyByPage(Page page,String keyword) {
        List<Company> list=companyMapper.getComanyByPage(DataState.ACTIVE, page,keyword);
        return new PageList<Company>(page,list);
    }

    @Override
    public int updateCompany(Company company) {
        Company c=companyMapper.getCompanyById(company.getCompanyId(),DataState.ACTIVE);
        String deleteIcon=c.getCompanyIcon();
        String companyIcon=company.getCompanyIcon();
        c.setAddress(company.getAddress());
        c.setAddressDetail(company.getAddressDetail());
        c.setAddressEn(company.getAddressEn());
        c.setCity(company.getCity());
        c.setCompanyIcon(company.getCompanyIcon());
        c.setCompanyName(company.getCompanyName());
        c.setEnglishName(company.getEnglishName());
        c.setProvince(company.getProvince());
        c.setTel(company.getTel());
        c.setZipCode(company.getZipCode());
        int result= companyMapper.updateCompany(company);
        if(result>0){
            if(StringUtils.isNotBlank(deleteIcon)&&!deleteIcon.equals(companyIcon)){
                 FileUtils.deleteQuietly(new File(CompanyConstants.getCompanyIconPath(deleteIcon)));
            }
            if(StringUtils.isNotBlank(companyIcon)&&!companyIcon.equals(deleteIcon)){
                try {
                    FileUtils.moveFile(new File(CompanyConstants.getCompanyIconTempPath() + File.separator + companyIcon),
                            new File(CompanyConstants.getCompanyIconPath(companyIcon)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        cleanCompanyIconTemp();
        return result;
    }

    @Override
    public int addCompany(Company company) {
        company.setState(DataState.ACTIVE);
        String companyIcon=company.getCompanyIcon();
        if(StringUtils.isNotBlank(companyIcon)){
            try {
                FileUtils.moveFile(new File(CompanyConstants.getCompanyIconTempPath() + File.separator + companyIcon),
                        new File(CompanyConstants.getCompanyIconPath(companyIcon)));
                cleanCompanyIconTemp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return companyMapper.addCompany(company);
    }

    @Override
    public int deleteCompany(int companyId) {
        Company company=companyMapper.getCompanyById(companyId,DataState.ACTIVE);
        if(company==null){
            return 1;
        }
        int result= companyMapper.updateCompanyState(companyId, DataState.DEELTED);
        String companyIcon=company.getCompanyIcon();
        if(result>0){
            if(StringUtils.isNotBlank(companyIcon)){
                FileUtils.deleteQuietly(new File(CompanyConstants.getCompanyIconPath(companyIcon)));
            }
        }
        return result;
    }

    @Override
    public Company getCompanyById(Integer companyId) {
        if(companyId==null){
            return null;
        }
        return companyMapper.getCompanyById(companyId,DataState.ACTIVE);
    }
    
    private void cleanCompanyIconTemp(){
        String path = CompanyConstants.getCompanyIconTempPath();
        File tempDir = new File(path);
        if (tempDir == null || !tempDir.exists()) {
            return;
        }
        if (tempDir.isFile()) {
            tempDir.delete();
            return;
        }
        File[] tempFiles = tempDir.listFiles();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date date = calendar.getTime();
        for (File file : tempFiles) {
            ObjectId id = new ObjectId(file.getName().substring(0, file.getName().indexOf(".")));
            if (date.after(id.getDate())) {
                file.delete();
            }
        }
    }

    @Override
    public List<Company> getAllCompany() {
        return companyMapper.getComanyList(DataState.ACTIVE);
    }

}
