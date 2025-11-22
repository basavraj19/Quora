package com.example.Quora.Config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Quora.Utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		if (request.getServletPath().equals("/user/signUp") || request.getServletPath().equals("/user/login")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("Cookies Not Found");
			return;
		}

		String token = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("jwtToken")) {
				token = cookie.getValue();
			}
		}

		if (token != null && !jwtUtils.isTokenExpired(token)) {
			String username = jwtUtils.extractUserName(token);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
					Collections.emptyList());

			SecurityContextHolder.getContext().setAuthentication(authToken);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("application/json");
			response.getWriter().write("Invalid Token!");
			return;
		}

		filterChain.doFilter(request, response);
	}

}
