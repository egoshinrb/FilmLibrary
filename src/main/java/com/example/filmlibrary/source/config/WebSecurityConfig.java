//package com.example.filmlibrary.source.config;
//
//import com.example.filmlibrary.source.service.userdetails.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import java.util.List;
//
//import static com.example.filmlibrary.source.constants.UserRoleConstants.*;
//import static com.example.filmlibrary.source.constants.SecurityConstants.*;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    CustomUserDetailsService customUserDetailsService;
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService,
//                             BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.customUserDetailsService = customUserDetailsService;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .cors().disable()
//                .csrf().disable()
//                //Настройка http-запросов - кому / куда, можно / нельзя
//                .authorizeHttpRequests((request) -> request
//                        .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
//                        .requestMatchers(FILMS_WHITE_LIST.toArray(String[]::new)).permitAll()
//                        .requestMatchers(USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
//                        .requestMatchers(FILMS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
//                        .anyRequest().authenticated()
//                )
//                //Настройки для входа в систему и выхода
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        //перенаправление после успешного логина
//                        .defaultSuccessUrl("/",true)
//                        .permitAll()
//                )
//                .logout((logout) -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                );
//
//        return httpSecurity.build();
//    }
//
//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(bCryptPasswordEncoder);
//    }
//}