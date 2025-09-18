package com.project.config.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.domain.Member;
import com.project.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Member member = mapper.readValue(request.getInputStream(), Member.class);
			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(),
					member.getPassword());
			return authenticationManager.authenticate(authToken);
		} catch (Exception e) {
			log.info(e.getMessage());
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal();
		System.out.println("auth:" + user);
		String accessToken = JWTUtil.getJWT(user.getUsername());
		response.addHeader(HttpHeaders.AUTHORIZATION, accessToken);
		String refreshToken = JWTUtil.getRefreshJWT(user.getUsername());
		Cookie cookie = new Cookie("refresh_token", refreshToken);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 7);
		response.addCookie(cookie);
		response.setStatus(HttpStatus.OK.value());

	}
}
