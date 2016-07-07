package com.celloud.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.MessageCategoryEnum;
import com.celloud.constants.MessageWay;
import com.celloud.model.mysql.MessageCategory;
import com.celloud.service.MessageCategoryService;
import com.celloud.utils.ActionLog;

/**
 * @author MQ:
 * @date 2016年7月4日 下午5:53:26
 * @description 消息设置Action
 */
@Controller
@RequestMapping("message/category")
public class MessageCategoryAction {

    private Logger logger = LoggerFactory.getLogger(MessageCategoryAction.class);

    @Resource
    private MessageCategoryService messageCategoryService;
    
    @ActionLog(value = "查看消息设置", button = "消息设置")
    @RequestMapping("setting")
    public ModelAndView setting() {
        logger.info("用户{}查看消息设置", ConstantsData.getLoginUserName());
        ModelAndView mv = new ModelAndView("notice/setting");
        Integer userId = ConstantsData.getLoginUserId();
        List<Map<String, String>> userMessageCategoryList = messageCategoryService.getMessageCategoryByUserId(userId);
        if (userMessageCategoryList != null && userMessageCategoryList.size() > 0) {
            mv.addObject("messageCategoryList", userMessageCategoryList);
            mv.addObject("flag", "1");// 用户更改过自己的消息设置
        } else {
            List<MessageCategory> allMessageCategory = messageCategoryService.getAllMessageCategory();
            mv.addObject("messageCategoryList", allMessageCategory);
            mv.addObject("flag", "0");// 用户未更改过自己的消息设置
        }
        return mv;
    }

    @ActionLog(value = "第一次更改消息设置", button = "消息开关")
    @RequestMapping("insert")
    @ResponseBody
    public int insert(String datas) {
        messageCategoryService.getUserMessageSwitch(ConstantsData.getLoginUserId(), MessageCategoryEnum.REPORT,
                MessageWay.email);
        Integer userId = ConstantsData.getLoginUserId();
        return messageCategoryService.initUserMessageCategory(userId, datas);
    }

    @ActionLog(value = "不是第一次更改消息设置", button = "消息开关")
    @RequestMapping("update")
    @ResponseBody
    public int update(String targetName, Integer targetVal, Integer relatId) {
        messageCategoryService.getUserMessageSwitch(ConstantsData.getLoginUserId(), MessageCategoryEnum.REPORT,
                MessageWay.email);
        return messageCategoryService.editUserMessageSwitch(targetName, targetVal, relatId);
    }

}
