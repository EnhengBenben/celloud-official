package com.celloud.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.Classify;
import com.celloud.service.ClassifyService;

/**
 * 
 * @description app分类action
 * @author miaoqi
 * @date 2017年3月23日 下午1:08:39
 */
@Controller
@RequestMapping("classifys")
public class ClassifyAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassifyAction.class);

    @Autowired
    private ClassifyService classifyService;

    /**
     * 
     * @description 根据pid加载app的分类列表
     * @author miaoqi
     * @date 2017年3月24日 上午10:06:08
     * @param pid
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Classify>> listByPid(Integer pid) {
        Integer userId = ConstantsData.getLoginUserId();
        LOGGER.info("用户 {} 加载应用市场分类列表 pid = {}", userId, pid);
        if (pid == null) {
            LOGGER.info("用户 {} 加载应用市场分类列表失败, 参数错误 pid = {}", userId, pid);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<Classify> list = classifyService.listClassifyByPid(pid);
        if (list.size() == 0) {
            LOGGER.error("用户 {} 根据pid没有加载到分类列表 pid = {}", userId, pid);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(list);
    }

}
