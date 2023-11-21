package com.example.Controller;


import com.example.Dto.UserDto;
import com.example.Entity.AdminEntity;
import com.example.Repository.AdminRepository;
import com.example.Repository.UserRepository;
import com.example.Service.UserService;
import com.example.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("web-user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "")
    String listUser(@RequestParam(required = false) String fullname,
                    Model model) {
        Object danhsach = userService.getAllUser(fullname);
        model.addAttribute("list", danhsach);
        return "/user/user";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    String newPage(Model model) {
        UserDto s = new UserDto();
        model.addAttribute("userDto", s);
        return "/user/create.html";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    String detailUser(@PathVariable Integer id, Model model) {
        Object u = userService.getDetail(id);
        model.addAttribute("userDto", u);
        return "/user/create.html";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        try{
            userService.deleteUser(id);
        }catch (UserNotFoundException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/web-user";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addUser(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult,
                   Model model,
                   RedirectAttributes redirectAttributes) throws IOException {
        Object result = null;

        String msg = "";

        if (bindingResult.hasErrors())
            return "/user/create.html";
        Integer id = userDto.getId();

        if (userDto.getId() == null) {
            UserDto u = userService.getDetailByManv(userDto.getManv());
            if (u != null) {
                model.addAttribute("message", "nhan vien da ton tai");
                return "/user/create.html";
            }
            userService.addUser(userDto);
            id = userService.getDetailByManv(userDto.getManv()).getId();
            msg = " tao moi";
        } else {
            result = userService.updateUser(userDto);
            msg = "Cập nhật";
        }
        if (Objects.equals(result, 0)) {
            model.addAttribute("message", msg + " fail");
            return "/user/create.html";
        }
        redirectAttributes.addFlashAttribute("message", msg + " user " + id + " thành công");
        return "redirect:/web-user/" + id;

    }
}
