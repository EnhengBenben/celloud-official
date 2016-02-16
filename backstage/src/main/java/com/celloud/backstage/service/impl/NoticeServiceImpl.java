package com.celloud.backstage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.DataState;
import com.celloud.backstage.mapper.NoticeMapper;
import com.celloud.backstage.model.Notice;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.NoticeService;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;


    @Override
    public PageList<Notice> getNoticeByPage(Page page) {
        List<Notice> list=noticeMapper.getNoticeByPage(DataState.ACTIVE, page);
        return new PageList<Notice>(page,list);
    }

    @Override
    public int updateNotice(Notice notice) {
        if(notice!=null&&notice.getNoticeId()!=null){
            Notice ntc=noticeMapper.getNoticeById(notice.getNoticeId());
            if(ntc!=null){
                ntc.setNoticeContext(notice.getNoticeContext());
                ntc.setNoticeTitle(notice.getNoticeTitle());
                return noticeMapper.updateNotice(ntc);
            }
        }
        return 0;
    }

    @Override
    public int addNotice(Notice notice) {
        if(notice!=null){
            return noticeMapper.addNotice(notice);
        }
        return 0;
    }

    @Override
    public Notice getNoticeById(Integer noticeId) {
        if(noticeId!=null){
            return noticeMapper.getNoticeById(noticeId);
        }
        return null;
    }
    

}
