package br.com.fiap.fasteats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers()
                .xssProtection().and()
                .contentSecurityPolicy("script-src 'self'").and()
                .contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable);
        return http.build();
    }
}
