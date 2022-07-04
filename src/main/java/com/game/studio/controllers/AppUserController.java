package com.game.studio.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.studio.dto.RoleToUserDto;
import com.game.studio.models.user.AppRole;
import com.game.studio.models.user.AppUser;
import com.game.studio.services.AppUserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping()
    public ResponseEntity<List<AppUser>> findAll() {
        return ResponseEntity.ok().body(appUserService.findAll());
    }

    @PostMapping()
    public ResponseEntity<AppUser> save(@RequestBody AppUser entity) {
        return ResponseEntity
                .created(URI.create(
                        ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/role").toUriString()))
                .body(appUserService.persist(entity));
    }

    @PostMapping("/role")
    public ResponseEntity<AppRole> saveRole(@RequestBody AppRole entity) {
        return ResponseEntity
                .created(URI.create(
                        ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/role").toUriString()))
                .body(appUserService.saveRole(entity));
    }

    @PostMapping("/role/assign")
    public ResponseEntity<?> assignRole(@RequestBody RoleToUserDto entity) {
        appUserService.addRole(entity.getUserName(), entity.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Game ")) {
            try {
                String refresh_token = authorizationHeader.substring(("Game ").length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = appUserService.findByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",
                                user.getRoles().stream().map(AppRole::getName)
                                        .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                log.error("Error Logging in: {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                // response.sendError(HttpStatus.FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }

        } else {
            throw new RuntimeException("Refresh tokrn is missing");
        }

    }
}
