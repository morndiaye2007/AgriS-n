package com.agri.sen.controller;

import com.agri.sen.model.AuthResponse;
import com.agri.sen.model.LoginRequest;
import com.agri.sen.model.Response;
import com.agri.sen.model.UtilisateurDTO;
import com.agri.sen.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "409", description = "Email or phone already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> register(@Valid @RequestBody UtilisateurDTO utilisateurDTO) {
        try {
            AuthResponse authResponse = authService.register(utilisateurDTO);
            return Response.created()
                    .setPayload(authResponse)
                    .setMessage("Inscription réussie");
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("Email")) {
                return Response.duplicateEmail().setMessage(ex.getMessage());
            }
            if (ex.getMessage().contains("téléphone")) {
                return Response.duplicateTelephone().setMessage(ex.getMessage());
            }
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Login", description = "Login with email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Wrong credentials"),
            @ApiResponse(responseCode = "403", description = "Account disabled"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = authService.login(loginRequest);
            return Response.ok()
                    .setPayload(authResponse)
                    .setMessage("Connexion réussie");
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("désactivé")) {
                return Response.disabledAccount().setMessage(ex.getMessage());
            }
            return Response.wrongCredentials()
                    .setMessage("Email ou mot de passe incorrect");
        }
    }

    @Operation(summary = "Refresh token", description = "Get a new access token using refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> refreshToken(@RequestParam String refreshToken) {
        try {
            AuthResponse authResponse = authService.refreshToken(refreshToken);
            return Response.ok()
                    .setPayload(authResponse)
                    .setMessage("Token rafraîchi");
        } catch (RuntimeException ex) {
            return Response.invalidToken().setMessage(ex.getMessage());
        }
    }
}