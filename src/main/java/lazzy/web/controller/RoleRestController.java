package lazzy.web.controller;
import lazzy.web.entity.RoleEntity;
import lazzy.web.entity.UserEntity;
import lazzy.web.service.RoleServiceImpl;
import lazzy.web.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {
    private final RoleServiceImpl roleService;
    private final UserServiceImpl userService;

    public RoleRestController(RoleServiceImpl roleService, UserServiceImpl userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/roles")
    public List<RoleEntity> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/assign")
    public void assignRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        roleService.assignRole(userId, roleId);
    }

    @PostMapping("/remove")
    public void removeRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        roleService.removeRole(userId, roleId);
    }

    @PostMapping("/create")
    public void createRole(@RequestParam("roleName") String roleName) {
        roleService.createRole(roleName);
    }

    @DeleteMapping("/roles/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId) {
        roleService.deleteRole(roleId);
    }
}
