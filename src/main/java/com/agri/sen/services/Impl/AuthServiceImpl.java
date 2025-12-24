package com.agri.sen.services.Impl;

import com.agri.sen.entity.Utilisateur;
import com.agri.sen.mapper.UtilisateurMapper;
import com.agri.sen.model.AuthResponse;
import com.agri.sen.model.LoginRequest;
import com.agri.sen.model.UtilisateurDTO;
import com.agri.sen.repository.UtilisateurRepository;
import com.agri.sen.security.JwtService;
import com.agri.sen.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(UtilisateurDTO utilisateurDTO) {
        // Vérifier si l'email existe déjà
        if (utilisateurRepository.existsByEmail(utilisateurDTO.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // Vérifier si le téléphone existe déjà
        if (utilisateurDTO.getTelephone() != null &&
                utilisateurRepository.existsByTelephone(utilisateurDTO.getTelephone())) {
            throw new RuntimeException("Numéro de téléphone déjà utilisé");
        }

        // Créer l'utilisateur
        var entity = utilisateurMapper.asEntity(utilisateurDTO);
        entity.setMot_de_passe(passwordEncoder.encode(utilisateurDTO.getMotDePasse()));

        var savedUser = utilisateurRepository.save(entity);

        // Générer les tokens
        String jwtToken = jwtService.generateToken(savedUser.getEmail());
        String refreshToken = jwtService.generateRefreshToken(savedUser.getEmail());

        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .type("Bearer")
                .user(utilisateurMapper.asDto(savedUser))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // Authentifier l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getMotDePasse()
                )
        );

        // Récupérer l'utilisateur
        Utilisateur user = utilisateurRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si le compte est activé
        if (!user.getEnabled()) {
            throw new RuntimeException("Compte désactivé");
        }

        // Mettre à jour la date de dernière connexion
        user.setLastLogin(LocalDateTime.now());
        utilisateurRepository.save(user);

        // Générer les tokens
        String jwtToken = jwtService.generateToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .type("Bearer")
                .user(utilisateurMapper.asDto(user))
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        // Extraire l'email du refresh token
        String email = jwtService.extractUsername(refreshToken);

        // Vérifier que l'utilisateur existe
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si le refresh token est valide
        if (!jwtService.isTokenValid(refreshToken, email)) {
            throw new RuntimeException("Refresh token invalide");
        }

        // Générer un nouveau token
        String newJwtToken = jwtService.generateToken(email);

        return AuthResponse.builder()
                .token(newJwtToken)
                .refreshToken(refreshToken)
                .type("Bearer")
                .user(utilisateurMapper.asDto(user))
                .build();
    }
}