package com.example.batam1spa.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.batam1spa.security.filter.JwtAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui/**",
            "/v1/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources"
    };

    private static final String[] POST_WHITELIST = {
            "/api/v1/auth/sign-in",
            "/api/v1/order/add-to-cart",
            "/api/v1/order/remove-from-cart",
            "/api/v1/order/checkout",
    };

    private static final String[] GET_WHITELIST = {
            "/api/v1/service/get-all-service",
            "/api/v1/service/get-signature-service",
            "/api/v1/availability/get-service-availability",
            "/api/v1/availability/get-service-available-date",
            "/api/v1/availability/get-service-available-time-slot",
            "/api/v1/availability/get-bundle-available-date",
            "/api/v1/availability/get-bundle-available-time-slot",
            "/api/v1/order/get-cart",
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, POST_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.GET, GET_WHITELIST).permitAll()
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                            final Map<String, Object> body = new HashMap<>();

                            body.put("status", false);
                            body.put("message", "Full authentication is required to access this resource" + exception);
                            body.put("code", HttpServletResponse.SC_UNAUTHORIZED);

                            final ObjectMapper mapper = new ObjectMapper();
                            mapper.writeValue(response.getOutputStream(), body);
                        }));

        return http.build();
    }
}
