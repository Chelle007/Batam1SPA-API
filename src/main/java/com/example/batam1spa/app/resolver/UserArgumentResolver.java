package com.example.batam1spa.app.resolver;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.example.batam1spa.security.service.JwtService;
import com.example.batam1spa.user.model.User;
import com.example.batam1spa.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    private final UserRepository userRepo;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return User.class.equals(parameter.getParameterType());
    }
    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        String authorization = servletRequest.getHeader("Authorization");
        if (authorization == null) {
            return null;
        }

        String token = authorization.replace("Bearer ", "");
        if (token.isEmpty()) {
            return null;
        }

        String username = jwtService.extractUsername(token);
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User username " + username + " not found"));
        if (!jwtService.isTokenValid(token, user)) {
            throw new ExpiredJwtException(null, null, "Token expired");
        }

        return user;
    }

}
