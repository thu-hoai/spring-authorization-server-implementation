package com.me.authserver.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

final class KeyGeneratorUtils {

  private KeyGeneratorUtils() {}

  static SecretKey generateSecretKey() {
    SecretKey hmacKey;
    try {
      hmacKey = KeyGenerator.getInstance("HmacSha256").generateKey();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    return hmacKey;
  }

  //  static KeyPair generateRsaKey() {
  //    KeyPair keyPair;
  //    try {
  //      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
  //      keyPairGenerator.initialize(2048);
  //      keyPair = keyPairGenerator.generateKeyPair();
  //    } catch (Exception ex) {
  //      throw new IllegalStateException(ex);
  //    }
  //    return keyPair;
  //  }

  public static KeyPair generateRsaKey() {
    KeyPairGenerator keyPairGenerator = null;
    try {
      keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    keyPairGenerator.initialize(2048);

    return keyPairGenerator.generateKeyPair();
  }
}
