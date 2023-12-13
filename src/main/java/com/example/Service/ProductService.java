package com.example.Service;

import com.example.Dto.ProductDto;
import com.example.Entity.CategoryEntity;
import com.example.Entity.ProductEntity;
import com.example.Repository.CategoryRepository;
import com.example.Repository.ProductRepository;
import com.example.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    public List<ProductEntity> getAllProduct(String name){
        if (StringUtils.isEmpty(name))
            return productRepository.findAll();
        else
            return productRepository.findAllByName(name);
    }

    public ProductDto getDetail(Integer id) {
        ProductDto productDto = new ProductDto();
        ProductEntity productEntity = productRepository.findById(id).get();
        if (productEntity != null)
            BeanUtils.copyProperties(productEntity,productDto);
        else
            return null;
        return productDto ;
    }

    public ProductDto getDetailByCode(String code){
        ProductDto productDto = new ProductDto();
        ProductEntity productEntity = productRepository.findFirstByCode(code);
        if (productEntity != null)
            BeanUtils.copyProperties(productEntity, productDto);
        else
            return null;
        return productDto;
    }
    public ProductDto addProduct(ProductDto productDto){
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productDto, productEntity);
        productRepository.save(productEntity);
        productDto.setId(productEntity.getId());
        return productDto;
    }

    public ProductDto updateProduct(ProductDto productDto){
        ProductEntity productEntity = productRepository.findById(productDto.getId()).get();
        BeanUtils.copyProperties(productDto, productEntity);
        productRepository.save(productEntity);
        return productDto;
    }
    public void deleteProduct(Integer id){
        Integer count = productRepository.countById(id);
        if (count == null || count == 0){
            throw new UserNotFoundException("could not find any users with Id"+id);
        }
        productRepository.deleteProductById(id);
    }

    //---------------------------------

    public List<ProductEntity> getProductByCategoryName(String categoryName){
        CategoryEntity categoryEntity = categoryRepository.findByName(categoryName);
        if (categoryEntity != null){
            return productRepository.findByCategoryEntity_Id(categoryEntity.getId());
        }
        return null;
    }

}
