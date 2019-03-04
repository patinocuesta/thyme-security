package fr.laerce.thymesecurity.web;


import fr.laerce.thymesecurity.security.dao.UserDao;
import fr.laerce.thymesecurity.security.domain.User;
import fr.laerce.thymesecurity.security.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class UserController {
    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;

    @GetMapping("admin/users")
    public String list(Model model){
        Iterable<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping(value = "/register")
   public String formAddPost(User user, BindingResult bindingResult, Model model) {
        model.addAttribute("user", new User());
       User userExists = userService.findByUserName(user.getName());
       if(userExists != null) {
           model.addAttribute("message", "Username already registered");
           return "/register";
       } else{
           userService.save(user);
           model.addAttribute("message", "Ok, please login");
           return "login";
       }

    }


    @GetMapping("modPasswordByAdmin")
    public String modPasswordByAdmin(@RequestParam("id") long id, @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
        if (confirmPassword.equals(newPassword)) {
            User user = userService.findById(id);
            user.setPassword(newPassword);
            userService.save(user);
        }
        return "redirect:/admin/users";
    }
    @GetMapping("admin/recovery/{id}")
    public String recovery(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("idUser", id);
        return "recovery";
    }
    @GetMapping("/user")
    public String protectedPage() {
        return "user/home";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/home";
    }


}