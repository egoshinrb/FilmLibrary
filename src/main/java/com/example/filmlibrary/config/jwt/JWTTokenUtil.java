package com.example.filmlibrary.config.jwt;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.core.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/*
       l123lsd7ti716t2oe8.glhudgqs786ret9i7o1y2f3.ivykdkfay6rdfi
       Header = l123lsd7ti716t2oe8 => {"alg":"HS256", "typ":"JWT"}
       Payload (JWT-Claims - заявки) = glhudgqs786ret9i7o1y2f3 => {"user_id":1, "login":"user", "role":"ROLE_USER", ....}
       Signature = ivykdkfay6rdfi
 */

@Component
@Slf4j
public class JWTTokenUtil {

    //7 * 24 * 60 * 60 * 1000 = 1 неделя в миллисекундах (время жизни токена)
    public static final long JWT_TOKEN_VALIDITY = 604800000; // 1 неделя в миллисекундах
    public final String secret = "l123lsd7TI716t2_oe";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String generateToken(final UserDetails payload) {
        return Jwts.builder()
                .setSubject(payload.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //подтверждение токена (Валидирование токена)
    public Boolean validateToken(final String token,
                                 UserDetails userDetails) {
        final String userName = getUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    //проверка на то, что токен истек или нет
    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    //нужно достать expirationDate из токена
    private Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }


    //Достаем username из токена (из Claims)
    public String getUsernameFromToken(final String token) {
        return getStringValueFromTokenByKey(token, "username");
    }

    public String getRoleFromToken(final String token) {
        return getStringValueFromTokenByKey(token, "user_role");
    }

    private String getStringValueFromTokenByKey(final String token, final String key) {
        String claim = getClaimsFromToken(token, Claims::getSubject);
        JsonNode claimJSON = null;
        try {
            claimJSON = objectMapper.readTree(claim);
        } catch (JsonProcessingException e) {
            log.error("JWTTokenUtil#getUsernameFromToken(): {}", e.getMessage());
        }

        if (claimJSON != null) {
            return claimJSON.get(key).asText();
        } else {
            return null;
        }
    }

    private <T> T getClaimsFromToken(final String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //для получения любой информации из токена, нужно предъявить секретный ключ
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
