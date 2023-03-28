package com.ecomerce.Ecomerce.security;

import com.ecomerce.Ecomerce.V1.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;
    @Value("${security.jwt.key}")
    private String key;

    public String generateToken(Account account){
        long expString = Long.parseLong(expiration);
        LocalDateTime dateTimeExpiration = LocalDateTime.now().plusMinutes(expString);
        Date date = Date.from(dateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts
                .builder()
                .setSubject(account.getEmail())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValid(String token){
        try{
            Claims claims = getClaims(token);
            Date dateExpiration = claims.getExpiration();
            LocalDateTime localDateTime = dateExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(localDateTime);
        }catch (Exception e){
            return false;
        }
    }

    public String getEmailAccount(String token) throws ExpiredJwtException{
        return getClaims(token).getSubject();
    }

//    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(EComerceApplication.class);
//        JwtService service = context.getBean(JwtService.class);
//        Account a = Account.builder().email("rafadot").build();
//        String token = service.generateToken(a);
//        System.out.println(token);
//        System.out.println(service.tokenValid(token));
//        System.out.println(service.getLoginAccount(token));
//    }
}
