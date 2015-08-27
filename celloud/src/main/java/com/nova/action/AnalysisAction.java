package com.nova.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.service.IAnalysisService;

/**
 * @Description:
 * @author lin
 * @date 2015-1-5 下午6:31:20
 */
@ParentPackage("json-default")
@Action("analysis")
@Results({
	@Result(name = "userAnalysisList", location = "../../pages/admin/userAnalysisList.jsp"),
	@Result(name = "getUserDataList", location = "../../pages/admin/userCount.jsp"),
	@Result(name = "userMounth", location = "../../pages/admin/userMounth.jsp"),
	@Result(name = "userDataMounth", location = "../../pages/admin/userDataMounth.jsp") })
public class AnalysisAction extends BaseAction {
    Logger log = Logger.getLogger(AnalysisAction.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private IAnalysisService analysisService;
    private Integer userId;
    private String mounth;
    private List<Map<String, String>> list;

    /**
     * 获取companyId
     * 
     * @return
     */
    private Object getCid() {
	Object cid = super.session.get("companyId");
	if (cid == null) {
	    log.error("后台session超时或者非法访问");
	}
	return cid;
    }

    /**
     * 获取嘉宝用户列表
     * 
     * @return
     */
    public String getUserList() {
	Object cid = getCid();
	if (cid != null) {
	    list = analysisService.getUserList((Integer) cid);
	}
	return "userAnalysisList";
    }

    /**
     * 所有用户每个月上传的数据总个数
     * 
     * @return
     */
    public String getUserDataList() {
	Object cid = getCid();
	if (cid != null) {
	    list = analysisService.getUserDataList((Integer) cid);
	}
	return "getUserDataList";
    }

    /**
     * 某个用户每个月的数据个数
     * 
     * @return
     */
    public String getUserData() {
	Object cid = getCid();
	if (cid != null) {
	    list = analysisService.getUserData(userId, (Integer) cid);
	}
	return "userMounth";
    }

    /**
     * 获取某个用户某个月的数据列表
     * 
     * @return
     */
    public String getDataList() {
	Object cid = getCid();
	if (cid != null) {
	    list = analysisService.getDataList(userId, mounth, (Integer) cid);
	}
	return "userDataMounth";
    }

    public Integer getUserId() {
	return userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    public String getMounth() {
	return mounth;
    }

    public void setMounth(String mounth) {
	this.mounth = mounth;
    }

    public List<Map<String, String>> getList() {
	return list;
    }

    public void setList(List<Map<String, String>> list) {
	this.list = list;
    }

}