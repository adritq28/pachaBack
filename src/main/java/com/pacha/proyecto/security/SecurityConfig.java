package com.pacha.proyecto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

        private final UserDetailsService userDetailsService;
        private final JWTAuthorizationFilter jwtAuthorizationFilter;

        @Bean
        SecurityFilterChain FilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

                JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
                jwtAuthenticationFilter.setAuthenticationManager(authManager);
                jwtAuthenticationFilter.setFilterProcessesUrl("/login");

                return http
                                .csrf().disable()
                                .authorizeHttpRequests()
                                .requestMatchers("/usuario/**").permitAll()
                                .requestMatchers("/alertas/**").permitAll()
                                .requestMatchers("/cultivos/**").permitAll()
                                .requestMatchers("/datosHidrologica/**").permitAll()
                                .requestMatchers("/datosEstacion/**").permitAll()
                                .requestMatchers("/datos_pronostico/**").permitAll()
                                .requestMatchers("/estacion/**").permitAll()
                                .requestMatchers("/fenologia/**").permitAll()
                                .requestMatchers("/email/**").permitAll()
                                .requestMatchers("/municipio/**").permitAll()
                                .requestMatchers("/observador/**").permitAll()
                                .requestMatchers("/promotor/**").permitAll()
                                .requestMatchers("/usuario/**").permitAll()
                                .requestMatchers("/zona/**").permitAll()
                                .anyRequest()
                                .authenticated()
                                .and()
                                .httpBasic()
                                .and()
                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilter(jwtAuthenticationFilter)
                                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();

        }

        @Bean
        AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
                return http.getSharedObject(AuthenticationManagerBuilder.class)
                                .userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder())
                                .and()
                                .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        public static void main(String[] args) {
                System.out.println("password: " + new BCryptPasswordEncoder().encode("123456"));
        }

}