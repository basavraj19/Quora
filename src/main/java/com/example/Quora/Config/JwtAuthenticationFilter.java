package com.example.Quora.Config;

import java.io.IOException;

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

		// if (request.getServletPath().equals("/user/signUp") ||
		// request.getServletPath().equals("/user/login")) {
		// filterChain.doFilter(request, response);
		// return;
		// }

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwtToken".equals(cookie.getName())) {
					String token = cookie.getValue();

					if (!jwtUtils.isTokenExpired(token)) {
						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
								jwtUtils.extractUserName(token), null, jwtUtils.extractRoles(token));

						SecurityContextHolder.getContext().setAuthentication(auth);
					}
					break;
				}
			}
		}

		filterChain.doFilter(request, response);

	}
}
