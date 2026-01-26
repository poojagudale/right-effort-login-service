package com.example.MyProject.config;

import com.example.MyProject.model.User;
import com.example.MyProject.repository.UserRepository;
import com.example.MyProject.service.CustomOAuth2UserService;
import com.example.MyProject.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(authenticationProvider());
        return builder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/oauth2/**", "/auth/**").permitAll()
                        .requestMatchers("/api/users/add").permitAll() // ✅ Allow public access to POST /api/users/add
                        .requestMatchers("/api/user/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler((request, response, authentication) -> {

                            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

                            String email = oauthUser.getAttribute("email");
                            String name = oauthUser.getAttribute("name");
                            String providerId = oauthUser.getAttribute("sub");
                            String picture = oauthUser.getAttribute("picture");

                            if (email == null) {
                                response.sendRedirect("http://localhost:3000/login?error=email");
                                return;
                            }

                            User user = userRepository.findByEmail(email).orElseGet(User::new);

                            user.setEmail(email);
                            user.setName(name);
                            user.setRole("ROLE_USER");
                            user.setProvider("GOOGLE");
                            user.setProviderId(providerId);
                            user.setPicture(picture);

                            userRepository.save(user);

                            response.sendRedirect("http://localhost:3000/google/success");
                        })
                )
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}