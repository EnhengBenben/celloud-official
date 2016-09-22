package com.celloud.backstage.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.DataState;
import com.celloud.backstage.constants.IconConstants;
import com.celloud.backstage.mapper.DeptMapper;
import com.celloud.backstage.model.Dept;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.DeptService;

@Service("deptServiceImpl")
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptMapper deptMapper;

    @Override
    public int updateDept(Dept dept) {
        Dept d=deptMapper.getDeptById(dept.getDeptId(),DataState.ACTIVE);
        String deleteIcon=d.getDeptIcon();
        String deptIcon=dept.getDeptIcon();
        d.setDeptIcon(dept.getDeptIcon());
        d.setDeptName(dept.getDeptName());
        d.setEnglishName(dept.getEnglishName());
        d.setTel(dept.getTel());
        int result= deptMapper.updateDept(dept);
        if(result>0){
            if(StringUtils.isNotBlank(deleteIcon)&&!deleteIcon.equals(deptIcon)){
				FileUtils.deleteQuietly(new File(IconConstants.getDeptPath(deleteIcon)));
            }
            if(StringUtils.isNotBlank(deptIcon)&&!deptIcon.equals(deleteIcon)){
                try {
					FileUtils.moveFile(new File(IconConstants.getTempPath() + File.separator + deptIcon),
							new File(IconConstants.getDeptPath(deptIcon)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		IconConstants.cleanTemp();
        return result;
    }

    @Override
    public int addDept(Dept dept) {
        dept.setState(DataState.ACTIVE);
        String deptIcon=dept.getDeptIcon();
        if(StringUtils.isNotBlank(deptIcon)){
            try {
				FileUtils.moveFile(new File(IconConstants.getTempPath() + File.separator + deptIcon),
						new File(IconConstants.getDeptPath(deptIcon)));
				IconConstants.cleanTemp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return deptMapper.addDept(dept);
    }

    @Override
    public int deleteDept(int deptId) {
        Dept dept=deptMapper.getDeptById(deptId,DataState.ACTIVE);
        if(dept==null){
            return 1;
        }
        int result= deptMapper.updateDeptState(deptId, DataState.DEELTED);
        String deptIcon=dept.getDeptIcon();
        if(result>0){
            if(StringUtils.isNotBlank(deptIcon)){
				FileUtils.deleteQuietly(new File(IconConstants.getDeptPath(deptIcon)));
            }
        }
        return result;
    }

    @Override
    public Dept getDeptById(Integer deptId) {
        if(deptId==null){
            return null;
        }
        return deptMapper.getDeptById(deptId,DataState.ACTIVE);
    }

    @Override
    public PageList<Dept> getDeptByPage(int companyId, Page page) {
        List<Dept> list=deptMapper.getDeptByPage(DataState.ACTIVE,companyId, page);
        return new PageList<Dept>(page,list);
    }


    @Override
    public List<Dept> getDeptByCompanyId(int companyId) {
        return deptMapper.getDeptByCompanyId(DataState.ACTIVE, companyId);
    }

}
