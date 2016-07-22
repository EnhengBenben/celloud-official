package com.celloud.backstage.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.model.EmailTemplate;
import com.celloud.backstage.model.UserSelect;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.EmailTemplateService;
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
    @Resource
    private EmailTemplateService emailTemplateService;

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
	
    @RequestMapping("mail/allTemplate")
    public ModelAndView allMailTemplate(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView mv = new ModelAndView("mailing/mail_template_main");
        Page page = new Page(currentPage, pageSize);
        PageList<EmailTemplate> pageList = emailTemplateService.getEmailTemplates(page);
        return mv.addObject("pageList", pageList);
    }

    @RequestMapping("mail/toEditTemplate")
    public ModelAndView toEditMailTemplate(Integer id) {
        ModelAndView mv = new ModelAndView("mailing/mail_template_detail");
        return mv.addObject("emailTemplate", emailTemplateService.findById(id));
    }

    @ResponseBody
    @RequestMapping(value = "mail/editTemplate", method = RequestMethod.POST)
    public String editTemplate(EmailTemplate emailTemplate) {
        Integer id = emailTemplate.getId();
        EmailTemplate et = emailTemplateService
                .getTemplateByMethod(emailTemplate.getMethod(), id);
        if (et != null) {
            return "模板方法重复，请重新填写";
        }
        if (id != null) {
            logger.info("修改邮件模板appId={}", emailTemplate.getId());
            return emailTemplateService.updateEmailTemplate(emailTemplate) > 0
                    ? "success" : "wrong";
        } else {
            logger.info("新增邮件模板");
            return emailTemplateService.addEmailTemplate(emailTemplate) > 0
                    ? "success" : "wrong";
        }

    }

    @ResponseBody
    @RequestMapping("mail/deleteTemplate")
    public Integer deleteTemplate(Integer id) {
        return emailTemplateService.deleteTemplate(id);
    }
}
