package com.example.Repository;


import com.example.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
    public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    List<CategoryEntity> findAllByName(String name);
    CategoryEntity findFirstByCode(String code);
    CategoryEntity findByName(String name);
    public Long countById(Integer id);
}
