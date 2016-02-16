package com.celloud.service.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.RSAKeyMapper;
import com.celloud.model.RSAKey;
import com.celloud.service.RSAKeyService;

/**
 * 用户记住密码使用的公钥私钥管理接口实现类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月24日 下午5:50:52
 */
@Service("rsaKeyServiceImpl")
public class RSAKeyServiceImpl implements RSAKeyService {
    @Resource
    private RSAKeyMapper rsaKeyMapper;

    @Override
    public boolean insert(RSAKey rsaKey) {
        return rsaKeyMapper.insertSelective(rsaKey) >= 0;
    }

    @Override
    public RSAKey getByModulus(String modulus) {
        return rsaKeyMapper.getByModulus(modulus);
    }

    @Override
    public int deleteExpiresKeys(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0 - days);
        Date lastTime = calendar.getTime();
        return rsaKeyMapper.deleteExpiresKeys(lastTime);
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
