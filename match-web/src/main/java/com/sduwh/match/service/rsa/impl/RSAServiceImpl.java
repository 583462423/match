package com.sduwh.match.service.rsa.impl;

import com.sduwh.match.service.rsa.RSAService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Service
public class RSAServiceImpl implements RSAService {

    private static final BASE64Encoder BASE_64_ENCODER = new BASE64Encoder();
    private static final BASE64Decoder BASE_64_DECODER = new BASE64Decoder();

    private static final KeyPairGenerator KEY_PAIR_GENERATOR;
    static {
        Security.addProvider(new BouncyCastleProvider());
        try {
            KEY_PAIR_GENERATOR = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new Error("RSA not spport: " + e.getMessage());
        }
    }


    @Override
    public RSAKeyPair rsaCreateKeyPair() {
        KeyPair keyPair = KEY_PAIR_GENERATOR.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKeyPair(
                BASE_64_ENCODER.encode(rsaPublicKey.getEncoded()),
                rsaPrivateKey
        );
    }

    @Override
    public String rsaDecode(final String encodedString, final PrivateKey privateKey)
            throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException
    {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(
                cipher.doFinal(
                        BASE_64_DECODER.decodeBuffer(encodedString)));
    }

}
