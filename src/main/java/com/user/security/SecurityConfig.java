package com.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private Environment environment;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String secret = environment.getProperty("jwt.secret");
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/swagger-ui.html").permitAll()
				.antMatchers("/docs/**").permitAll().antMatchers("/i1/**").permitAll().antMatchers("/h2console/**")
				.permitAll().antMatchers("/webjars/**").permitAll().antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/v1/myself/login").permitAll().antMatchers("/csrf").permitAll().antMatchers("/")
				.permitAll().anyRequest().authenticated().and()
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), secret)).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
}