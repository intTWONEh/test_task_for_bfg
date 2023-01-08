package com.example.chat.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsService myUserDetailsService;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    /**
     * Не аутентифицированные пользователи могут создавать учетную запись заходя на registration и отправляя данные на user
     * так же могут проходить аутентификацию через страницу login
     * Все аутентифицированные пользователи имеют полный доступ
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.GET, "/login", "/registration", "/pjs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .userDetailsService(myUserDetailsService)
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .and()
                .logout().logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .build();
    }
}
