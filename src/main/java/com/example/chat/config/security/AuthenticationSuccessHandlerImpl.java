package com.example.chat.config.security;

import com.example.chat.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Обработчик для удачной аутентификации пользователя
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    /**
     * Отправляем пользователю его userId в cookie и перенаправлям на основную страницу с чатом
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = ((SecurityUser) authentication.getPrincipal()).getUser();
        response.addCookie(new Cookie("userId", user.getUserId()));
        response.sendRedirect("/");
    }
}
