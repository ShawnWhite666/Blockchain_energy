package com.blockchainenergy.security.rsa;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.util.Base64;
import java.util.Random;

/**
 * 该类用于生成公钥私钥，并且验证公钥私钥是否匹配
 * 使用方法：
 * 1.生成公钥私钥： 使用new RSAEncrypt()创建对象，之后调用get方法即可获取生成的公钥私钥（字符串）
 * 2.验证公钥私钥是否匹配： 调用静态方法verifyPublicAndPrivateKey()，传入公钥私钥字符串，返回boolean判断是否匹配
 */
public class RSAEncrypt {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    public RSAEncrypt() {
        genKeyPair();
    }

    /**
     * 获取私钥字符串
     *
     * @return 当前的私钥字符串
     */
    public String getPrivateKeyStr() {
        KeyFactory keyFactory = null;
        String privateKeyStr = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = keyFactory.getKeySpec(privateKey, PKCS8EncodedKeySpec.class);
            byte[] buffer = keySpec.getEncoded();
            privateKeyStr = Base64.getEncoder().encodeToString(buffer);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("无此算法");
        } catch (InvalidKeySpecException e) {
            System.err.println("私钥非法");
        }
        return privateKeyStr;
    }

    /**
     * 获取公钥字符串
     *
     * @return 当前的公钥字符串
     */
    public String getPublicKeyStr() {
        KeyFactory keyFactory = null;
        String publicKeyStr = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = keyFactory.getKeySpec(publicKey, X509EncodedKeySpec.class);
            byte[] buffer = keySpec.getEncoded();
            publicKeyStr = Base64.getEncoder().encodeToString(buffer);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("无此算法");
        } catch (InvalidKeySpecException e) {
            System.err.println("公钥非法");
        }
        return publicKeyStr;
    }

    /**
     * 随机生成公钥-私钥
     */
    private void genKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * @param publicKeyStr  公钥
     * @param privateKeyStr 私钥
     * @return boolean值判断是否匹配
     */
    public static boolean verifyPublicAndPrivateKey(String publicKeyStr, String privateKeyStr) {
        RSAPublicKey publicKeyToVerify = null;
        RSAPrivateKey privateKeyToVerify = null;
        try {
            publicKeyToVerify = loadPublicKey(publicKeyStr);
        } catch (Exception e) {
            System.err.println("加载公钥失败-公钥非法");
            return false;
        }
        try {
            privateKeyToVerify = loadPrivateKey(privateKeyStr);
        } catch (Exception e) {
            System.err.println("加载私钥失败-私钥非法");
            return false;
        }
        if (publicKeyToVerify == null || privateKeyToVerify == null) {
            return false;
        }
        //生成随机数字符串用于验证公钥私钥是否匹配
        Random seed = new Random();
        int randomNum = seed.nextInt(1000) + 1;
        String randomStr = String.valueOf(randomNum);

        byte[] cipher = null;
        byte[] plainText = null;
        try {
            cipher = encrypt(publicKeyToVerify, randomStr.getBytes());
            plainText = decrypt(privateKeyToVerify, cipher);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        String plainStr = new String(plainText);
        return plainStr.equals(randomStr);
    }


    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    private static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        RSAPublicKey loadedPublicKey = null;
        try {
            byte[] buffer = Base64.getDecoder().decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            loadedPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e);
        } catch (InvalidKeySpecException e) {
            throw new Exception(e);
        } catch (NullPointerException e) {
            throw new Exception(e);
        }
        return loadedPublicKey;
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr 私钥数据字符串
     * @throws Exception 加载私钥时产生的异常
     */
    private static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        RSAPrivateKey loadedPrivateKey = null;
        try {
            byte[] buffer = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            loadedPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
        return loadedPrivateKey;
    }

    /**
     * 加密过程
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return 加密后的数据
     * @throws Exception 加密过程中的异常信息
     */
    private static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    private static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }


    public static void main(String[] args) {
        RSAEncrypt rsaEncrypt = new RSAEncrypt();
        System.out.println("公钥：" + rsaEncrypt.getPublicKeyStr());
        System.out.println("私钥：" + rsaEncrypt.getPrivateKeyStr());
        boolean res = RSAEncrypt.verifyPublicAndPrivateKey(rsaEncrypt.getPublicKeyStr(), rsaEncrypt.getPrivateKeyStr());
        System.out.println("是否匹配：" + res);
    }
}