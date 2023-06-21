package lazzy.web.controller;

import lazzy.web.entity.UserEntity;
import lazzy.web.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserServiceImpl userService;

    @Autowired
    public UserRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<String> home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String role = getRoleFromAuthentication(authentication);
            model.addAttribute("role", role);
        }
        return ResponseEntity.ok("index");
    }

    private String getRoleFromAuthentication(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "ADMIN";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            return "USER";
        }
        return "UNKNOWN";
    }

    @GetMapping("/users/current")
    public ResponseEntity<UserEntity> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        UserEntity currentUser = userService.findUser(username);
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/login")
    public ResponseEntity<String> showLoginPage() {
        return ResponseEntity.ok("login");
    }

    @GetMapping("/login-error")
    public ResponseEntity<String> showLoginErrorPage(Model model) {
        model.addAttribute("loginError", true);
        return ResponseEntity.ok("login-error");
    }
}
