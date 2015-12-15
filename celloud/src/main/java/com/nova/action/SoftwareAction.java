package com.nova.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.inject.Inject;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Company;
import com.nova.sdo.ParamStrain;
import com.nova.sdo.Project;
import com.nova.sdo.Screen;
import com.nova.sdo.Software;
import com.nova.sdo.SoftwareComment;
import com.nova.service.ICompanyService;
import com.nova.service.IScreenService;
import com.nova.service.ISoftwareCommentService;
import com.nova.service.ISoftwareService;

/**
 * @类名称：SoftwareNewAction  
 * @类描述：软件action  
 * @创建人：zl  
 * @创建时间：2013-7-29 下午2:08:03    
 * @version 1.0     
 */
public class SoftwareAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(SoftwareAction.class);
    @Inject
    private ICompanyService cs;
    @Inject
    private ISoftwareService softwareService;
    @Inject
    private IScreenService screenService;
    @Inject
    private ISoftwareCommentService commentService;
    private List<Software> list;
    private PageList<Software> pageList;
    private List<Screen> screenList;
    private Software soft;
    private int softNum;
    private int userId;
    private int dataNum;

    // 分页
    private Page page;
    private int currentPage = 1;
    private int pageSize = 8;
    // 软件评论页面大小
    private int commentPageSize = 1;
    private boolean result;
    private Integer deskNo;
    private Project project;

    private String softId;
    private String editType;
    private List<Company> companyList;

    /**
     * appInfo 页面获取所有软件信息列表
     * 
     * @author lin
     * @return
     */
    public String getAppInfo() {
	list = softwareService.getAllSofts();
	return SUCCESS;
    }

    /**
     * 根据软件编号获取数据格式
     * 
     * @return
     */
    public String getAppFormats() {
	editType = softwareService.getFormatsByAppId(soft.getSoftwareId());
	return SUCCESS;
    }

    /**
     * 软件下线
     * 
     * @return
     */
    public String offLineSoft() {
	dataNum = softwareService.offLineSoftware(soft.getSoftwareId(),
		soft.getOffLine());
	return SUCCESS;
    }

    /**
     * 跳转到添加app页面
     * 
     * @return
     */
    public String goToAddSoftPage() {
	companyList = cs.getAllCompany();
	return "goToAddSoftPage";
    }

    /**
     * 根据软件编号获取物种列表
     * 
     * @return
     */
    private List<ParamStrain> strainList;

    public String getStrainListBySoftwareId() {
	strainList = softwareService.getStrainListBySoftwareId(String
		.valueOf(softwareId));
	return SUCCESS;
    }

    /**
     * 根据软件编号获取运行软件至少需要的数据条数
     * 
     * @return
     */
    public String getAppRunDataNum() {
	dataNum = softwareService.getAppRunDataNum(softwareId);
	return SUCCESS;
    }

    /**
     * 根据文件格式获取软件列表
     * 
     * @return
     */
    private int fileFormat;

    /**
     * 根据软件编号获取软件名称
     * 
     * @return
     */
    public String getSoftwareNameById() {
	Software software = softwareService.getSoftware(softwareId);
	softwareName = software.getSoftwareName();
	return SUCCESS;
    }

    /**
     * 根据软件名称获取软件编号
     * 
     * @return
     */
    private String softwareName;

    public String getSoftwareIdByName() {
	softwareId = softwareService.getSoftIdByName(softwareName);
	return SUCCESS;
    }

    public String getSoftIdByNameOffLine() {
	softwareId = softwareService.getSoftIdByNameOffLine(softwareId,
		softwareName, editType);
	return SUCCESS;
    }

    private String email;

    /**
     * 根据软件编号获取管理员邮箱
     * 
     * @return
     */
    public String getEmailBySoftwareId() {
	email = softwareService.getEmailBySoftId(softwareId);
	return SUCCESS;
    }

    /**
     * @Title: getSoftwareList
     * @Description: (根据软件类型和用户获取用户添加列表)
     * @return
     * @param String
     * @throws
     */
    public String getSoftwareList() {
	userId = (Integer) session.get("userId");
	pageList = softwareService.getSoftwarePageList(userId,
		soft.getClassifyId(), page);
	return SUCCESS;
    }

    /**
     * 获取软件详细信息
     * 
     * @return
     */
    private List<SoftwareComment> commentList;

    /**
     * 修改人气指数
     */
    public String updateBhri() {
	userId = (Integer) session.get("userId");
	result = softwareService.updateBhri(soft.getSoftwareId(), userId,
		deskNo);
	return SUCCESS;
    }

    public int getSoftNum() {
	return softNum;
    }

    public void setSoftNum(int softNum) {
	this.softNum = softNum;
    }

    public List<Software> getList() {
	return list;
    }

    public void setList(List<Software> list) {
	this.list = list;
    }

    public Software getSoft() {
	return soft;
    }

    public void setSoft(Software soft) {
	this.soft = soft;
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public Page getPage() {
	return page;
    }

    public void setPage(Page page) {
	this.page = page;
    }

    public int getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
    }

    public int getPageSize() {
	return pageSize;
    }

    public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
    }

    public boolean isResult() {
	return result;
    }

    public void setResult(boolean result) {
	this.result = result;
    }

    public List<Screen> getScreenList() {
	return screenList;
    }

    public void setScreenList(List<Screen> screenList) {
	this.screenList = screenList;
    }

    /***************************************************************************
	 * 
	 */
    /**
     * @Title:SoftwareAction.java
     * @Description: 根据type取数据库（软件）列表
     * @author lin
     * @date 2012-7-3 下午04:44:03
     */
    private int classifyId;

    public String getSoftByType() {
	userId = (Integer) super.session.get("userId");
	list = softwareService.getAllDb(classifyId, userId, soft.getType());
	return SUCCESS;
    }

    /**
     * @Title: getOneSoftwareXML
     * @Description: (获取一个软件信息并封装成xml)
     * @return
     * @param String
     * @throws
     */
    private String xml;
    private int softwareId;

    public String getOneSoftwareXML() {
	log.info("获取一个软件的详细信息XML封装");
	this.xml = softwareService.getOneSoftwareXML(softwareId);
	return SUCCESS;
    }

    public String getOneSoftwareById() {
	soft = softwareService.getSoftware(softwareId);
	return "getOneSoftwareById";
    }

    /**
     * @Title: addSoftwareToDeskTop
     * @Description: (添加软件到桌面上)
     * @return
     * @param String
     * @throws
     */
    private String isAdd;

    public String addSoftwareToDeskTop() {
	HttpServletRequest request = ServletActionContext.getRequest();
	Object obj = request.getSession().getAttribute("deskNo");
	userId = (Integer) super.session.get("userId");
	if (obj == null) {
	    obj = 0;
	}
	String desk = obj.toString();
	int deskNo = Integer.parseInt(desk);
	// 桌面添加软件
	softwareService.saveSoftwareOnDesk(userId, softwareId, deskNo + 1);
	// 添加后更新用户人气指数
	softwareService.updateBhri(softwareId);
	// 添加软件成功，标志isAdd为 yes
	this.isAdd = "yes";
	return SUCCESS;
    }

    /**
     * 分页查询软件
     * 
     * @return
     */
    public String getPageListSoft() {
	list = softwareService.getSoftPageList(page);
	page = new Page(page.getPageSize(), page.getCurrentPage());
	int rowCount = softwareService.getTotalSize();
	page.make(rowCount);
	return "getPageListSoftSuc";
    }

    /**
     * 打开编辑页面
     * 
     * @return
     */
    public String toEditSoft() {
	soft = softwareService.getSoftware(soft.getSoftwareId());
	return "toEditSoft";
    }

    /**
     * 打开软件详细信息页面
     * 
     * @return
     */
    public String toDetailSoft() {
	soft = softwareService.getSoftware(soft.getSoftwareId());
	// 获取软件截图列表
	screenList = screenService.getAllScreen(soft.getSoftwareId());
	return "toDetailSoft";
    }

    /**
     * 获取所有的软件列表
     * 
     * @return
     */
    public String getAllSoft() {
	list = softwareService.getAllSofts();
	return "getAllSoft";
    }

    /**
     * 获取用户应用运行的次数
     * 
     * @return
     */
    private String softRunNum;

    public String getSoftwareRunNum() {
	userId = (Integer) super.session.get("userId");
	int totalSoftRunNum = softwareService.getRunningSoftwareNum(userId);
	list = softwareService.getUsersSoftwareRunNum(userId);
	String firstSoftName = "";
	String secondSoftName = "";
	String thirdSoftName = "";
	String forthSoftName = "";
	int firstSoftRunNum = 0;
	int secondSoftRunNum = 0;
	int thirdSoftRunNum = 0;
	int forthSoftRunNum = 0;
	if (list == null) {
	    softRunNum = totalSoftRunNum + "";
	} else if (list.size() == 1) {
	    firstSoftName = list.get(0).getSoftwareName();
	    firstSoftRunNum = list.get(0).getRunNum();
	    softRunNum = totalSoftRunNum + "," + firstSoftName + ","
		    + firstSoftRunNum;
	} else if (list.size() == 2) {
	    firstSoftName = list.get(0).getSoftwareName();
	    firstSoftRunNum = list.get(0).getRunNum();
	    secondSoftName = list.get(1).getSoftwareName();
	    secondSoftRunNum = list.get(1).getRunNum();
	    softRunNum = totalSoftRunNum + "," + firstSoftName + ","
		    + firstSoftRunNum + "," + secondSoftName + ","
		    + secondSoftRunNum;
	} else if (list.size() == 3) {
	    firstSoftName = list.get(0).getSoftwareName();
	    firstSoftRunNum = list.get(0).getRunNum();
	    secondSoftName = list.get(1).getSoftwareName();
	    secondSoftRunNum = list.get(1).getRunNum();
	    thirdSoftName = list.get(2).getSoftwareName();
	    thirdSoftRunNum = list.get(2).getRunNum();
	    softRunNum = totalSoftRunNum + "," + firstSoftName + ","
		    + firstSoftRunNum + "," + secondSoftName + ","
		    + secondSoftRunNum + "," + thirdSoftName + ","
		    + thirdSoftRunNum;
	} else if (list.size() == 4) {
	    firstSoftName = list.get(0).getSoftwareName();
	    firstSoftRunNum = list.get(0).getRunNum();
	    secondSoftName = list.get(1).getSoftwareName();
	    secondSoftRunNum = list.get(1).getRunNum();
	    thirdSoftName = list.get(2).getSoftwareName();
	    thirdSoftRunNum = list.get(2).getRunNum();
	    forthSoftName = list.get(3).getSoftwareName();
	    forthSoftRunNum = list.get(3).getRunNum();
	    softRunNum = totalSoftRunNum + "," + firstSoftName + ","
		    + firstSoftRunNum + "," + secondSoftName + ","
		    + secondSoftRunNum + "," + thirdSoftName + ","
		    + thirdSoftRunNum + "," + forthSoftName + ","
		    + forthSoftRunNum;
	}
	return "softwareRunNum";
    }

    /**
     * 获取软件运行统计信息
     * 
     * @return
     */
    private List<Software> softwareRunList;

    public String getSoftRunStatistic() {
	softwareRunList = softwareService.getSoftwaresRunNum();
	return "getSoftStatistic";
    }

    /**
     * 获取不属于某项目且符合该项目要求的APP
     * 
     * @return
     */
    private List<Software> softwareNotInPro;
    private int projectId;

    public String getAllSoftwareNotInPro() {
	softwareNotInPro = softwareService.getAllSoftwareNotInPro(project);
	return "getAllSoftwareNotInPro";
    }

    /**
     * 获取不属于某项目且符合该数据要求的APP
     * 
     * @return
     */
    private List<Software> softwareNotInData;
    private int dataId;

    public String getAllSoftwareNotInDate() {
	softwareNotInData = softwareService.getAllSoftwareNotInData(dataId);
	return "getAllSoftwareNotInDate";
    }

    /**
     * 新增软件
     * 
     * @return
     */
    private int changeNum;
    private String[] dataTypes;

    public String createSoftware() {
	if (editType.equals("0")) {// 0代表新增，1代表修改
	    changeNum = softwareService.insertSoftware(soft);
	} else {
	    soft.setSoftwareId(Integer.parseInt(softId));
	    changeNum = softwareService.updateSoftware(soft);
	}
	return "createSoftware";
    }

    /**
     * 修改软件
     * 
     * @return
     */
    public String editSoft() {
	int classifyId = soft.getClassifyId();
	if (classifyId == 0) {
	    soft.setClassify("全部应用");
	} else if (classifyId == 1) {
	    soft.setClassify("引物设计");
	} else if (classifyId == 2) {
	    soft.setClassify("序列处理");
	} else if (classifyId == 3) {
	    soft.setClassify("对比和画树");
	} else if (classifyId == 4) {
	    soft.setClassify("数据统计");
	} else if (classifyId == 5) {
	    soft.setClassify("图形展示");
	} else if (classifyId == 6) {
	    soft.setClassify("热点应用");
	} else if (classifyId == 100) {
	    soft.setClassify("数据库系统");
	}
	changeNum = softwareService.updateSoftware(soft);
	return "editSoft";
    }

    /**
     * 删除软件
     * 
     * @return
     */
    public String deleteSoftWare() {
	changeNum = softwareService.deleteSoftware(soft.getSoftwareId());
	return "deleteSoftWare";
    }

    /**
     * 根据软件名称获取软件编号
     * 
     * @return
     */
    public String getSoftIdByName() {
	softwareId = softwareService.getSoftIdByName(soft.getSoftwareName());
	return "getSoftIdByName";
    }

    /**
     * 软件推荐
     * 
     * @return
     */
    public String softwareRecommend() {
	result = softwareService.recommendSoftware(soft.getSoftwareId());
	return SUCCESS;
    }

    /**
     * 软件取消推荐
     * 
     * @return
     */
    public String softwareNoRecommend() {
	result = softwareService.recommendNoSoftware(soft.getSoftwareId());
	return SUCCESS;
    }

    public String getSoftwareName() {
	return softwareName;
    }

    public void setSoftwareName(String softwareName) {
	this.softwareName = softwareName;
    }

    public String[] getDataTypes() {
	return dataTypes;
    }

    public void setDataTypes(String[] dataTypes) {
	this.dataTypes = dataTypes;
    }

    public int getChangeNum() {
	return changeNum;
    }

    public void setChangeNum(int changeNum) {
	this.changeNum = changeNum;
    }

    public List<Software> getSoftwareNotInData() {
	return softwareNotInData;
    }

    public void setSoftwareNotInData(List<Software> softwareNotInData) {
	this.softwareNotInData = softwareNotInData;
    }

    public int getDataId() {
	return dataId;
    }

    public void setDataId(int dataId) {
	this.dataId = dataId;
    }

    public List<Software> getSoftwareNotInPro() {
	return softwareNotInPro;
    }

    public void setSoftwareNotInPro(List<Software> softwareNotInPro) {
	this.softwareNotInPro = softwareNotInPro;
    }

    public int getProjectId() {
	return projectId;
    }

    public void setProjectId(int projectId) {
	this.projectId = projectId;
    }

    public List<Software> getSoftwareRunList() {
	return softwareRunList;
    }

    public void setSoftwareRunList(List<Software> softwareRunList) {
	this.softwareRunList = softwareRunList;
    }

    public String getSoftRunNum() {
	return softRunNum;
    }

    public void setSoftRunNum(String softRunNum) {
	this.softRunNum = softRunNum;
    }

    public String getIsAdd() {
	return isAdd;
    }

    public void setIsAdd(String isAdd) {
	this.isAdd = isAdd;
    }

    public int getSoftwareId() {
	return softwareId;
    }

    public void setSoftwareId(int softwareId) {
	this.softwareId = softwareId;
    }

    public String getXml() {
	return xml;
    }

    public void setXml(String xml) {
	this.xml = xml;
    }

    public int getClassifyId() {
	return classifyId;
    }

    public void setClassifyId(int classifyId) {
	this.classifyId = classifyId;
    }

    public Integer getDeskNo() {
	return deskNo;
    }

    public void setDeskNo(Integer deskNo) {
	this.deskNo = deskNo;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public List<SoftwareComment> getCommentList() {
	return commentList;
    }

    public void setCommentList(List<SoftwareComment> commentList) {
	this.commentList = commentList;
    }

    public int getCommentPageSize() {
	return commentPageSize;
    }

    public void setCommentPageSize(int commentPageSize) {
	this.commentPageSize = commentPageSize;
    }

    public Project getProject() {
	return project;
    }

    public void setProject(Project project) {
	this.project = project;
    }

    public int getFileFormat() {
	return fileFormat;
    }

    public void setFileFormat(int fileFormat) {
	this.fileFormat = fileFormat;
    }

    public int getDataNum() {
	return dataNum;
    }

    public void setDataNum(int dataNum) {
	this.dataNum = dataNum;
    }

    public List<ParamStrain> getStrainList() {
	return strainList;
    }

    public void setStrainList(List<ParamStrain> strainList) {
	this.strainList = strainList;
    }

    public String getSoftId() {
	return softId;
    }

    public void setSoftId(String softId) {
	this.softId = softId;
    }

    public String getEditType() {
	return editType;
    }

    public void setEditType(String editType) {
	this.editType = editType;
    }

    public PageList<Software> getPageList() {
	return pageList;
    }

    public void setPageList(PageList<Software> pageList) {
	this.pageList = pageList;
    }

    public List<Company> getCompanyList() {
	return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
	this.companyList = companyList;
    }

}
