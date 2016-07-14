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
        mv.addObject("userMessageCategoryList", userMessageCategoryList);
        return mv;
    }

    @ActionLog(value = "第一次更改消息设置", button = "消息开关")
    @RequestMapping("insert")
    @ResponseBody
    public int insert(String data) {
        Integer userId = ConstantsData.getLoginUserId();
        return messageCategoryService.initUserMessageCategory(userId, data);
    }

    @ActionLog(value = "不是第一次更改消息设置", button = "消息开关")
    @RequestMapping("update")
    @ResponseBody
    public int update(String targetName, Integer targetVal, Integer relatId) {
        Integer userId = ConstantsData.getLoginUserId();
        return messageCategoryService.editUserMessageSwitch(userId, targetName, targetVal, relatId);
    }

}
