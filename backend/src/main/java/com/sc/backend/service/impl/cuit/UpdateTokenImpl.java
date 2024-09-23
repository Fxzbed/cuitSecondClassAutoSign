package com.sc.backend.service.impl.cuit;

import com.sc.backend.mapper.UserMapper;
import com.sc.backend.service.cuit.UpdateTokenService;
import com.sc.backend.utils.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateTokenImpl implements UpdateTokenService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpRequestUtil httpRequestUtil;

    public String RSAtoURL(String password) {
//        try {
//            String publicKeyPEM = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCK4n2xrbtnRyBqMJ2iiDeDRdJ/F8EVmzcjSGy/vVNfEVahl6sQOjQXZTc8AEbiZdyLnP9QwX3ZkIsEGUz1VMaPUJeHLHQC5uVljRWR0ORt4oiU7mtN5ZsEl8gPQBzSbC7IpnXVRN1Mx7s/RlFsWZgkuZKbPjxcfgoA9zXyhmcHywIDAQAB";
////            byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s", ""));
//            KeyFactory keyPair = KeyFactory.getInstance("RSA");
//
//            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(Cipher.ENCRYPT_MODE, publicKeyPEM)
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyPEM.getBytes());
//
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PublicKey publicKey = keyFactory.generatePublic(keySpec);
//
//            // 加密
//            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//
//            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
//
//            // Base64编码
//            String encryptedValue = Base64.getEncoder().encodeToString(encryptedBytes);
//
//            // URL编码
//            try {
//                return URLEncoder.encode(encryptedValue, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                // UTF-8是Java平台的标准字符集，这里不会抛出异常
//                e.printStackTrace();
//            }
//        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException |
//                 InvalidKeySpecException | BadPaddingException | InvalidKeyException e) {
//            throw new RuntimeException(e);
//        }
        return "";
    }

    @Override
    public String updateToken(String sc_password) {
        Map<String, String> map = new HashMap<>();
        return httpRequestUtil.GetToken(sc_password);
    }
}
