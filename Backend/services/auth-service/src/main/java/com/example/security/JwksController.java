package com.example.security;

import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwksController {
    private final KeyManager keyManager;

    public JwksController(KeyManager keyManager){
        this.keyManager = keyManager;
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String,Object> getJwks(){
        var jwk = KeyUtils.publicKeyToJwk(keyManager.getPublicKey(),keyManager.getKid());
        return Map.of("keys",List.of(jwk));
    }
}
