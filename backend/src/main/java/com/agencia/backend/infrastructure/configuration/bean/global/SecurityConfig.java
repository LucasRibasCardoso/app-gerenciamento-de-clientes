package com.agencia.backend.infrastructure.configuration.bean.global;

import com.agencia.backend.infrastructure.configuration.jwt.AuthTokenFilter;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${url.frontend}")
    private String clientUrl;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    public SecurityConfig() {
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");
        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Configuração de CORS
        http.cors(cors -> cors.configurationSource(request -> {
            var corsConfiguration = new CorsConfiguration();

            corsConfiguration.setAllowedOrigins(List.of(clientUrl));

            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

            corsConfiguration.setAllowedHeaders(List.of(
                    "Authorization",
                    "Content-Type",
                    "Accept",
                    "Origin",
                    "X-Requested-With"
            ));

            corsConfiguration.setExposedHeaders(List.of(
                    "Authorization"
            ));

            corsConfiguration.setMaxAge(Duration.ofDays(1));

            corsConfiguration.setAllowCredentials(Boolean.TRUE);

            return corsConfiguration;
        }));

        // Desabilita o CSRF
        http.csrf(csrf -> csrf.disable());

        // Permite iframes para o console H2
        http.headers(headers ->
                headers.frameOptions(frameOptions -> frameOptions.disable())
        );

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests

                // Libera o preflight request
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Endpoints públicos - não requerem autenticação
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/logout").permitAll()

                // Endpoints somente para administradores
                .requestMatchers("/users/**").hasRole("ADMIN")
                .requestMatchers("/h2-console/**").permitAll()

                // Endpoints para administradores - controle granular
                .requestMatchers(HttpMethod.POST, "/clients/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT, "/clients/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/clients/**").hasAnyRole("ADMIN", "MANAGER")

                // Endpoints para leitura - disponíveis para todos os usuários autenticados
                .requestMatchers(HttpMethod.GET, "/clients/**").hasAnyRole("USER", "ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.GET, "/dashboard/data").hasAnyRole("USER", "ADMIN", "MANAGER")


                // Qualquer outro endpoint requer autenticação
                .anyRequest().authenticated()
        );

        // Configuração de sessão
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Configuração de autenticação caso ocorra algum erro
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));

        // Adiciona o filtro de autenticação
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

}
