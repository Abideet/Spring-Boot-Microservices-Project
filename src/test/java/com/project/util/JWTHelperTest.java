package com.project.util;

import com.auth0.jwt.interfaces.Claim;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import com.project.util.JWTHelper;

public class JWTHelperTest {

    @Test
    public void testCreateToken() {
        String scopes = "read write";
        String token = JWTHelper.createToken(scopes);

        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
    }

    @Test
    public void testVerifyValidToken() {
        String token = JWTHelper.createToken("read");
        assertTrue(JWTHelper.verifyToken(token), "Token should be valid");
    }

    @Test
    public void testVerifyInvalidToken() {
        String invalidToken = "invalid.token.here";
        assertFalse(JWTHelper.verifyToken(invalidToken), "Token should be invalid");
    }

    @Test
    public void testGetClaimsFromValidToken() {
        String token = JWTHelper.createToken("read write");
        Map<String, Claim> claims = JWTHelper.getClaims(token);

        assertNotNull(claims, "Claims should not be null");
        assertTrue(claims.containsKey("scopes"), "Claims should contain 'scopes'");
        assertEquals("read write", claims.get("scopes").asString(), "The scopes claim should match");
    }

    @Test
    public void testGetClaimsFromInvalidToken() {
        String invalidToken = "invalid.token.here";
        Map<String, Claim> claims = JWTHelper.getClaims(invalidToken);

        assertNull(claims, "Claims should be null for an invalid token");
    }

    @Test
    public void testGetScopesFromValidToken() {
        String token = JWTHelper.createToken("admin");
        String scopes = JWTHelper.getScopes(token);

        assertNotNull(scopes, "Scopes should not be null");
        assertEquals("admin", scopes, "Scopes should match 'admin'");
    }

    @Test
    public void testGetScopesFromInvalidToken() {
        String invalidToken = "invalid.token.here";
        String scopes = JWTHelper.getScopes(invalidToken);

        assertNull(scopes, "Scopes should be null for an invalid token");
    }
}
