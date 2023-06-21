package lazzy.web.config;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        String userRole;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//Получаем роль
        if (authorities != null && !authorities.isEmpty()) {
            userRole = authorities.iterator().next().getAuthority();
        } else {
            userRole = "UNKNOWN";
        }
        httpServletRequest.setAttribute("userRole", userRole);

        if ("ROLE_ADMIN".equals(userRole)) {
            httpServletResponse.sendRedirect("/admin");
        } else if ("ROLE_USER".equals(userRole)) {
            httpServletResponse.sendRedirect("/user");
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }
}