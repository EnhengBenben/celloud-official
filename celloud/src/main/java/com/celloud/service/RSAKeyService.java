package com.celloud.service;

import com.celloud.model.mysql.RSAKey;

/**
 * 用户记住密码使用的公钥私钥管理接口
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月24日 下午5:51:15
 */
public interface RSAKeyService {
    /**
     * 插入公钥私钥对
     * 
     * @param rsaKey
     * @return
     */
    public boolean insert(RSAKey rsaKey);

    /**
     * 根据系数获取公钥私钥对
     * 
     * @param modulus
     * @return
     */
    public RSAKey getByModulus(String modulus);

    /**
     * 根据系数删除公钥私钥对
     * 
     * @param modulus
     * @return
     */
    public int deleteByModulus(String modulus);

    /**
     * 删除过期的公钥私钥对
     * 
     * @param days
     *            有效期的天数
     * @return
     */
    public int deleteExpiresKeys(int days);

    /**
     * 删除某个用户对应的所有私钥，用在用户修改密码之后
     * 
     * @param userId
     * @return
     */
    public int deleteAllKeys(int userId);

}
