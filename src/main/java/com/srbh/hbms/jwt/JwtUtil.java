package com.srbh.hbms.jwt;

import com.srbh.hbms.service.jwtUserDetail.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final long serialVersionUID = 1L;

    //Read secret key from properties file
    @Value("${hbms.jwt.secretKeyHBMS}")
    private String secretKey;

    //Read expiration time from properties file
    @Value("${hbms.jwt.expirationTime}")
    private long expirationTime;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public String generateJwtToken(Authentication authentication) {

        //Fetch principal from authentication
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        //Set an empty map as a claim
        Map<String,Object> claims = new HashMap<>();

        //Getting current time of the system in milliseconds
        final long currentTime = System.currentTimeMillis();

        //Building JWT by generating claims of principal
        return Jwts.builder()
                .setClaims(claims)
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime+expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {

        //Fetch username from JWT
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            //Parse JWT
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            //If JWT is parsed and control reaches here return true
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        //If control reaches here
        //Else return false
        return false;
    }
}
