package com.ms.tourist_app.application.utils;

import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {

    private final String SECRET_KEY = "1231231";

    private final Integer TIME_EXPIRATION = 864000;

    private final UserRepository userRepository;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String extractUsername(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public Date extractExpiration(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claim = new HashMap<>();
        User user = userRepository.findByEmail(userDetails.getUsername());
        claim.put(AppStr.AuthorityConstant.claimUUID, userDetails.getUsername());
        claim.put(AppStr.AuthorityConstant.claimId,user.getId());
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setClaims(claim)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TIME_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
}
