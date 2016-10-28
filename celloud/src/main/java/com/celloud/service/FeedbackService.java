package com.celloud.service;

import java.util.List;

import com.celloud.model.mysql.Feedback;
import com.celloud.model.mysql.FeedbackAttachment;
import com.celloud.model.mysql.FeedbackReply;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * 投诉与建议等service接口
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月24日 上午10:03:19
 */

public interface FeedbackService {

	/**
	 * 保存一个建议
	 * 
	 * @param feedback
	 * @return
	 */
	public int insert(User user, Feedback feedback, List<String> attachments);

	/**
	 * 获取当前用户的问题反馈（工单）
	 * 
	 * @param page
	 * @return
	 */
	public PageList<Feedback> findFeedbacks(Page page);

	/**
	 * 根据id获取问题反馈(工单)
	 * 
	 * @param id
	 * @return
	 */
	public Feedback selectFeedbackById(Integer id);

	/**
	 * 查询工单附件
	 * 
	 * @param feedbackId
	 * @return
	 */
	public List<FeedbackAttachment> findAttachments(Integer feedbackId);

	/**
	 * 查询工单回复
	 * 
	 * @param feedbackId
	 * @return
	 */
	public List<FeedbackReply> findReplies(Integer feedbackId);

	/**
	 * 回复工单
	 * 
	 * @param feedbackId
	 * @param content
	 * @return
	 */
	public boolean insertReply(int feedbackId, String content);

	/**
	 * 关闭工单
	 * 
	 * @param feedbackId
	 * @return
	 */
	public boolean solve(int feedbackId);

	/**
	 * 给工单添加附件
	 * 
	 * @param feedbackId
	 * @param name
	 */
	public void saveAttach(Integer feedbackId, String name);

	/**
	 * 删除指定的工单附件
	 * 
	 * @param attachId
	 * @return
	 */
	public boolean deleteAttach(Integer attachId);

	/**
	 * 删除指定名称的未保存的附件
	 * 
	 * @param name
	 * @return
	 */
	public boolean deleteAttachTemp(String name);

}
