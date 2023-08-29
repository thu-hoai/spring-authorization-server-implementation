package com.me.authserver.utils;

import com.nimbusds.jose.jwk.RSAKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

public final class JwkUtils {

  private JwkUtils() {}

  private static final Logger LOGGER = LoggerFactory.getLogger(JwkUtils.class);

  public static RSAKey generateRsa() {
    KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    return new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
  }
}
