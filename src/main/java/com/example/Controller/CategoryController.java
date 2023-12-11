package com.example.Controller;

import com.example.Dto.CategoryDto;
import com.example.Service.CategoryService;
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
@RequestMapping("web-category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    String listCategory(@RequestParam(required = false) String name,
                       Model model){
        Object danhsach = categoryService.getAllCate(name);
        model.addAttribute("list", danhsach);
        return "/category/listCategory";
    }
    @GetMapping
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    String newPage(Model model) {
        CategoryDto c = new CategoryDto();
        model.addAttribute("categoryDto", c);
        return "/category/create";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    String detailCategory(@PathVariable Integer id, Model model) {
        Object c = categoryService.getDetail(id);
        model.addAttribute("categoryDto", c);
        return "/category/create";
    }
    @GetMapping("/delete/{id}")
    public String deleteCate(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        try{
            categoryService.deleteCategory(id);
        }catch (UserNotFoundException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/web-category";
    }
    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addCate(@Valid @ModelAttribute CategoryDto categoryDto, BindingResult bindingResult,
                   Model model,
                   RedirectAttributes redirectAttributes) throws IOException {
        Object result = null;

        String msg = "";

        if (bindingResult.hasErrors())  return "/category/create";
        Integer id = categoryDto.getId();

        if (categoryDto.getId() == null) {
            CategoryDto c = categoryService.getDetailByCode(categoryDto.getCode());
            if (c != null) {
                model.addAttribute("message", "Thể loại đã tồn tại");
                return "/category/create";
            }
            categoryService.addCate(categoryDto);
            id = categoryService.addCate(categoryDto).getId();
            msg = " tao moi";
        } else {
            result = categoryService.updateCategory(categoryDto);
            msg = "Cập nhật";
        }
        if (Objects.equals(result, 0)) {
            model.addAttribute("message", msg + " fail");
            return "/category/create";
        }
        redirectAttributes.addFlashAttribute("message", msg + " category " + id + " thành công");
        return "redirect:/web-category/" + id;
    }
}
