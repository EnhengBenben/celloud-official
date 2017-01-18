package com.celloud.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliEmailUtils;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.FeedbackConstants;
import com.celloud.mail.EmailUtils;
import com.celloud.mapper.FeedbackAttachmentMapper;
import com.celloud.mapper.FeedbackMapper;
import com.celloud.mapper.FeedbackReplyMapper;
import com.celloud.model.mysql.Feedback;
import com.celloud.model.mysql.FeedbackAttachment;
import com.celloud.model.mysql.FeedbackReply;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.FeedbackService;
import com.celloud.utils.DateUtil;

/**
 * 投诉与建议service实现类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月24日 上午10:04:23
 */
@Service("feedbackServiceImpl")
public class FeedbackServiceImpl implements FeedbackService {
	private Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);
	@Resource
	private EmailUtils emailUtils;
	@Resource
	private AliEmailUtils aliEmailUtils;
	private static final Page DEFAULT_PAGE = new Page(1, 5);
	@Resource
	private FeedbackMapper feedbackMapper;
	@Resource
	private FeedbackAttachmentMapper attachmentMapper;
	@Resource
	private FeedbackReplyMapper replyMapper;

	@Override
	public int insert(User user, Feedback feedback, List<String> attachments) {
		if (user == null) {
			user = ConstantsData.getLoginUser();
		}
		feedback.setCreateDate(new Date());
		feedback.setHasAttachment(attachments != null && attachments.size() > 0
				? FeedbackConstants.HASATTACHMENT.byteValue() : FeedbackConstants.NOT_HAS_ATTACHMENT.byteValue());
		feedback.setUserId(user.getUserId());
		feedback.setUsername(user.getUsername());
		feedback.setEmail(user.getEmail());
		int result = feedbackMapper.insertSelective(feedback);
		List<FeedbackAttachment> feedbackAttachments = new ArrayList<>();
		if (attachments != null) {
			for (String name : attachments) {
				FeedbackAttachment attachment = new FeedbackAttachment();
				attachment.setFeedbackId(feedback.getId());
				attachment.setFilePath(name);
				attachment.setFileType(name.substring(name.lastIndexOf(".") + 1));
				try {
					FileUtils.moveFile(new File(FeedbackConstants.getAttachmentTempPath() + File.separator + name),
							new File(FeedbackConstants.getAttachment(name)));
					feedbackAttachments.add(attachment);
				} catch (IOException e) {
					logger.error("复制附件失败：{}", name, e);
				}
			}
		}
		if (feedbackAttachments.size() > 0) {
			result = attachmentMapper.insertbatch(feedbackAttachments);
		}
		feedbackMapper.updateAttachState(feedback.getId());
		sendEmail(feedback, feedbackAttachments);
		cleanAttachment();
		return result;
	}

	private void sendEmail(Feedback feedback, List<FeedbackAttachment> attachments) {
		if (emailUtils.getFeedbackMailTo() == null) {
			logger.warn("用户提交了工单，正在发送工单信息邮件，但是没有找到邮件接收者！");
			return;
		}
		AliEmail aliEmail = AliEmail.template(EmailType.FEADBACK)
				.substitutionVars(AliSubstitution.sub().set(EmailParams.FEADBACK.title.name(), feedback.getTitle())
						.set(EmailParams.FEADBACK.start.name(),
								DateUtil.getDateToString(feedback.getCreateDate(), DateUtil.YMDHMS))
						.set(EmailParams.FEADBACK.home.name(), ConstantsData.getContextUrl())
						.set(EmailParams.FEADBACK.userName.name(), feedback.getUsername())
						.set(EmailParams.FEADBACK.email.name(), feedback.getEmail())
						.set(EmailParams.FEADBACK.context.name(), feedback.getContent())
						.set(EmailParams.FEADBACK.end.name(), DateUtil.getDateToString("yyyy")));
		aliEmailUtils.simpleSend(aliEmail, emailUtils.getFeedbackMailTo());
	}

	/**
	 * 清理工单附件的临时目录
	 */
	private void cleanAttachment() {
		String path = FeedbackConstants.getAttachmentTempPath();
		File tempDir = new File(path);
		if (tempDir == null || !tempDir.exists()) {
			return;
		}
		if (tempDir.isFile()) {
			tempDir.delete();
			return;
		}
		File[] tempFiles = tempDir.listFiles();
		if (tempFiles == null || tempFiles.length == 0) {
			return;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		Date date = calendar.getTime();
		for (File file : tempFiles) {
			ObjectId id = new ObjectId(file.getName().substring(0, file.getName().indexOf(".")));
			if (date.after(id.getDate())) {
				file.delete();
			}
		}
	}

	@Override
	public PageList<Feedback> findFeedbacks(Page page) {
		page = page == null ? DEFAULT_PAGE : page;
		// TODO 不需要分页的需求比较蛋疼，不太可能，这里临时解决办法就是查询前1000条，足够了，多余1000一定需要分页的
		page.setPageSize(1000);
		List<Feedback> list = feedbackMapper.selectByUserId(ConstantsData.getLoginUserId(), page);
		return new PageList<>(page, list);
	}

	@Override
	public Feedback selectFeedbackById(Integer id) {
		return feedbackMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FeedbackAttachment> findAttachments(Integer feedbackId) {
		return attachmentMapper.findByFeedbackId(feedbackId);
	}

	@Override
	public List<FeedbackReply> findReplies(Integer feedbackId) {
		return replyMapper.findByFeedbackId(feedbackId);
	}

	@Override
	public boolean insertReply(int feedbackId, String content) {
		FeedbackReply reply = new FeedbackReply();
		reply.setContent(content);
		reply.setFeedbackId(feedbackId);
		reply.setCreateTime(new Date());
		reply.setEmail(ConstantsData.getLoginEmail());
		reply.setUserId(ConstantsData.getLoginUserId());
		reply.setUserName(ConstantsData.getLoginUserName());
		return replyMapper.insertSelective(reply) > 0;
	}

	@Override
	public boolean solve(int feedbackId) {
		Feedback feedback = new Feedback();
		feedback.setId(feedbackId);
		feedback.setSolve(FeedbackConstants.SOLVED.byteValue());
		return feedbackMapper.updateByPrimaryKeySelective(feedback) > 0;
	}

	@Override
	public void saveAttach(Integer feedbackId, String name) {
		FeedbackAttachment attachment = new FeedbackAttachment();
		attachment.setFeedbackId(feedbackId);
		attachment.setFilePath(name);
		attachment.setFileType(name.substring(name.lastIndexOf(".") + 1));
		attachmentMapper.insert(attachment);
		feedbackMapper.updateAttachState(feedbackId);
		cleanAttachment();
	}

	@Override
	public boolean deleteAttach(Integer attachId) {
		FeedbackAttachment attachment = attachmentMapper.selectByPrimaryKey(attachId);
		int result = attachmentMapper.deleteByPrimaryKey(attachId);
		feedbackMapper.updateAttachState(attachment.getFeedbackId());
		return result > 0;
	}

	@Override
	public boolean deleteAttachTemp(String name) {
		String path = FeedbackConstants.getAttachmentTempPath() + File.separator + name;
		File targetFile = new File(path);
		return targetFile.delete();
	}

}
