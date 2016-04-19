package com.celloud.service.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.celloud.mapper.RSAKeyMapper;
import com.celloud.model.PrivateKey;
import com.celloud.model.PublicKey;
import com.celloud.model.mysql.RSAKey;
import com.celloud.model.mysql.User;
import com.celloud.service.RSAKeyService;

/**
 * 用户记住密码使用的公钥私钥管理接口实现类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月24日 下午5:50:52
 */
@Service("rsaKeyServiceImpl")
public class RSAKeyServiceImpl implements RSAKeyService {
    private static Logger logger = LoggerFactory.getLogger(RSAKeyServiceImpl.class);
    @Resource
    private RSAKeyMapper rsaKeyMapper;

    @Override
    public boolean saveRSAKey(PublicKey publicKey, PrivateKey privateKey, User user) {
        RSAKey key = new RSAKey();
        key.setCreateTime(new Date());
        key.setModulus(privateKey.getModulus().toString(16));
        key.setPriExponent(privateKey.getPrivateExponent().toString(16));
        key.setPubExponent(publicKey.getExponent());
        key.setUserId(user.getUserId());
        key.setState(0);
        return rsaKeyMapper.insertSelective(key) >= 0;
    }

    @Override
    public RSAKey getByModulus(String modulus) {
        return rsaKeyMapper.getByModulus(modulus);
    }

    @Override
    public int deleteExpiresKeys(Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0 - days);
        Date lastTime = calendar.getTime();
        return rsaKeyMapper.deleteExpiresKeys(lastTime);
    }

    @Scheduled(cron = "0 0 0,12 * * ?")
    @Override
    public void deleteExpireKeys() {
        int result = deleteExpiresKeys(7);
        logger.info("清理掉{}个过期的key！", result);
    }

    @Override
    public int deleteByModulus(String modulus) {
        return rsaKeyMapper.deleteByModulus(modulus);
    }

    @Override
    public int deleteAllKeys(int userId) {
        return rsaKeyMapper.deleteAllKeys(userId);
    }

}
