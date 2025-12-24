package com.agri.sen.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Vérifier si le header Authorization existe et commence par "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraire le token JWT
        jwt = authHeader.substring(7);

        try {
            // Extraire l'email du token
            userEmail = jwtService.extractUsername(jwt);

            // Si l'email existe et l'utilisateur n'est pas déjà authentifié
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Charger les détails de l'utilisateur
                var userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Vérifier si le token est valide
                if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {

                    // Créer l'objet d'authentification
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // Mettre à jour le contexte de sécurité
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Log l'erreur et continupackage com.agri.sen.security;
            //
            //import jakarta.servlet.FilterChain;
            //import jakarta.servlet.ServletException;
            //import jakarta.servlet.http.HttpServletRequest;
            //import jakarta.servlet.http.HttpServletResponse;
            //import lombok.RequiredArgsConstructor;
            //import org.springframework.lang.NonNull;
            //import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
            //import org.springframework.security.core.context.SecurityContextHolder;
            //import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
            //import org.springframework.stereotype.Component;
            //import org.springframework.web.filter.OncePerRequestFilter;
            //
            //import java.io.IOException;
            //
            //@Component
            //@RequiredArgsConstructor
            //public class JwtAuthenticationFilter extends OncePerRequestFilter {
            //
            //    private final JwtService jwtService;
            //    private final CustomUserDetailsService userDetailsService;
            //
            //    @Override
            //    protected void doFilterInternal(
            //            @NonNull HttpServletRequest request,
            //            @NonNull HttpServletResponse response,
            //            @NonNull FilterChain filterChain
            //    ) throws ServletException, IOException {
            //
            //        final String authHeader = request.getHeader("Authorization");
            //        final String jwt;
            //        final String userEmail;
            //
            //        // Vérifier si le header Authorization existe et commence par "Bearer "
            //        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            //            filterChain.doFilter(request, response);
            //            return;
            //        }
            //
            //        // Extraire le token JWT
            //        jwt = authHeader.substring(7);
            //
            //        try {
            //            // Extraire l'email du token
            //            userEmail = jwtService.extractUsername(jwt);
            //
            //            // Si l'email existe et l'utilisateur n'est pas déjà authentifié
            //            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //
            //                // Charger les détails de l'utilisateur
            //                var userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //
            //                // Vérifier si le token est valide
            //                if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
            //
            //                    // Créer l'objet d'authentification
            //                    UsernamePasswordAuthenticationToken authToken =
            //                            new UsernamePasswordAuthenticationToken(
            //                                    userDetails,
            //                                    null,
            //                                    userDetails.getAuthorities()
            //                            );
            //
            //                    authToken.setDetails(
            //                            new WebAuthenticationDetailsSource().buildDetails(request)
            //                    );
            //
            //                    // Mettre à jour le contexte de sécurité
            //                    SecurityContextHolder.getContext().setAuthentication(authToken);
            //                }
            //            }
            //        } catch (Exception e) {
            //            // Log l'erreur et continuer
            //            logger.error("Cannot set user authentication: {}", e);
            //        }
            //
            //        filterChain.doFilter(request, response);
            //    }
            //}er
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }
}