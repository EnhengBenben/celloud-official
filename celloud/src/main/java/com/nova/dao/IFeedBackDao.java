package com.nova.dao;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.FeedDaoImpl;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.FeedBack;

@ImplementedBy(FeedDaoImpl.class)
public interface IFeedBackDao {
    /**
     * 查询所有用户反馈信息
     * 
     * @return
     */
    public PageList<FeedBack> selectAllFeedBack(Page page);

    /**
     * 保存反馈信息
     * 
     * @param fb
     * @return
     */
    public boolean saveFeedBack(FeedBack fb);
}
