package com.sustech.football.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

public class WXBizDataCrypt {

    private String appId;
    private String sessionKey;

    public WXBizDataCrypt(String appId, String sessionKey) {
        this.appId = appId;
        this.sessionKey = sessionKey;
    }

    public JSONObject decryptData(String encryptedData, String iv) throws Exception {
        // Base64解码sessionKey和iv
        byte[] sessionKeyByte = Base64.decodeBase64(sessionKey);
        byte[] ivByte = Base64.decodeBase64(iv);

        // Base64解码数据
        byte[] encryptedDataByte = Base64.decodeBase64(encryptedData);

        // 初始化
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(sessionKeyByte, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivByte);

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedDataByte);

        // 将解密后的数据转换为字符串
        String decryptedString = new String(decryptedBytes, "UTF-8");

        // 转换为JSON对象
        JSONObject decryptedData = new JSONObject(decryptedString);

        // 验证appId
        if (!decryptedData.getJSONObject("watermark").getString("appid").equals(this.appId)) {
            throw new Exception("Illegal Buffer");
        }

        return decryptedData;
    }

    // 测试方法
    public static void main(String[] args) {
        // 使用正确的参数替换这些值
        String appId = "yourAppId";
        String sessionKey = "yourSessionKey";
        String encryptedData = "yourEncryptedData";
        String iv = "yourIv";

        WXBizDataCrypt crypt = new WXBizDataCrypt(appId, sessionKey);
        try {
            JSONObject decryptedData = crypt.decryptData(encryptedData, iv);
            System.out.println(decryptedData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
