package com.nova.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.SoftwareCommentImpl;
import com.nova.pager.Page;
import com.nova.sdo.SoftwareComment;

/**
 * 软件评论接口
 * 
 * @类名称：ISoftwareCommentDao  
 * @类描述：  
 * @创建人：zl  
 * @创建时间：2013-8-7 下午3:19:48    
 * @version      
 */
@ImplementedBy(SoftwareCommentImpl.class)
public interface ISoftwareCommentDao {
    /**
     * 获取软件评论列表,不包括回复
     * 
     * @param softwareId
     * @return
     */
    public List<SoftwareComment> getCommentList(int softwareId, Page page);

    /**
     * 获取软件评论（包括回复）列表
     * 
     * @param softwareId
     * @return
     */
    public List<SoftwareComment> getCommentList(int softwareId);

    /**
     * 获取软件评论回复列表
     * 
     * @param softwareId
     * @return
     */
    public List<SoftwareComment> getReplyList(int commentId);

    /**
     * 保存评论信息，根据评论类型判断保存的是评论信息还是回复信息
     * 
     * @param comment
     * @return
     */
    public int addComment(SoftwareComment comment);

    /**
     * 根据编号删除评论
     * 
     * @param commentId
     * @return
     */
    public int delComment(int commentId, int delType);

    /**
     * 根据编号获取评论信息
     * 
     * @param commentId
     * @return
     */
    public SoftwareComment getCommentById(int commentId);
}
