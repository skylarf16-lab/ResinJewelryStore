package com.example.resinjewelrystore.security;

import com.example.resinjewelrystore.security.filters.CustomAuthenticationFilter;
import com.example.resinjewelrystore.security.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManagerBuilder authManagerBuilder;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authManagerBuilder.getOrBuild());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(requests -> requests
                        // Public endpoints
                        .requestMatchers("/api/login/**").permitAll()
                        .requestMatchers(GET, "/api/products/**").permitAll() // anyone can view products

                        // Cart endpoints - must be logged in
                        .requestMatchers("/api/carts/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        // Customer endpoints - must be logged in
                        .requestMatchers("/api/customers/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        // Orders
                        .requestMatchers(GET, "/api/orders/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers(POST, "/api/orders").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") // checkout
                        .requestMatchers(PUT, "/api/orders/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(DELETE, "/api/orders/**").hasAnyAuthority("ROLE_ADMIN")

                        // User & role management
                        .requestMatchers(GET, "/api/users").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers(POST, "/api/users").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(POST, "/api/roles").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(POST, "/api/roles/add-to-user").hasAnyAuthority("ROLE_ADMIN")

                        // Any other requests need authentication
                        .anyRequest().authenticated()
                );

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}