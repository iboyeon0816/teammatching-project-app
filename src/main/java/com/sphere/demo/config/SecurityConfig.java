package com.sphere.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sphere.demo.auth.bearer.BearerAuthEntryPoint;
import com.sphere.demo.auth.bearer.BearerAuthFilter;
import com.sphere.demo.auth.jwt.JwtProperties;
import com.sphere.demo.auth.jwt.JwtUtils;
import com.sphere.demo.auth.login.LoginAuthFilter;
import com.sphere.demo.auth.login.LoginAuthProvider;
import com.sphere.demo.auth.login.handler.LoginFailureHandler;
import com.sphere.demo.auth.login.handler.LoginSuccessHandler;
import com.sphere.demo.service.user.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers(HttpMethod.POST, "/community").authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/community/{communityId}").authenticated()
                        .requestMatchers("/projects/**").authenticated()
                        .requestMatchers("/resumes/**").authenticated()
//                        .requestMatchers("/users").permitAll()
//                        .requestMatchers("/users/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(loginAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(bearerAuthFilter(), BasicAuthenticationFilter.class)
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(new BearerAuthEntryPoint(objectMapper)))
                .build();
    }

    @Bean
    public LoginAuthFilter loginAuthFilter() throws Exception {
        LoginAuthFilter filter = new LoginAuthFilter(objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler(objectMapper, jwtUtils, refreshTokenService));
        filter.setAuthenticationFailureHandler(new LoginFailureHandler(objectMapper));
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new LoginAuthProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public BearerAuthFilter bearerAuthFilter() {
        return new BearerAuthFilter(jwtUtils);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
