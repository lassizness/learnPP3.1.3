package lazzy.web.controller;

import lazzy.web.service.RoleServiceImpl;
import lazzy.web.service.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {
    private final RoleServiceImpl roleService;
    private final UserServiceImpl userService;

    public RoleController(RoleServiceImpl roleService, UserServiceImpl userService) {
        this.roleService = roleService;
        this.userService =userService;

    }

    @GetMapping("/assign-role")
    public String showAssignRolePage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "assignRole";
    }

    @PostMapping("/assign-role")
    public String assignRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        roleService.assignRole(userId, roleId);
        return "redirect:/assign-role";
    }

    @PostMapping("/remove-role")
    public String removeRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        roleService.removeRole(userId, roleId);
        return "redirect:/assign-role";
    }

    @PostMapping("/create-role")
    public String createRole(@RequestParam("roleName") String roleName) {
        roleService.createRole(roleName);
        return "redirect:/assign-role";
    }

    @PostMapping("/remove-roles")
    public String removeRole(@RequestParam("roleId") Long roleId) {
        roleService.deleteRole(roleId);
        return "redirect:/assign-role";
    }

}
