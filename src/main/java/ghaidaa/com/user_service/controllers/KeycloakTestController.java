package ghaidaa.com.user_service.controllers;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class KeycloakTestController {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakTestController.class);
    private final Keycloak keycloak;

    public KeycloakTestController(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @GetMapping("/keycloak-connection")
    public ResponseEntity<String> testKeycloakConnection() {
        try {
            logger.info("Testing Keycloak connection...");

            // Test 1: Try to access realm
            RealmResource realmResource = keycloak.realm("government");
            RealmRepresentation realm = realmResource.toRepresentation();
            logger.info("Realm accessed successfully: {}", realm.getRealm());

            return ResponseEntity.ok("Keycloak connection successful! Realm: " + realm.getRealm());

        } catch (Exception e) {
            logger.error("Keycloak connection failed", e);
            return ResponseEntity.status(500)
                    .body("Keycloak connection failed: " + e.getMessage());
        }
    }

    @GetMapping("/keycloak-admin-auth")
    public ResponseEntity<String> testAdminAuth() {
        try {
            logger.info("Testing admin authentication...");

            // Try to get access token (this will test auth)
            String token = keycloak.tokenManager().getAccessToken().getToken();
            logger.info("Admin authentication successful, token obtained");

            return ResponseEntity.ok("Admin authentication successful! Token length: " + token.length());

        } catch (Exception e) {
            logger.error("Admin authentication failed", e);
            return ResponseEntity.status(500)
                    .body("Admin authentication failed: " + e.getMessage());
        }
    }
}