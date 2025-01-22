package com.agencia.backend.infrastructure.configuration.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter{

  private final JwtUtils jwtUtils;
  private final UserDetailsService userDetailsService;

  public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
    this.jwtUtils = jwtUtils;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {

    String token = jwtUtils.getJwtFromHeader(request);

    if (token != null && jwtUtils.validateJwtToken(token)) {
      // Encontra o usuário associado ao token JWT
      String username = jwtUtils.getUsernameFromJwtToken(token);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      // Valida o usuário e cria um objeto de autenticação
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());

      // Define os detalhes da autenticação
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Continua a cadeia de filtros
    filterChain.doFilter(request,response);
  }

}
