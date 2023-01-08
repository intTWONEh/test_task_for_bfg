package com.example.chat.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Обработчик выхода пользователя из чата
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    /**
     * Удаляем пользователя из списка пользователей онлйн и перенаправляем на страницу login
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.sendRedirect("/login");
    }
}
