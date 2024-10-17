package com.project.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.project.util.JWTHelper;

@Component
@Order(1)
public class TokenValidationFilter implements Filter {

	// Different scopes for validation
	private String internal_scope = "com.example.internal.apis";
	private String external_scope = "com.example.external.apis";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Extract the HTTP request and response objects
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String requestURI = httpRequest.getRequestURI();

		// For testing or development purposes, bypass token check if tokencheck:false
		// header is present
		String tokenCheckHeader = httpRequest.getHeader("token-check");
		if (tokenCheckHeader != null && tokenCheckHeader.equalsIgnoreCase("false")) {
			chain.doFilter(request, response);
			return;
		}

		// Exclude certain endpoints from token validation
		if (!requestURI.startsWith("/api/resources") && !requestURI.equals("/api/users")) {
			chain.doFilter(request, response);
			return;
		}

		// Validate JWT token for protected endpoints
		String authorizationHeader = httpRequest.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.length() > 7
				&& authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			if (JWTHelper.verifyToken(token)) {
				String tokenScopes = JWTHelper.getScopes(token);
				if (tokenScopes.contains(internal_scope) || tokenScopes.contains(external_scope)) {
					chain.doFilter(request, response);
					return;
				}
			}
		}

		// If validation fails, send a 403 Forbidden response
		httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization failed.");
	}
}
