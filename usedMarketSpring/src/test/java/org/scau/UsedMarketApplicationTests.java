package org.scau;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class UsedMarketApplicationTests {

    @Test
    void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("name", "abc");

        String token = JWT.create()
                .withClaim("user", claims)  // 有效载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // 过期时间
                .sign(Algorithm.HMAC256("secret123"));  // 加密算法及密钥

        System.out.println("令牌: " + token);
    }

    @Test
    void testParse() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJleHAiOjE3MTA3NTAwNjMsInVzZXIiOnsibmFtZSI6ImFiYyIsImlkIjoxfX0" +
                ".p8TgIhJtWOM4KJc7VEKCRM7RPgDTfES4Pf42hPQLPBQ";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("secret123")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Object> claim = decodedJWT.getClaim("user").asMap();
        System.out.println("claimName: " + claim.get("name"));
    }
}
