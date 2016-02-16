package com.celloud.backstage.service;


import com.celloud.backstage.model.Notice;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;

/**
 * 公告接口
 *
 * @author han
 * @date 2016年2月16日 下午2:05:34
 */
public interface NoticeService {
    
    public PageList<Notice> getNoticeByPage(Page page);
    
    public int updateNotice(Notice notice);
    
    public int addNotice(Notice notice);
    
    public Notice getNoticeById(Integer noticeId);
    
}
