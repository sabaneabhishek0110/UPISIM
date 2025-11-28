package com.example.security;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.math.BigInteger;
import java.util.Map;

public class KeyUtils {

    private static String readPem(String path) throws Exception {
        try (InputStream in = KeyUtils.class.getClassLoader().getResourceAsStream(path)) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public static RSAPrivateKey loadPrivateKey(String path) throws Exception {
        var pem = readPem(path)
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----END PRIVATE KEY-----","")
                .replaceAll("\\s","");
        var bytes = Base64.getDecoder().decode(pem);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(bytes));
    }

    public static RSAPublicKey loadPublicKey(String path) throws Exception {
        var pem = readPem(path)
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replaceAll("\\s","");
        var bytes = Base64.getDecoder().decode(pem);
        return (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(bytes));
    }

    private static String base64Url(BigInteger v) {
        var bytes = v.toByteArray();
        if (bytes[0] == 0) {
            var copy = new byte[bytes.length - 1];
            System.arraycopy(bytes,1,copy,0,copy.length);
            bytes = copy;
        }
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static String computeKid(RSAPublicKey key) throws Exception {
        String n = base64Url(key.getModulus().toByteArray());
        String e = base64Url(key.getPublicExponent().toByteArray());

        String canonical = "{\"e\":\"" + e + "\",\"kty\":\"RSA\",\"n\":\"" + n + "\"}";

        return base64Url(
                MessageDigest.getInstance("SHA-256")
                        .digest(canonical.getBytes(StandardCharsets.UTF_8))
        );
    }

    private static String base64Url(byte[] b) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(stripLeadingZero(b));
    }

    private static byte[] stripLeadingZero(byte[] input) {
        if (input.length > 1 && input[0] == 0) {
            return java.util.Arrays.copyOfRange(input, 1, input.length);
        }
        return input;
    }


    public static Map<String,Object> publicKeyToJwk(RSAPublicKey key, String kid) {
        return Map.of(
                "kty","RSA",
                "alg","RS256",
                "use","sig",
                "kid",kid,
                "n", base64Url(key.getModulus()),
                "e", base64Url(key.getPublicExponent())
        );
    }
}
