/**    
 * @Title: INoticeDao.java  
 * @Package com.nova.dao   
 * @author summer    
 * @date 2012-6-28 上午10:33:16  
 * @version V1.0    
 */
package com.nova.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.NoticeDaoImpl;
import com.nova.sdo.Notice;

/**
 * @ClassName: INoticeDao
 * @Description: (公告)
 * @author summer
 * @date 2012-6-28 上午10:33:16
 * 
 */
@ImplementedBy(NoticeDaoImpl.class)
public interface INoticeDao {
    boolean addNotice(Notice notice);// 增加公告

    void deleteNotice(int noticeId);// 公告失效

    void editNotice(String noticeTitle, String noticeContext, int noticeId);// 编辑公告

    Notice getNoticeById(int noticeId);// 根据公告号查询公告

    List<Notice> getAllNotice();// 查询所有公告

    Notice getNewsNotice();// 查询最新公告
}
