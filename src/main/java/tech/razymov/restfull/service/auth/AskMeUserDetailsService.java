package tech.razymov.restfull.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tech.razymov.restfull.service.UserService;

@RequiredArgsConstructor
public class AskMeUserDetailsService implements UserDetailsService {
    private final UserService usersService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = usersService.findByEmail(email);
        return new AskMeUserPrincipal(user);
    }
}