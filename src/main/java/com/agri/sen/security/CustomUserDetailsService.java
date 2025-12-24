package com.agri.sen.security;

import com.agri.sen.entity.Utilisateur;
import com.agri.sen.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getMot_de_passe())
                .authorities(getAuthorities(utilisateur))
                .accountExpired(false)
                .accountLocked(!utilisateur.getEnabled())
                .credentialsExpired(false)
                .disabled(!utilisateur.getEnabled())
                .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Utilisateur utilisateur) {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name())
        );
    }
}