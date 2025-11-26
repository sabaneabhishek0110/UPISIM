package com.example.security;

import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
public class KeyManager {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;
    private final String kid;

    public KeyManager() throws Exception {
        this.privateKey = KeyUtils.loadPrivateKey("keys/private.pem"); // resource path
        this.publicKey = KeyUtils.loadPublicKey("keys/public.pem");
        this.kid = KeyUtils.computeKid(publicKey);
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public String getKid() {
        return kid;
    }
}