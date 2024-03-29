package com.FinalYearProject.GarbageCollectionMS.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.FinalYearProject.GarbageCollectionMS.entity.users.Permission.*;
import static com.FinalYearProject.GarbageCollectionMS.entity.users.Role.ADMIN;
import static com.FinalYearProject.GarbageCollectionMS.entity.users.Role.SUPERVISOR;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request->request
                                .requestMatchers("/api/v1/auth/**")
                                .permitAll()

                                .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), SUPERVISOR.name())
                                .requestMatchers(GET,"/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(),SUPERVISOR_READ.name())
                                .requestMatchers(POST,"/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(),SUPERVISOR_CREATE.name())
                                .requestMatchers(PUT,"/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(),SUPERVISOR_UPDATE.name())
                                .requestMatchers(DELETE,"/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(),SUPERVISOR_DELETE.name())

//                                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
//                                .requestMatchers(GET,"/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
//                                .requestMatchers(POST,"/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
//                                .requestMatchers(PUT,"/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
//                                .requestMatchers(DELETE,"/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;
        return http.build();
    }

}
