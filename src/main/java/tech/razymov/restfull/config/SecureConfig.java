package tech.razymov.restfull.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tech.razymov.restfull.entity.Role;
import tech.razymov.restfull.service.UserService;
import tech.razymov.restfull.service.auth.AskMeUserDetailsService;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecureConfig{
    @Value("${security.rememberMeKey}")
    private String rememberMeKey;

    private final DataSource dataSource;
    private final UserService userService;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://askme-donation.ru")); // Разрешенные источники
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    UserDetailsService userDetailsService(){
        return new AskMeUserDetailsService(userService);
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new AuthSuccessHandler();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(AbstractHttpConfigurer::disable)
                //.requiresChannel(c->c.anyRequest().requiresSecure())
                .authorizeHttpRequests(a->
                    a.requestMatchers(HttpMethod.GET,
                            "/api/users",
                            "/api/users/**",
                                    "/ws/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/users/**","/api/donations/**", "/payments/**").permitAll()
                            .requestMatchers(HttpMethod.PATCH, "/api/users/**").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
                            .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority()))
                .formLogin(login -> login
                        .permitAll()
                        .loginPage("/login")
                        .passwordParameter("password")
                        .usernameParameter("email")
                        .successHandler(myAuthenticationSuccessHandler()))
                .rememberMe(r -> r.alwaysRemember(true)
                        .key(rememberMeKey)
                        .tokenRepository(persistentTokenRepository())
                        .userDetailsService(userDetailsService())
                        .tokenValiditySeconds(24*60*60*14))
                .exceptionHandling(exconf ->
                        exconf.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(logout -> logout.logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
                        .deleteCookies("JSESSIONID", "remember-me"))
        ;
        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
