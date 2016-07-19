package com.celloud.backstage.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.constants.Constants;
import com.celloud.backstage.constants.NoticeConstants;
import com.celloud.backstage.model.Notice;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.NoticeService;
import com.celloud.backstage.utils.DateUtil;
import com.celloud.message.alimail.AliEmail;
import com.celloud.message.alimail.AliSubstitution;
import com.celloud.message.alimail.EmailParams;
import com.celloud.message.alimail.EmailType;
import com.celloud.message.category.MessageCategoryCode;
import com.celloud.message.category.MessageCategoryUtils;
import com.celloud.message.web.MessageUtils;

/**
 * 
 *
 * @author han
 * @date 2016年2月16日 下午2:20:51
 */
@Controller
public class NoticeAction {
    Logger logger=LoggerFactory.getLogger(NoticeAction.class);
    @Resource
    private NoticeService noticeService;
	@Resource
	private MessageCategoryUtils mcu;
	
    @RequestMapping("notice/noticeList")
    public ModelAndView getNoticeByPage(@RequestParam(defaultValue = "1") int currentPage,
             @RequestParam(defaultValue = "10") int size){
         ModelAndView mv=new ModelAndView("notice/notice_main");
         Page page = new Page(currentPage, size);
         PageList<Notice> pageList=noticeService.getNoticeByPage(page);
         mv.addObject("pageList",pageList);
         return mv;
     }
    
    @RequestMapping("notice/noticeEdit")
    public ModelAndView toNoticeEdit(HttpServletRequest request){
        ModelAndView mv=new ModelAndView("notice/notice_edit");
        String noticeId=request.getParameter("noticeId");
        if(StringUtils.isNotBlank(noticeId)){
            Notice notice=noticeService.getNoticeById(Integer.parseInt(noticeId));
            mv.addObject("notice", notice);
        }
        return mv;
    }
    
    @RequestMapping("notice/save" )
    @ResponseBody
    public int saveNotice(Notice notice){
        if(notice.getNoticeId()!=null){
           return noticeService.updateNotice(notice);
        }else{
			noticeService.insertMessage(notice, null);
			//构造桌面消息
			MessageUtils mu = MessageUtils.get().on(Constants.MESSAGE_USER_CHANNEL).send(
					NoticeConstants.createMessage("notice", "系统公告", "您收到一份系统公告【" + notice.getNoticeTitle() + "】。"),
					false);
			//构造邮件内容
			AliEmail aliEmail = AliEmail.template(EmailType.NOTICE).substitutionVars(
					AliSubstitution.sub().set(EmailParams.NOTICE.title.name(), notice.getNoticeTitle())
							.set(EmailParams.NOTICE.context.name(), notice.getNoticeContext())
							.set(EmailParams.NOTICE.end.name(), DateUtil.getDateToString("yyyy")));
			//构造微信消息
			//				Param params = ParamFormat.param()
			//						.set(WechatParams.RUN_OVER.first.name(), "您好，您的数据" + tipsName + " 运行结束", "#222222")
			//						.set(WechatParams.RUN_OVER.keyword1.name(), appName, null)
			//						.set(WechatParams.RUN_OVER.keyword2.name(), startDate, null)
			//						.set(WechatParams.RUN_OVER.keyword3.name(), endDate, "#222222");
			mcu.sendMessage(MessageCategoryCode.NOTICE, aliEmail, null, mu);
			return 1;
        }
        
    }
}
