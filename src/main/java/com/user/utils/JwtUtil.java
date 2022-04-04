package com.user.utils;

import java.util.Date;
import java.util.Map;

import org.springframework.core.env.Environment;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

	public static String generateToken(Map<String, Object> claims, String subject, Environment environment) {
		String secret = environment.getProperty("jwt.secret");
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + 10 * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public static String getUser(String token, Environment environment) {
		String secret = environment.getProperty("jwt.secret");
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token.split(" ")[1]).getBody();
		return claims.getSubject();
	}
}
