package com.example.Controller;


import com.example.Dto.ProductDto;
import com.example.Entity.CategoryEntity;
import com.example.Entity.ProductEntity;
import com.example.Repository.ProductRepository;
import com.example.Service.CategoryService;
import com.example.Service.ProductService;
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

import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
@Controller
@RequestMapping("web-product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET, value = "")
    String listProduct(@RequestParam(required = false) String name,
                    Model model) {
       List<ProductEntity> listProduct = productService.getAllProduct(name);
       List<CategoryEntity> list = categoryService.getAllCate(name);
        model.addAttribute("listCate", list);
        model.addAttribute("list", listProduct);
        return "product1/listProduct";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, params = "categoryName")
    public String getProductByCategoryName(@QueryParam("categoryName") String categoryName, String name, Model model){
        List<ProductEntity> productEntities = productService.getProductByCategoryName(categoryName);
        List<CategoryEntity> list = categoryService.getAllCate(name);
        model.addAttribute("listCate", list);
        model.addAttribute("product", productEntities);
        return "/product1/Product";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    String newPage(Model model) {
        ProductDto p = new ProductDto();
        model.addAttribute("productDto", p);
        return "product1/create";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}",  params = "!categoryName")
    public String detailProduct(@PathVariable Integer id, Model model) {
        Object productDto = productService.getDetail(id);
        model.addAttribute("productDto", productDto);
        return "product1/create";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        try{
            productService.deleteProduct(id);
        }catch (UserNotFoundException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/web-product";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String addCate(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult,
                   Model model,
                   RedirectAttributes redirectAttributes) throws IOException {
        Object result = null;

        String msg = "";

        if (bindingResult.hasErrors())
            return "product1/create";
        Integer id = productDto.getId();

        if (productDto.getId() == null) {
            ProductDto c = productService.getDetailByCode(productDto.getCode());
            if (c != null) {
                model.addAttribute("message", "san pham đã tồn tại");
                return "product1/create";
            }
            id = productService.addProduct(productDto).getId();
            msg = " tao moi";
        } else {
            result = productService.updateProduct(productDto);
            msg = "Cập nhật";
        }
        if (Objects.equals(result, 0)) {
            model.addAttribute("message", msg + " fail");
            return "product1/create";
        }
        redirectAttributes.addFlashAttribute("message", msg + " product " + id + " thành công");
        return "redirect:/web-product/" + id;
    }
}
