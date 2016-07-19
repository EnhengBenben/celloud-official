package com.celloud.backstage.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.model.UserSelect;
import com.celloud.backstage.service.UserService;
import com.celloud.backstage.utils.EmailUtils;

/**
 * 
 *
 * @author han
 * @date 2016年2月29日 下午2:18:10
 */
@Controller
public class MailingAction {
	Logger logger = LoggerFactory.getLogger(MailingAction.class);
	@Resource
	private UserService userService;

	/**
	 * 跳转到邮件邮件群发页面
	 * 
	 * @return
	 */
	@RequestMapping("mailing")
	public ModelAndView toMailing() {
		ModelAndView mv = new ModelAndView("mailing/mailing_main");
		return mv;
	}

	/**
	 * 获取用户列表
	 * 
	 * @return
	 */
	@RequestMapping("mailing/getUser")
	@ResponseBody
	public List<UserSelect> getUser() {
		List<UserSelect> userSelectList = userService.getAllUserSelectList();
		return userSelectList;
	}

	@ResponseBody
	@RequestMapping("mailing/send")
	public int sendEmail(String[] emails, String emailContent,String emailTitle) {
		logger.info("邮件群发");
		try {
			EmailUtils.sendWithTitle(emailTitle, emailContent, emails);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
}
