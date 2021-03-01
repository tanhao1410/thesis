package com.github.tanhao1410.thesis.common.utils.utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hushawen
 * @date 2020/6/30 14:53
 */
public class RSAUtils {

    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcLRobGxeU1TIgppZO+cSb/GNeDA1+L0LoQCx1Fsi2shjXZXwBNNZIxGf1Y3vMXYzmCTmECGt360gmUtpvGhazqENZiIRy1MotIyd4xiB+EZRvnplumqCLDgsyBv5mGDZavElUohuhtW8S/x8B5fWd3WyW7vHOvFeC4gPGuFY+HQIDAQAB";
    public static final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANwtGhsbF5TVMiCmlk75xJv8Y14MDX4vQuhALHUWyLayGNdlfAE01kjEZ/Vje8xdjOYJOYQIa3frSCZS2m8aFrOoQ1mIhHLUyi0jJ3jGIH4RlG+emW6aoIsOCzIG/mYYNlq8SVSiG6G1bxL/HwHl9Z3dbJbu8c68V4LiA8a4Vj4dAgMBAAECgYEAinz7/KyvynfHDe56gLZtykeYIOGn4wTLKtaioAtyuCeRpHo6429ekWqokRrAFa2KIG8Q9FFvd4BQY3qd1mAsgi2AJc5fwLq4zNVHOcVOiqus2V1PHe3qdKq53S3Vsqf7Lfo5r6z/eAKGfOXXS6GuDNbXR2ZPHAPGj4IexH+ZZ0ECQQD5VQ+CCpstYoXP0VMSw/HsP9VzL2R/XwzG9DayzagwF8fz/oT9NFWqVv49cLCG4nfiJ+KaTg9xCwTtr2ZIAy/FAkEA4hBwBDsGWi1Ece2PSNDqbcuCmCpjjYjx8WWndGqRoSMR4rZ8SRvHsKHQ8DUFoyEE1oPmEKimvuGBfXNmP9KieQJBAJBuockm87VauUEldQYN0/qTD8bl0UWnffNSTM1PLPqPDBlfexJiWq2OL0DQIrJARkoP6MiSJ4fIZlZ5oLpefjECQGbVe18xSm5y8ROyMN+6ySkwzjNR9P9JMGXTDB2U7LIUNeqfgGHxVSEwOs9KD0pplr3OOjWRwmhKRJFAkV6u/mECQELmmj2loIR2CfQy2wuSP5U4ltgLGncbHs64WgzfkJQeMDBGnWMo4YaTqCqleKfRSblS2TXHGhQIk65q0WUAiBs=";

    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 1024;
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
    public static void main(String[] args) {
        try {
//            genKeyPair();
            String tartString = "hushawen";
            String end = encrypt(tartString,publicKey);
            System.out.println("encode:" + end);
            System.out.println("decode:" + decrypt(end,privateKey));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        // 将公钥和私钥保存到Map
        //0表示公钥
        keyMap.put(0, publicKeyString);
        //1表示私钥
        keyMap.put(1, privateKeyString);
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str);
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
