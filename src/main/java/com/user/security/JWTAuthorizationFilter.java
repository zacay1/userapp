package com.user.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.pojo.response.GenericResponse;
import com.user.utils.Constants;
import com.user.utils.Messages;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private String secret;
	private ObjectMapper mapper = new ObjectMapper();

	public JWTAuthorizationFilter(AuthenticationManager authManager, String secret) {
		super(authManager);
		this.secret = secret;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {

			String tokenHeader = request.getHeader(Constants.HEADER_AUTHORIZATION);

			if (tokenHeader == null || !tokenHeader.startsWith(Constants.TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}

			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(tokenHeader.split(" ")[1]).getBody();

			if (claims != null) {
				SecurityContextHolder.getContext().setAuthentication(
						new UsernamePasswordAuthenticationToken(claims.getSubject(), null, new ArrayList<>()));
				filterChain.doFilter(request, response);
			} else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter()
						.write(mapper.writeValueAsString(new GenericResponse(Messages.ERROR_USER_FORBIDDEN)));
				response.getWriter().flush();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
