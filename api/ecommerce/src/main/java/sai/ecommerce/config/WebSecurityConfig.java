package sai.ecommerce.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sai.ecommerce.filter.JwtAuthenticationFilter;
import sai.ecommerce.model.ApiError;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  private final AuthenticationProvider authenticationProvider;

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return (request, response, authException) -> {
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      ApiError apiError = new ApiError();
      apiError.setError("Unauthorized");
      apiError.setMessage(authException.getMessage());
      apiError.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      final ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(response.getOutputStream(), apiError);
    };
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint())
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/**/auth/**")
        .permitAll()
        .anyRequest()
        .authenticated();

    http.authenticationProvider(authenticationProvider);

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
