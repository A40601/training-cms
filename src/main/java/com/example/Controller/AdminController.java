package com.example.Controller;

import com.example.Entity.AdminEntity;
import com.example.Entity.UserEntity;
import com.example.Repository.AdminRepository;
import com.example.Repository.UserRepository;
import com.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
@RequestMapping("web-admin")
public class AdminController {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @RequestMapping(method = RequestMethod.GET, value="/login")
    String login(Model model){
        AdminEntity adminEntity = new AdminEntity();
        model.addAttribute("adminEntity", adminEntity);
        return "/login1/login";
    }

    @RequestMapping(method = RequestMethod.POST, value="/login")
    String loginAdmin(@ModelAttribute("adminEntity") AdminEntity adminEntity, Model model,String fullname){
        String username = adminEntity.getUsername();
        AdminEntity adminData=adminRepository.findByUsername(username);
        Object danhsach = userService.getAllUser(fullname);
        model.addAttribute("list", danhsach);
        if (adminEntity.getPassword().equals(adminData.getPassword())){
            return "/user/user";
        }else {
            return "/login1/login";
        }

    }
}
