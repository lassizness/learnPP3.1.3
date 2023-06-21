package lazzy.web.controller;

import lazzy.web.entity.UserEntity;
import lazzy.web.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
//@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {

        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        List<UserEntity> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/admin/add")
    public String addUser(@RequestParam String name, @RequestParam int age, @RequestParam String username, @RequestParam String password) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setAge(age);
        user.setUsername(username);
        user.setPassword(password);
        adminService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        adminService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUserForm(@PathVariable("id") long id, Model model) {
        UserEntity user = adminService.getUser(id);
        model.addAttribute("id", id);
        model.addAttribute("user", user);
        return "editUser";
    }
    @PostMapping("/admin/edit")
    public String editUser(@RequestParam("id") long id, UserEntity user) {
        user.setId(id);
        adminService.updateUser(user);
        return "redirect:/admin";
    }

}
