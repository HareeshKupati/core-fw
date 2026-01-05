package com.hbk.corefw.service;

import com.hbk.corefw.dto.UserDetailsDTO;
import com.hbk.corefw.exception.LatestTokenNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtService {
    public static final String SECRET_KEY = "7c73c5a83fa580b5d6f8208768adc931ef3123291ac8bc335a1277a39d256d9a";
    public static final int ONE_AND_HALF_SEC = 1500;

    @Value("${auth.access-token.validity-in-hours}")
    private Integer accessTokenValidity;

    @Value("${auth.refresh-token.validity-in-hours}")
    private Integer refreshTokenValidity;

    public enum TokenType {
        ACCESS_TOKEN, REFRESH_TOKEN;
    }


    static Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String createToken(String username, Date currentDate, TokenType tokenType) {
        Calendar expiryDate = Calendar.getInstance();
        expiryDate.setTime(currentDate);
        expiryDate.add(Calendar.HOUR, TokenType.ACCESS_TOKEN.equals(tokenType) ?
                accessTokenValidity : refreshTokenValidity);
        return Jwts.builder().setClaims(Jwts.claims()
                        .setIssuer("GOD")
                        .setIssuedAt(currentDate)
                        .setExpiration(expiryDate.getTime())
                        .setSubject(username))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean validateToken(String token, TokenType tokenType) {
        Claims claims = getClaims(token);
        Date expiryDate = getExpiryDateFromIssueDate(claims.getIssuedAt(), tokenType);
        return claims != null && expiryDate.equals(claims.getExpiration())
                && claims.getExpiration().after(new Date());
    }

    public String getSubject(String token, TokenType tokenType) {
        if (validateToken(token, tokenType)) {
            return getClaims(token).getSubject();
        }
        return null;
    }

    public Claims getClaims(String token) {
        return (Claims) Jwts.parser().setSigningKey(getSignKey()).parse(token).getBody();
    }

    private Date getExpiryDateFromIssueDate(Date issueDate, TokenType tokenType) {
        Calendar expiryDate = Calendar.getInstance();
        expiryDate.setTime(issueDate);
        expiryDate.add(Calendar.HOUR, TokenType.ACCESS_TOKEN.equals(tokenType) ?
                accessTokenValidity : refreshTokenValidity);
        return expiryDate.getTime();
    }

    public boolean isLatestToken(UserDetailsDTO userDTO, String token) {
        Claims claims = getClaims(token);
        long issuedTimeFromToken =  claims.getIssuedAt().getTime();
        long issuedTimeFromDB = userDTO.getTokenIssuedDate().getTime();
        if (claims != null && Math.abs(issuedTimeFromDB-issuedTimeFromToken) > ONE_AND_HALF_SEC)
            throw new LatestTokenNotFoundException();
        return true;
    }
}
