package spring.jpa.test.devetiadb.config;

import com.nimbusds.jose.JWSAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import spring.jpa.test.devetiadb.enums.Role;

import javax.crypto.spec.SecretKeySpec;

@Configuration // nó sẽ tự động chạy vào và đẩ các bean vào context
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {
            "/users",
            "/auth/token",
            "/auth/introspect",
            "/auth/logout"
    };

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(
                        oauth2 -> oauth2
                                .jwt(jwtConfigurer -> jwtConfigurer
                                        .decoder(jwtDecoder())
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                                //Điểm mà lỗi được bắt, ta sẽ quyết định điều hướng đi đâu
                                //Chúng ta phải implement 1 interface AuthenticationEntryPoint
                                // bởi vì chúng ta chỉ sử dụng JwtAuthenticationEntryPoint ở đây nên cũng ko cần tạo bean cho nó
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                )
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec spec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(spec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
