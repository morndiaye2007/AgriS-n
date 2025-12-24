package com.agri.sen.services;

import com.agri.sen.model.AuthResponse;
import com.agri.sen.model.LoginRequest;
import com.agri.sen.model.UtilisateurDTO;

public interface AuthService {
    AuthResponse register(UtilisateurDTO utilisateurDTO);
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(String refreshToken);
}