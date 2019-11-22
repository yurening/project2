/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/21
 * Time: 15:40
 */
package com.cskaoyan.utils;

import com.cskaoyan.utils.rsa.RSAUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthUtils {

    private static Map<String, Object> keyPair;
    private static String publicKey;
    private static String privateKey;

    static {
        try {
            keyPair = RSAUtil.genKeyPair();
            publicKey = RSAUtil.getPublicKey(keyPair);
            privateKey = RSAUtil.getPrivateKey(keyPair);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加密
    public static String  encrypt(String param) throws Exception {
        byte[] encodedData = RSAUtil.encryptByPrivateKey(param.getBytes(), privateKey);
        return new String(encodedData);
    }

    public static String  decrypt(String param) throws Exception {
        byte[] decodedData = RSAUtil.decryptByPublicKey(param.getBytes(), publicKey);
        return new String(decodedData);
    }
}
