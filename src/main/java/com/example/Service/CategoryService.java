package com.example.Service;

import com.example.Dto.CategoryDto;
import com.example.Entity.CategoryEntity;
import com.example.Repository.CategoryRepository;
import com.example.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<CategoryEntity> getAllCate(String name){
        if (StringUtils.isEmpty(name))
            return categoryRepository.findAll();
        else
            return categoryRepository.findAllByName(name);
    }
    public CategoryDto getDetail(Integer id) {
        CategoryDto categoryDto = new CategoryDto();
        CategoryEntity categoryEntity = categoryRepository.findById(id).get();
        if (categoryEntity != null)
            BeanUtils.copyProperties(categoryEntity,categoryDto);
        else
            return null;
        return categoryDto ;
    }
    @Transactional
    public void deleteCategoryWithProducts(Integer id) {
        // Thực hiện xóa danh mục và tất cả các sản phẩm liên quan sẽ được xóa tự động
        categoryRepository.deleteById(id);
    }
    public CategoryDto getDetailByCode(String code){
        CategoryDto categoryDto = new CategoryDto();
        CategoryEntity categoryEntity = categoryRepository.findFirstByCode(code);
        if (categoryEntity != null)
            BeanUtils.copyProperties(categoryEntity, categoryDto);
        else
            return null;
        return categoryDto;
    }
    public CategoryDto addCate(CategoryDto categoryDto){
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(categoryDto, categoryEntity);
        categoryRepository.save(categoryEntity);
        categoryDto.setId(categoryEntity.getId());
        return categoryDto;
    }

    public CategoryDto updateCategory(CategoryDto categoryDto){
        CategoryEntity categoryEntity = categoryRepository.findById(categoryDto.getId()).get();
        BeanUtils.copyProperties(categoryDto, categoryEntity);
        categoryRepository.save(categoryEntity);
        return categoryDto;
    }
    public void deleteCategory(Integer id){
        Long count = categoryRepository.countById(id);
        if (count == null || count == 0){
            throw new UserNotFoundException("could not find any users with Id"+ id);
        }
        categoryRepository.deleteById(id);
    }
}
