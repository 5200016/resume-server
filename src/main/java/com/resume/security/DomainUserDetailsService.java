package com.resume.security;

import com.resume.domain.Authority;
import com.resume.domain.SLogin;
import com.resume.domain.User;
import com.resume.repository.SLoginRepository;
import com.resume.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    private final SLoginRepository loginRepository;

    public DomainUserDetailsService(UserRepository userRepository, SLoginRepository loginRepository) {
        this.userRepository = userRepository;
        this.loginRepository = loginRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        if(login.equals("resume")){
            String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
            Optional<User> userFromDatabase = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
            return userFromDatabase.map(user -> {
                if (!user.getActivated()) {
                    throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
                }
                List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toList());
                return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                    user.getPassword(),
                    grantedAuthorities);
            }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
                "database"));
        }
        else {
            String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
            Optional<SLogin> userFromDatabase = loginRepository.findOneWithAuthoritiesByUsername(lowercaseLogin);
            return userFromDatabase.map(user -> {
                if (!user.isIsActive()) {
                    throw new UserNotActivatedException("用户 " + lowercaseLogin + " 没有激活");
                }
                Set<Authority> authorities = new HashSet<Authority>();
                Authority auth = new Authority();
                auth.setName("ROLE_USER");
                authorities.add(auth);
                List<GrantedAuthority> grantedAuthorities = authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toList());
                return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                    user.getPassword(),
                    grantedAuthorities);
            }).orElseThrow(() -> new UsernameNotFoundException("用户 " + lowercaseLogin + " 不存在"));
        }
    }
}
