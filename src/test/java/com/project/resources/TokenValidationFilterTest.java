package com.project.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.project.filter.TokenValidationFilter;
import com.project.util.JWTHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TokenValidationFilterTest {

    private TokenValidationFilter filter;

    @Mock
    private FilterChain mockFilterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new TokenValidationFilter();
    }

    @Test
    void testDoFilter_bypassAuthCheck() throws IOException, ServletException {
        // Create a mock HTTP request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Set the "token-check" header to false to bypass token validation
        request.addHeader("token-check", "false");

        // Call the filter
        filter.doFilter(request, response, mockFilterChain);

        // Verify that the filter chain continued
        verify(mockFilterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilter_invalidToken() throws IOException, ServletException {
        // Create a mock HTTP request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Set an invalid Bearer token in the Authorization header
        request.addHeader("Authorization", "Bearer invalid-token");
        request.setRequestURI("/api/resources");

        mockStatic(JWTHelper.class);
        when(JWTHelper.verifyToken("invalid-token")).thenReturn(false);

        // Call the filter
        filter.doFilter(request, response, mockFilterChain);

        // Verify that the filter chain did not continue and returned a 403
        assertEquals(403, response.getStatus());
        verify(mockFilterChain, times(0)).doFilter(request, response);
    }

    @Test
    void testDoFilter_noToken() throws IOException, ServletException {
        // Create a mock HTTP request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // No Authorization header is provided
        request.setRequestURI("/api/resources");

        // Call the filter
        filter.doFilter(request, response, mockFilterChain);

        // Verify that the filter chain did not continue and returned a 403
        assertEquals(403, response.getStatus());
        verify(mockFilterChain, times(0)).doFilter(request, response);
    }
}
