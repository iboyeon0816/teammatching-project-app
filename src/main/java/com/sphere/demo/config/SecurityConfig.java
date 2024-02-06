package com.sphere.demo.config;

import com.sphere.demo.auth.bearer.BearerAuthEntryPoint;
import com.sphere.demo.auth.bearer.BearerAuthFilter;
import com.sphere.demo.auth.jwt.JwtProperties;
import com.sphere.demo.auth.jwt.JwtUtils;
import com.sphere.demo.auth.login.LoginAuthFilter;
import com.sphere.demo.auth.login.LoginAuthProvider;
import com.sphere.demo.service.UserAuthService;
import com.sphere.demo.auth.login.handler.LoginFailureHandler;
import com.sphere.demo.auth.login.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
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

    private final JwtUtils jwtUtils;
    private final UserAuthService userAuthService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        LoginAuthFilter loginAuthFilter = createLoginAuthFilter();
        BearerAuthFilter bearerAuthFilter = new BearerAuthFilter(jwtUtils);

        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/community").authenticated()
                        .requestMatchers(HttpMethod.POST, "/projects").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/community/{communityId}").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(loginAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(bearerAuthFilter, BasicAuthenticationFilter.class)
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(new BearerAuthEntryPoint()))
                .build();
    }

    private LoginAuthFilter createLoginAuthFilter() throws Exception {
        LoginAuthFilter loginAuthFilter = new LoginAuthFilter();
        loginAuthFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        loginAuthFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(jwtUtils, userAuthService));
        loginAuthFilter.setAuthenticationFailureHandler(new LoginFailureHandler());
        return loginAuthFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        ProviderManager authenticationManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(authenticationProvider());
        return authenticationManager;
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new LoginAuthProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
