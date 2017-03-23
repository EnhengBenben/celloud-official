package com.celloud.action;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.celloud.constants.ConstantsData;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppCommentService;

/**
 * 
 * @description App评价Action
 * @author miaoqi
 * @date 2017年3月23日 下午1:14:46
 */
@Controller
@RequestMapping("appComment")
public class AppCommentAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppCommentAction.class);

    @Autowired
    private AppCommentService appCommentService;

    /**
     * 
     * @description 根据appId分页查询评论列表
     * @author miaoqi
     * @date 2017年3月23日 下午1:59:12
     * @param page
     * @param appId
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<PageList<Map<String, Object>>> listByAppId(Page page, Integer appId) {
        Integer userId = ConstantsData.getLoginUserId();
        LOGGER.info("用户 {} 获取app评论列表, appId = {}", userId, appId);
        if (appId == null) {
            LOGGER.error("参数错误 appId = {}", appId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        PageList<Map<String, Object>> pageList = appCommentService.listAppCommentByAppId(page, appId);
        LOGGER.info("用户 {} 获取app评论列表成功, appId = {}", userId, appId);
        return ResponseEntity.ok(pageList);
    }

    /**
     * 
     * @description 添加评论
     * @author miaoqi
     * @date 2017年3月23日 下午3:17:02
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveAppComment(Integer appId, Integer score, String comment) {
        Integer userId = ConstantsData.getLoginUserId();
        if (appId == null || score == null || StringUtils.isBlank(comment)) {
            LOGGER.error("用户 {} 对app进行评论参数错误 appId = {}, score = {}, comment = {}", userId, appId, score, comment);
        }
        LOGGER.info("用户 {} 对app进行评论, appId = {}", userId, appId);
        Boolean flag = appCommentService.saveAppComment(userId, appId, score, comment);
        if (flag) {
            LOGGER.info("用户 {} 对app进行评论成功, appId = {}", userId, appId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        LOGGER.error("用户 {} 对app进行评论失败, appId = {}, score = {}, comment = {}", userId, appId, score, comment);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
