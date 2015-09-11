package com.nova.utils;

/**
 * Copyright (C) 2010 ustb
 * Author:  ZhaoXY
 * Function:  AES crypt,decrypt
 * Create date:2010-4-29  下午03:40:12
 * Last Modified:
 * Last Modified date:
 */

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESCrypt {

    // 加密
    public static String encrypt(String sSrc, String sKey) {
	try {
	    if (sKey == null) {
		System.out.print("Key is null");
		return null;
	    }
	    // 判断Key是否为16位
	    if (sKey.length() != 16) {
		System.out.print("Key length is not 16");
		return null;
	    }
	    byte[] raw = sKey.getBytes("ASCII");
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec iv = new IvParameterSpec(
		    "0102030405060708".getBytes("ASCII"));
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	    byte[] encrypted = cipher.doFinal(sSrc.getBytes("ASCII"));
	    return Base64.encodeBase64String(encrypted);
	} catch (UnsupportedEncodingException ex) {
	    System.err.println(ex);
	    return null;
	} catch (NoSuchAlgorithmException ex) {
	    System.err.println(ex);
	    return null;
	} catch (NoSuchPaddingException ex) {
	    System.err.println(ex);
	    return null;
	} catch (InvalidKeyException ex) {
	    System.err.println(ex);
	    return null;
	} catch (InvalidAlgorithmParameterException ex) {
	    System.err.println(ex);
	    return null;
	} catch (IllegalBlockSizeException ex) {
	    System.err.println(ex);
	    return null;
	} catch (BadPaddingException ex) {
	    System.err.println(ex);
	    return null;
	}
    }

    // 解密
    public static String decrypt(String sSrc, String sKey) {
	try {
	    // 判断Key是否正确
	    if (sKey == null) {
		System.out.print("Key is null");
		return null;
	    }
	    // 判断Key是否为16位
	    if (sKey.length() != 16) {
		System.out.print("Key length is not 16");
		return null;
	    }
	    byte[] raw = sKey.getBytes("ASCII");
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec iv = new IvParameterSpec(
		    "0102030405060708".getBytes("ASCII"));
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	    byte[] encrypted = Base64.decodeBase64(sSrc);

	    byte[] original = cipher.doFinal(encrypted);
	    String originalString = new String(original);
	    return originalString;
	} catch (UnsupportedEncodingException ex) {
	    System.err.println(ex);
	    return null;
	} catch (NoSuchAlgorithmException ex) {
	    System.err.println(ex);
	    return null;
	} catch (NoSuchPaddingException ex) {
	    System.err.println(ex);
	    return null;
	} catch (InvalidKeyException ex) {
	    System.err.println(ex);
	    return null;
	} catch (InvalidAlgorithmParameterException ex) {
	    System.err.println(ex);
	    return null;
	} catch (IllegalBlockSizeException ex) {
	    System.err.println(ex);
	    return null;
	} catch (BadPaddingException ex) {
	    System.err.println(ex);
	    return null;
	}
    }

    public static void main(String[] args) {
	String cipher = encrypt("Admin@123", "nova1234567cloud");
	System.out.println(cipher);
	String plain = decrypt(cipher, "nova1234567cloud");
	System.err.println(plain);
    }
}