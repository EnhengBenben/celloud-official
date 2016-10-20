package com.celloud.utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAUtil {
    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);
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
            if (keyPairGen == null) {
                keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("初始化RSAUtils失败！", e);
        }

    }

    /* 不允许实例化 */
    private RSAUtil() {
    }

    /* 生成并返回RSA密钥对 */
    public static KeyPair generateKeyPair() {
        keyPairGen.initialize(KEY_SIZE,
                new SecureRandom(DateFormatUtils.format(new Date(), "yyyyMMddhhmmss").getBytes()));
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
            logger.error("使用私钥解密数据失败！", e);
        }
        return null;
    }

    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 使用公钥加密数据
     * 
     * @param key
     * @param str
     * @return
     */
    public static String encryptedString(RSAPublicKey key, String str) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 模长
            int key_len = key.getModulus().bitLength() / 8;
            // 加密数据长度 <= 模长-11
            String[] datas = splitString(str, key_len - 11);
            String mi = "";
            // 如果明文长度大于模长-11则要分组加密
            for (String s : datas) {
                mi += bcd2Str(cipher.doFinal(s.getBytes()));
            }
            return mi;
        } catch (Exception e) {
            logger.error("使用公钥加密数据失败！", e);
        }
        return null;
    }

    /**
     * 加密数据
     * 
     * @param modulus
     * @param pubExponent
     * @param str
     * @return
     */
    public static String encryptedString(String modulus, String pubExponent, String str) {
        try {
            KeyFactory factory = KeyFactory.getInstance(ALGORITHOM);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(modulus, 16),
                    new BigInteger(pubExponent, 16));
            RSAPublicKey key = (RSAPublicKey) factory.generatePublic(keySpec);
            return encryptedString(key, str);
        } catch (NoSuchAlgorithmException e) {
            logger.error("使用公钥加密数据失败！", e);
        } catch (InvalidKeySpecException e) {
            logger.error("使用公钥加密数据失败！", e);
        }
        return null;
    }

    /* 使用私钥解密给定的字符串 */
    public static String decryptString(PrivateKey privateKey, String encryptStr) {
        if (encryptStr == null || encryptStr.equals("")) {
            return null;
        }
        String result = null;
        try {
            byte[] en_data = Hex.decodeHex(encryptStr.toCharArray());
            byte[] data = decrypt(privateKey, en_data);
            if (data != null) {
                result = new String(data);
            }
        } catch (DecoderException e) {
            logger.error("使用私钥解密数据失败！", e);
        }
        return result;
    }

    /* 使用私钥解密由js加密的字符串 */
    public static String decryptStringByJs(PrivateKey privateKey, String encryptStr) {
        String result = decryptString(privateKey, encryptStr);
        if (result != null) {
            return StringUtils.reverse(result);
        }
        return null;
    }

    public static void main(String[] args) {
        String password = "123";
        KeyPair keyPair = RSAUtil.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println("pub_m:" + rsaPublicKey.getModulus().toString(16));
        System.out.println("pup_e:" + rsaPublicKey.getPublicExponent().toString(16));
        System.out.println("pri_m:" + rsaPrivateKey.getModulus().toString(16));
        System.out.println("pri_e:" + rsaPrivateKey.getPrivateExponent().toString(16));
        // String temp = RSAUtil.encryptedString(rsaPublicKey, password);
        String temp = RSAUtil.encryptedString(rsaPublicKey.getModulus().toString(16),
                rsaPublicKey.getPublicExponent().toString(16), password);
        System.out.println(temp);
        temp = RSAUtil.decryptString(keyPair.getPrivate(), temp);
        System.out.println(temp);
    }
}