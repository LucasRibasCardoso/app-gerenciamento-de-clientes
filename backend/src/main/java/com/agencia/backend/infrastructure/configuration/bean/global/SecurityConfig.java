package com.agencia.backend.infrastructure.configuration.bean.global;

import com.agencia.backend.infrastructure.configuration.jwt.AuthTokenFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Value("${url.frontend}")
  private String urlFrontend;

  @Autowired
  private AuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private AuthTokenFilter authTokenFilter;

  public SecurityConfig() {}

  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
    return roleHierarchy;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // Configuração de CORS
    http.cors(cors -> cors.configurationSource(request -> {
      var corsConfiguration = new CorsConfiguration();

      corsConfiguration.setAllowedOrigins(List.of(urlFrontend));

      corsConfiguration.setAllowedMethods(List.of(
          "GET",
          "POST",
          "PUT",
          "DELETE"
      ));

      corsConfiguration.setAllowedHeaders(List.of(
          "Authorization",
          "Content-Type",
          "Accept"
      ));

      corsConfiguration.setExposedHeaders(List.of(
          "Authorization"
      ));

      corsConfiguration.setMaxAge(3600L);

      corsConfiguration.setAllowCredentials(true);

      return corsConfiguration;
    }));

    // Desabilita o CSRF
    http.csrf(csrf -> csrf.disable());

    http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
        // Usuários e administradores podem acessar os endpoints abaixo
        .requestMatchers("/clients/**").hasAnyRole("USER", "ADMIN")
        .requestMatchers("/auth/logout").permitAll()
        .requestMatchers("/auth/login").permitAll()

        // Somente administradores pode acessar os endpoints abaixo
        .requestMatchers("/users/**").hasRole("ADMIN")
        .requestMatchers("/auth/register").hasRole("ADMIN")

        // H2 Database utilizado para desenvolvimento
        .requestMatchers("/h2-console/**").hasRole("ADMIN")

        // Qualquer outro endpoint requer autenticação
        .anyRequest().authenticated()
    );

    // Ativando o XSS Protection
    http.headers(headers -> headers.xssProtection(
        xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)));

    // Permite o uso de iframes somente para o mesmo domínio
    http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

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
