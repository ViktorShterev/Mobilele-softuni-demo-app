package org.softuni.mobilelele.service.impl;


import org.softuni.mobilelele.model.entity.UserRole;
import org.softuni.mobilelele.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MobileleUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public MobileleUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .map(MobileleUserDetailsServiceImpl::map)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + email + " not found"));
    }

    private static UserDetails map(org.softuni.mobilelele.model.entity.User user) {
         return User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(MobileleUserDetailsServiceImpl::mapped).toList())
                .build();
    }

    private static GrantedAuthority mapped(UserRole userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole.getRole().name());
    }
}
