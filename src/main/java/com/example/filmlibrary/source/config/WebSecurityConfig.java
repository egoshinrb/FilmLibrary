package com.example.filmlibrary.source.config;

import com.example.filmlibrary.source.service.userdetails.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

import static com.example.filmlibrary.source.constants.UserRoleConstants.ADMIN;
import static com.example.filmlibrary.source.constants.UserRoleConstants.LIBRARIAN;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    CustomUserDetailService customUserDetailService;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(CustomUserDetailService customUserDetailService,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customUserDetailService = customUserDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private final List<String> RESOURCES_WHITE_LIST = List.of(
            "/resources/**",
            "/static/**",
            "/css/**",
            "/js/**",
            "/swagger-ui/**",
            "/"
    );


    private final List<String> FILMS_WHITE_LIST = List.of("/films");

    private final List<String> FILMS_PERMISIONS_LIST = List.of(
            "/films/add",
            "films/update",
            "films/delete"
    );

    private final List<String> USER_WHITE_LIST = List.of(
            "/login",
            "/users/registration",
            "/users/remember-password/"
    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().disable()
                .csrf().disable()
                // Настройка http-запросов - кому / куда можно / нельзя
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(FILMS_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(FILMS_PERMISIONS_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
                        .anyRequest().authenticated()
                )
                // Настройкадля входа в систему и выхода
                .formLogin(form -> form
                        .loginPage("/login")
                        // перенаправление после успешного входа
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                );

        return httpSecurity.build();
    }

    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
}
