package com.example.filmlibrary.source.service.userdetails;

import com.example.filmlibrary.source.constants.UserRoleConstants;
import com.example.filmlibrary.source.model.User;
import com.example.filmlibrary.source.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.filmlibrary.source.constants.UserRoleConstants.ADMIN;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Value("${spring.security.user.name}")
    private String adminUser;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(adminUser)) {
            return new CustomUserDetails(null, username, adminPassword,
                    List.of(new SimpleGrantedAuthority("ROLE_" + ADMIN)));
        }

        User user = userRepository.findUserByLogin(username);
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(user.getRole().getId() == 1L
            ? "ROLE_" + UserRoleConstants.USER
            : "ROLE_" + UserRoleConstants.LIBRARIAN));

        return new CustomUserDetails(user.getId().intValue(), username, user.getPassword(), authorities);
    }
}
