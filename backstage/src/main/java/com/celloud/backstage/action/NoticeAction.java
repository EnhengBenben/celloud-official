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

import com.celloud.backstage.model.Notice;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.NoticeService;

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
           return noticeService.addNotice(notice);
        }
        
    }
}
