package com.sduwh.match.service.rsa;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

/**
 * Use public-key to encode and private-key to decode.
 */
public interface RSAService {

    /**
     * Create a key pair contains public-key and private-key.
     *
     * @return public-key and private-key in Base64
     */
    RSAKeyPair rsaCreateKeyPair();

    /**
     * Decode encoded string via private-key.
     *
     * @param encodedString encoded string, e.g. password
     * @param privateKeyByte private-key
     * @return decoded string
     */
    String rsaDecode(String encodedString, final PrivateKey privateKeyByte) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException;


    class RSAKeyPair {
        public String publicKeyStr;
        public PrivateKey privateKey;

        public RSAKeyPair(String publicKeyStr, PrivateKey privateKey) {
            this.publicKeyStr = publicKeyStr;
            this.privateKey = privateKey;
        }
    }

}
