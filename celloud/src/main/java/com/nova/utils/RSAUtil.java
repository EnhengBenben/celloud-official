package com.nova.utils;

import java.io.File;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
    /* 缓存的密钥对 */
    private static KeyPair oneKeyPair;
    private static boolean hasCreatedKeyPair = false;

    public static HashMap<String, String> map = new HashMap<String, String>();

    static {
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
        }

        String rsaPath = PropertiesUtil.rsainfo;
        if (!new File(rsaPath).exists()) {
            RSAPrivateKey defaultPrivateKey = RSAUtil.getDefaultPrivateKey();
            FileTools.createFile(rsaPath);
            FileTools.appendWrite(rsaPath, defaultPrivateKey.toString());
        }
        // public modulus
        String pubmo = FileTools.getLineByNum(rsaPath, 2).split(":")[1].trim();
        // public exponent
        String pubex = FileTools.getLineByNum(rsaPath, 3).split(":")[1].trim();
        // private exponent
        String priex = FileTools.getLineByNum(rsaPath, 4).split(":")[1].trim();
        map.put("pubmo", pubmo);
        map.put("pubex", pubex);
        map.put("priex", priex);
    }

    /* 不允许实例化 */
    private RSAUtil() {
    }

    /* 返回已经初始化的默认的私钥 */
    private static RSAPrivateKey getDefaultPrivateKey() {
        KeyPair keyPair = getKeyPair();
        if (keyPair != null) {
            return (RSAPrivateKey) keyPair.getPrivate();
        }
        return null;
    }

    /* 获取密钥对 */
    private static KeyPair getKeyPair() {
        if (hasCreatedKeyPair) {
            return oneKeyPair;
        } else {
            return generateKeyPair();
        }
    }

    /* 生成并返回RSA密钥对 */
    private static synchronized KeyPair generateKeyPair() {
        keyPairGen.initialize(KEY_SIZE, new SecureRandom(DateFormatUtils.format(new Date(), "yyyyMMdd").getBytes()));
        oneKeyPair = keyPairGen.generateKeyPair();
        hasCreatedKeyPair = true;
        return oneKeyPair;
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

    /* 使用默认的私钥解密给定的字符串 */
    private static String decryptString(String encryptStr) {
        if (encryptStr == null || encryptStr.equals("")) {
            return null;
        }
        com.nova.sdo.PrivateKey pk = new com.nova.sdo.PrivateKey(new BigInteger(map.get("pubmo"), 16),
                new BigInteger(map.get("priex"), 16));
        try {
            byte[] en_data = Hex.decodeHex(encryptStr.toCharArray());
            byte[] data = decrypt(pk, en_data);
            return new String(data);
        } catch (DecoderException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /* 使用默认的私钥解密由js加密的字符串 */
    public static String decryptStringByJs(String encryptStr) {
        String result = decryptString(encryptStr);
        if (result != null) {
            return StringUtils.reverse(result);
        }
        return null;
    }
}