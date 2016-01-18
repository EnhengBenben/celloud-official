package com.celloud.backstage.model;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;

public class PrivateKey implements RSAPrivateKey {
    private static final long serialVersionUID = 1L;
    private String algorithm;
    private byte[] encoded;
    private String format;
    private BigInteger modulus;
    private BigInteger privateExponent;

    public PrivateKey(BigInteger modulus, BigInteger privateExponent) {
        this.modulus = modulus;
        this.privateExponent = privateExponent;
    }

    @Override
    public String getAlgorithm() {
        return algorithm;
    }

    @Override
    public byte[] getEncoded() {
        return encoded;
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public BigInteger getModulus() {
        return modulus;
    }

    @Override
    public BigInteger getPrivateExponent() {
        return privateExponent;
    }
}