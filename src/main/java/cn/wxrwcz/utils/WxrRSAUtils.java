package cn.wxrwcz.utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * Author:chengkang ren
 * Date:2017/9/21 15:51
 */
public class WxrRSAUtils {
    /** *//**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** *//**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** *//**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** *//**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /** *//**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return String 加密后的字符串
     * @throws Exception 异常
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /** *//**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param privateKey 私钥(WxrBase64编码)
     *
     * @return String 加密后的字符串
     * @throws Exception 异常
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = WxrBase64.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return WxrBase64.encode(signature.sign());
    }

    /** *//**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param publicKey 公钥(WxrBase64编码)
     * @param sign 数字签名
     *
     * @return String 加密后的字符串
     * @throws Exception 异常
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = WxrBase64.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(WxrBase64.decode(sign));
    }

    /** *//**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(WxrBase64编码)
     * @return String 加密后的字符串
     * @throws Exception 异常
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = WxrBase64.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        return commonMethod(encryptedData,cipher,MAX_DECRYPT_BLOCK);
    }

    /** *//**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(WxrBase64编码)
     * @return String 加密后的字符串
     * @throws Exception 异常
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = WxrBase64.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        return commonMethod(encryptedData,cipher,MAX_DECRYPT_BLOCK);
    }

    /** *//**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @param publicKey 公钥(WxrBase64编码)
     * @return String 加密后的字符串
     * @throws Exception 异常
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = WxrBase64.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        return commonMethod(data,cipher,MAX_ENCRYPT_BLOCK);
    }

    /** *//**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data 源数据
     * @param privateKey 私钥(WxrBase64编码)
     * @return String 加密后的字符串
     * @throws Exception 异常
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = WxrBase64.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        return commonMethod(data,cipher,MAX_ENCRYPT_BLOCK);
    }

    /** *//**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return String 加密后的字符串
     * @throws Exception 异常
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return WxrBase64.encode(key.getEncoded());
    }

    /** *//**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return String 加密后的字符串
     * @throws Exception 异常
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return WxrBase64.encode(key.getEncoded());
    }
    public static byte[] commonMethod(byte[] encryptedData,Cipher cipher,int block)throws Exception{
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > block) {
                cache = cipher.doFinal(encryptedData, offSet, block);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * block;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static void main(String[] args) throws Exception {
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKeukC1mT7JpS9AOvw+RlQ5eTFWlFnJ6e+opzPty0Gn5Fv/aJKlM7TEGJ5NrB15wDim4v/lCWrTmi/79RSQdLG865vLamoM9vEVCP6b90j+9QLm805P8EtQcPDaolfwTvwjWGWBfrcLa+f2XNyTdE/Hznw+1MNs8gZlVXnpx1+HvAgMBAAECgYA+nRiJW2L3JAqDLn55ucsHCn9Gm9a9+K68yc8ZNc5NRA4qi7KU5XzMiZfNnHGr+fvP6IUG9pgYZN5x4PEogxxL8F/xsKKNdb8jUOkEz5pzzPkLlgp37pU6xPFic1F+T8KS5IR5WEJOShHEhdcLcitfYJrBAmYbvr3+xSBVMT17QQJBAO4UTP32r5WSGLFZSqYajRwNmsbLt1lOWo7YTI19hd21kmHiX7IrCRNScvVo2ZhSdx/AK3cW0TntkKYKK1O9Mw0CQQC0TbsHXCBlKqpDnGeiSnMzWdzGGR9oTLQxyvCq/yvMv7TPhkbs/cLwk7jqU1f585eof439loB19w/u24YVdNnrAkBcULrZ7wF+ebvhaMItTjZRHmVZmDxsAJnUscJdSsLRiUpNcj2xFEbcmyfASPu2uKNASH+SGeLfF2LzPxY1zjMxAkAzpojLZPLKyFwaHjX54cX6keWSwiOjJ0X/dZlYAt0zPD+5q0eqIE+1xzuTtOhg7A0n8dJlMLYAFRR1YmGYx+6dAkBkG7ghj0/JmqaSVsFpt0aY2TjEwsw+YNDgtYmhWKwRoqDv9TESfKF1wMuQCLv7VeUsvW1GtLojDJhyXhwYEJst";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnrpAtZk+yaUvQDr8PkZUOXkxVpRZyenvqKcz7ctBp+Rb/2iSpTO0xBieTawdecA4puL/5Qlq05ov+/UUkHSxvOuby2pqDPbxFQj+m/dI/vUC5vNOT/BLUHDw2qJX8E78I1hlgX63C2vn9lzck3RPx858PtTDbPIGZVV56cdfh7wIDAQAB";
//        String str = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnrpAtZk+yaUvQDr8PkZUOXkxVpRZyenvqKcz7ctBp+Rb/2iSpTO0xBieTawdecA4puL/5Qlq05ov+/UUkHSxvOuby2pqDPbxFQj+m/dI/vUC5vNOT/BLUHDw2qJX8E78I1hlgX63C2vn9lzck3RPx858PtTDbPIGZVV56cdfh7wIDAQAB";
        String str = "ptBOva6m2mkZStQTMOvfwP+2StUhZ4OloJBj8VeerQj/uv/zgI3OGxQQrbUkRBoEVQToZVT08LxD2Oc3Xi0BW+RrNaMxpT0flx6r+F26oWf7SSu3nDW45yFClstegsNxhQXpCPOxGEEQfZA2Sh4DndEhovd3ao3I1PyqFGtZ738=";
        String encryptStr = "{\"mobile\":\"18334771324\",\"money\":\"2.00\"}";
        str = new String(WxrRSAUtils.decryptByPublicKey(WxrBase64.decode(str), publicKey));
        System.out.println(str);
//        str = new String(RSAUtils.decryptByPrivateKey(WxrBase64.decode(str),privateKey));
//        str = new String(RSAUtils.decryptByPrivateKey(WxrBase64.decode(str),privateKey));
//        String encryptData = WxrBase64.encode(RSAUtils.encryptByPrivateKey(encryptSt
    }
}
