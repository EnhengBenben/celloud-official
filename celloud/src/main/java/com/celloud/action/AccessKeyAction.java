package com.celloud.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.celloud.alidayu.AlidayuConfig;
import com.celloud.constants.ConstantsData;
import com.celloud.model.mongo.UserCaptcha;
import com.celloud.model.mysql.AccessKey;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AccessKeyService;
import com.celloud.service.CustomerService;
import com.celloud.service.UserService;
import com.celloud.utils.DataUtil;

/**
 * 
 * @description apikey,apiscret
 * @author miaoqi
 * @date 2017年3月8日 下午5:17:46
 */
@Controller
@RequestMapping("key")
public class AccessKeyAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessKeyAction.class);
    @Autowired
    private AccessKeyService accessKeyService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;

    /**
     * 
     * @description 医院管理员分页查询keys
     * @author miaoqi
     * @date 2017年3月8日 下午5:34:39
     * @param page
     * @return
     */
    @RequestMapping(value = "keys", method = RequestMethod.GET)
    public ResponseEntity<PageList<AccessKey>> listKeys(Page page) {
        Integer userId = ConstantsData.getLoginUserId();
        LOGGER.info("用户 {} 查看key列表", userId);
        return ResponseEntity.ok(accessKeyService.listKeys(userId, page));
    }

    /**
     * 
     * @description 为用户增加一对key,secret
     * @author miaoqi
     * @date 2017年3月9日 上午10:49:57
     * @return
     */
    @RequestMapping(value = "keys", method = RequestMethod.POST)
    public ResponseEntity<AccessKey> addKey() {
        Integer userId = ConstantsData.getLoginUserId();
        String username = ConstantsData.getLoginUserName();
        LOGGER.info("用户 {} 增加key", userId);
        Boolean flag = accessKeyService.save(userId, username);
        if (flag) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 
     * @description 根据主键删除一堆key, secret
     * @author miaoqi
     * @date 2017年3月9日 上午11:19:51
     * @return
     */
    @RequestMapping(value = "keys/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeKey(@PathVariable("id") Integer keyId) {
        Integer userId = ConstantsData.getLoginUserId();
        LOGGER.info("用户 {} 删除key, keyId = {}", userId, keyId);
        Boolean flag = accessKeyService.remove(keyId);
        if (flag) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 
     * @description 更改key的启用/禁用状态 
     * @author miaoqi
     * @date 2017年3月9日 下午1:05:37
     * @param keyId
     * @return
     */
    @RequestMapping(value = "keys/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateKey(@PathVariable("id") Integer keyId, Integer state) {
        Integer userId = ConstantsData.getLoginUserId();
        LOGGER.info("用户 {} 更改key状态, keyId = {}, state = {}", userId, keyId, state);
        AccessKey accessKey = new AccessKey();
        accessKey.setId(keyId);
        accessKey.setState(state);
        int num = accessKeyService.updateByPrimaryKeySelective(accessKey);
        if (num == 1) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping("checkCellphone")
    public ResponseEntity<String> checkCellphone() {
        User user = userService.selectUserById(ConstantsData.getLoginUserId());
        String cellphone = user.getCellphone();
        if (StringUtils.isEmpty(cellphone)) {
            return ResponseEntity.ok("");
        }
        cellphone = cellphone.substring(0, 3) + "****" + cellphone.substring(7);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain");
        return new ResponseEntity<String>(cellphone, headers, HttpStatus.OK);
    }

    /**
     * 
     * @description 向用户发送校验码
     * @author miaoqi
     * @date 2017年3月9日 下午5:04:16
     * @return
     */
    @RequestMapping("sendCaptcha")
    public ResponseEntity<Void> sendCaptcha() {
        String cellphone = ConstantsData.getLoginUser().getCellphone();
        // 获取验证码
        String captcha = DataUtil.getCapchaRandom();
        // 新增或更新验证码
        Boolean flag = customerService.addOrUpdateUserCaptcha(cellphone, captcha);
        if (flag) {
            LOGGER.info("验证码 {} 发送成功", captcha);
            // 200 发送成功
            return ResponseEntity.ok().build();
        }
        LOGGER.info("验证码 {} 发送失败", captcha);
        // 500 发送失败
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 
     * @description 校验用户填写的验证码
     * @author miaoqi
     * @date 2017年3月9日 下午5:08:03
     * @param captcha
     * @return
     */
    @RequestMapping(value = "authenticationCellphone", method = RequestMethod.POST)
    public ResponseEntity<String> authenticationCellphone(String captcha, HttpSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain");
        String cellphone = ConstantsData.getLoginUser().getCellphone();
        // 参数校验
        LOGGER.info("用户 {} 进行手机号认证, 手机号 = {}, 验证码 = {}", ConstantsData.getLoginUserId(), cellphone, captcha);
        if (StringUtils.isEmpty(cellphone)) {
            LOGGER.info("手机号码格式有误");
            return new ResponseEntity<String>("手机号码格式有误", headers, HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(captcha)) {
            LOGGER.info("验证码格式有误");
            return new ResponseEntity<String>("验证码格式有误", headers, HttpStatus.BAD_REQUEST);
        }
        // 1. 根据手机号从mongo中查询用户的验证码信息
        UserCaptcha userCaptcha = customerService.getUserCaptchaByCellphone(cellphone);
        if (userCaptcha != null) {
            // 2. 获取创建时间
            DateTime createDate = new DateTime(userCaptcha.getCreateDate());
            // 3. 创建时间 + 1分钟 大于当前时间, 代表没有过期, 并且验证码相等
            if (createDate.plusMinutes(AlidayuConfig.captcha_expire_time).isAfterNow()) {
                if (userCaptcha.getCaptcha().equals(captcha)) {
                    LOGGER.info("用户 {} 认证成功", ConstantsData.getLoginUserId());
                    session.setAttribute("authenFlag", 1);
                    return new ResponseEntity<String>("认证成功", headers, HttpStatus.OK);
                } else {
                    LOGGER.info("用户 {} 验证码有误", ConstantsData.getLoginUserId());
                    return new ResponseEntity<String>("验证码有误", headers, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        LOGGER.info("用户 {} 尚未获取验证码或验证码已超时", ConstantsData.getLoginUserId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("尚未获取验证码或验证码已超时");
    }

    /**
     * 
     * @description 根据主键查询key, secret
     * @author miaoqi
     * @date 2017年3月9日 下午6:50:04
     * @param keyId
     * @return
     */
    @RequestMapping(value = "keys/{id}", method = RequestMethod.GET)
    public ResponseEntity<AccessKey> getKey(@PathVariable("id") Integer keyId, HttpSession session) {
        Integer userId = ConstantsData.getLoginUserId();
        LOGGER.info("用户 {} 获取key信息", userId);
        Optional<AccessKey> opt = Optional.ofNullable(accessKeyService.get(keyId));
        if (opt.isPresent()) {
            Map<Integer, String> map = (Map<Integer, String>) session.getAttribute("secretJson");
            if (map == null) {
                map = new HashMap<Integer, String>();
            }
            map.put(opt.get().getId(), opt.get().getKeySecret());
            session.setAttribute("secretJson", map);
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    /**
     * 
     * @description 获取key是否认证
     * @author miaoqi
     * @date 2017年3月9日 下午6:50:19
     * @param session
     * @return
     */
    @RequestMapping("getAuthenFlag")
    public ResponseEntity<Integer> getAuthenFlag(HttpSession session) {
        Integer authenFlag = (Integer) session.getAttribute("authenFlag");
        return ResponseEntity.ok(authenFlag);
    }

    /**
     * 
     * @description 获取用户已显示的key, secret
     * @author miaoqi
     * @date 2017年3月10日 上午10:01:37
     * @return
     */
    @RequestMapping("getSecretJson")
    public ResponseEntity<Map<Integer, String>> getSecretJson(HttpSession session) {
        Map<Integer, String> map = (Map<Integer, String>) session.getAttribute("secretJson");
        if (map == null) {
            map = new HashMap<Integer, String>();
        }
        return ResponseEntity.ok(map);
    }

    @RequestMapping("removeSecret")
    public ResponseEntity<Map<Integer, String>> removeSecret(Integer id, HttpSession session) {
        Map<Integer, String> map = (Map<Integer, String>) session.getAttribute("secretJson");
        if (map == null) {
            map = new HashMap<Integer, String>();
        } else {
            map.remove(id);
        }
        return ResponseEntity.ok(map);
    }

}
