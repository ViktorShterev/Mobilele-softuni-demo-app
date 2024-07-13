package org.softuni.mobilelele.config;

import org.softuni.mobilelele.model.enums.RolesEnum;
import org.softuni.mobilelele.repository.UserRepository;
import org.softuni.mobilelele.service.impl.MobileleUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final String rememberMeKey;

    public SecurityConfig(@Value("${mobilele.default.remember.me.key}") String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.authorizeHttpRequests(
//                Define which urls are visible to which users
                authorizeRequests -> authorizeRequests
//                        All static resources which are situated in js, images, css are visible to anyone
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                        Permit monitoring with actuator on different port (allow actuator endpoints)
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                        Allow anyone to see the home, login and register form and page
                        .requestMatchers("/", "/users/login", "users/register", "/users/login-error").permitAll()
                        .requestMatchers("/offers/all").permitAll()
                        .requestMatchers("/api/currency/convert").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/offer/**").permitAll()
                        .requestMatchers("/brands").hasRole(RolesEnum.ADMIN.name())
//                        All other requests are authenticated
                        .anyRequest().authenticated()

        ).formLogin(
                formLogin -> {
                    formLogin
//                            Redirect here when we access something which is not allowed
//                            Also this is the page where we perform login
                            .loginPage("/users/login")
//                            The names of the input fields (in our case auth-login.html)
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureForwardUrl("/users/login-error");

                }

        ).logout(
                logout -> {
                    logout
//                            The url where we should POST something in order to perform the logout
                            .logoutUrl("/users/logout")
//                            Where to go when logged out
                            .logoutSuccessUrl("/")
//                            Invalidate the HTTP session
                            .invalidateHttpSession(true);
                }

        ).rememberMe(
                rememberMe -> {
                    rememberMe
                            .key(rememberMeKey)
                            .rememberMeParameter("rememberme")
                            .rememberMeCookieName("rememberme");
                }

        ).build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
//        This service translates the mobilele users and roles
//        to representation which spring security understands
        return new MobileleUserDetailsServiceImpl(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
