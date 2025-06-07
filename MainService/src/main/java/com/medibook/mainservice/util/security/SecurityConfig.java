package com.medibook.mainservice.util.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Stream;

@Configuration
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.client.issuer-uri}")
    private String clientIssuer;
    @Value("${spring.security.oauth2.resourceserver.jwt.doctor.issuer-uri}")
    private String doctorIssuer;
    @Value("${spring.security.oauth2.resourceserver.jwt.admin.issuer-uri}")
    private String adminIssuer;


    interface AuthoritiesConverter extends Converter<Map<String, Object>, Collection<GrantedAuthority>> {}

    @Bean
    AuthoritiesConverter realmRolesAuthoritiesConverter() {
        return claims -> {
            var realmAccess = Optional.ofNullable((Map<String, Object>) claims.get("realm_access"));
            var roles = realmAccess.flatMap(map -> Optional.ofNullable((List<String>) map.get("roles")));
            System.out.println(roles);
            List<GrantedAuthority> authorities =  roles.map(List::stream)
                    .orElse(Stream.empty())
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
                    .toList();

            for(GrantedAuthority authority : authorities) {
                System.out.printf("%s\n", authority.getAuthority());
            }

            return authorities;
        };
    }

    @Bean
    JwtAuthenticationConverter authenticationConverter(
            Converter<Map<String, Object>, Collection<GrantedAuthority>> authoritiesConverter) {
        var authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            return authoritiesConverter.convert(jwt.getClaims());
        });
        return authenticationConverter;
    }

    @Bean
    SecurityFilterChain resourceServerSecurityFilterChain(
            HttpSecurity http,
            Converter<Jwt, AbstractAuthenticationToken> authenticationConverter) throws Exception {

        JwtIssuerAuthenticationManagerResolver authenticationManagerResolver =
                new JwtIssuerAuthenticationManagerResolver(issuer -> {
                    if (!Objects.equals(issuer, clientIssuer)
                            && !Objects.equals(issuer, doctorIssuer)
                            && !Objects.equals(issuer, adminIssuer)) {
                        throw new JwtException("Untrusted issuer: " + issuer);
                    }

                    JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuer);

                    JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtDecoder);
                    provider.setJwtAuthenticationConverter(authenticationConverter);

                    return new ProviderManager(provider);
                });

        http.oauth2ResourceServer(resourceServer -> {
            resourceServer.authenticationManagerResolver(authenticationManagerResolver);
        });

        http.sessionManagement(sessions -> {
            sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }).csrf(csrf -> {
            csrf.disable();
        });

        http.authorizeHttpRequests(requests -> {
            requests.requestMatchers("/me").authenticated();
            requests.requestMatchers("/api/v1/*/client/**").hasAuthority("CLIENT");
            requests.requestMatchers("/api/v1/*/doctor/**").hasAuthority("DOCTOR");
            requests.anyRequest().permitAll();
        });

        return http.build();
    }


}
