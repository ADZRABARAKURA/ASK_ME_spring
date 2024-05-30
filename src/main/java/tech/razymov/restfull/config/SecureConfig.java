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
import tech.razymov.restfull.entity.Role;
import tech.razymov.restfull.service.UserService;
import tech.razymov.restfull.service.auth.AskMeUserDetailsService;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecureConfig{
    @Value("${security.rememberMeKey}")
    private String rememberMeKey;

    private final DataSource dataSource;
    private final UserService userService;
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
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a->
                    a.requestMatchers(HttpMethod.GET,
                            "/api/users",
                            "/api/users/*").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/users").hasRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/users/*").hasRole("USER"))
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
