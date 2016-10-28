package com.celloud.backstage.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSAUtil {
    private static final Logger LOGGER = Logger.getLogger(RSAUtil.class);
    /* 算法名称 */
    private static final String ALGORITHOM = "RSA";
    /* 默认的安全服务提供者 */
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
    /* 密钥大小 */
    private static final int KEY_SIZE = 1024;
    /* 密钥产生器 */
    private static KeyPairGenerator keyPairGen = null;

    public static HashMap<String, String> map = new HashMap<String, String>();

    static {
        try {
            if(keyPairGen==null){
                keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
        }

    }

    /* 不允许实例化 */
    private RSAUtil() {
    }

    /* 生成并返回RSA密钥对 */
    public static KeyPair generateKeyPair() {
        keyPairGen.initialize(KEY_SIZE, new SecureRandom(DateFormatUtils.format(new Date(), "yyyyMMddhhmmss").getBytes()));
        return keyPairGen.generateKeyPair();
    }

    /* 使用私钥解密数据 */
    private static byte[] decrypt(PrivateKey privateKey, byte[] data) {
        Cipher ci;
        try {
            ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
            ci.init(Cipher.DECRYPT_MODE, privateKey);
            return ci.doFinal(data);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /* 使用私钥解密给定的字符串 */
    private static String decryptString(PrivateKey privateKey,String encryptStr) {
        if (encryptStr == null || encryptStr.equals("")) {
            return null;
        }
        try {
            byte[] en_data = Hex.decodeHex(encryptStr.toCharArray());
            byte[] data = decrypt(privateKey, en_data);
            return new String(data);
        } catch (DecoderException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /* 使用私钥解密由js加密的字符串 */
    public static String decryptStringByJs(PrivateKey privateKey,String encryptStr) {
        String result = decryptString(privateKey,encryptStr);
        if (result != null) {
            return StringUtils.reverse(result);
        }
        return null;
    }
}