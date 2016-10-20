package com.celloud.service;

import com.celloud.model.PrivateKey;
import com.celloud.model.PublicKey;
import com.celloud.model.mysql.RSAKey;
import com.celloud.model.mysql.User;

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
    public boolean saveRSAKey(PublicKey publicKey, PrivateKey privateKey, User user);

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
    public int deleteExpiresKeys(Integer days);

    /**
     * 删除过期的公钥私钥对,过期时间为7天，自动执行
     */
    public void deleteExpireKeys();

    /**
     * 删除某个用户对应的所有私钥，用在用户修改密码之后
     * 
     * @param userId
     * @return
     */
    public int deleteAllKeys(int userId);

}
