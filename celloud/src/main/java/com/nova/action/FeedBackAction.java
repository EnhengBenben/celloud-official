package com.nova.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.FeedBack;
import com.nova.service.IFeedBackService;

@ParentPackage("celloud-default")
@Action("feedBack")
@Results({
	@Result(name = "getAllFeedBack", location = "/content/feedBackList.jsp"),
	@Result(name = "saveFeedBack", type = "json", params = { "root", "flag" }) })
public class FeedBackAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    @Inject
    private IFeedBackService fbService;
    private Page page;
    private PageList<FeedBack> fbList;
    private FeedBack fb;
    private boolean flag;

    /**
     * 获取所有反馈列表
     * 
     * @return
     */
    public String getAllFeedBack() {
	fbList = fbService.selectAllFeedBack(page);
	return "getAllFeedBack";
    }

    /**
     * 保存反馈信息
     * 
     * @return
     */
    public String saveFeedBack() {
	flag = fbService.saveFeedBack(fb);
	return "saveFeedBack";
    }

    public Page getPage() {
	return page;
    }

    public void setPage(Page page) {
	this.page = page;
    }

    public PageList<FeedBack> getFbList() {
	return fbList;
    }

    public void setFbList(PageList<FeedBack> fbList) {
	this.fbList = fbList;
    }

    public FeedBack getFb() {
	return fb;
    }

    public void setFb(FeedBack fb) {
	this.fb = fb;
    }

    public boolean isFlag() {
	return flag;
    }

    public void setFlag(boolean flag) {
	this.flag = flag;
    }

}
