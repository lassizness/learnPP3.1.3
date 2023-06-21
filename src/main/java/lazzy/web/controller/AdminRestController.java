package lazzy.web.controller;

import lazzy.web.entity.UserEntity;
import lazzy.web.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final AdminService adminService;

    @Autowired
    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public List<UserEntity> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PostMapping("/users")
    public UserEntity addUser(@RequestBody UserEntity user) {
        return adminService.addUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        adminService.deleteUser(id);
    }

    @PutMapping("/users/{id}")
    public UserEntity updateUser(@PathVariable("id") long id, @RequestBody UserEntity user) {
        user.setId(id);
        return adminService.updateUser(user);
    }
}