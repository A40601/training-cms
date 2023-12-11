package com.example.Repository;


import com.example.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.categoryEntity WHERE p.name = :name")
    List<ProductEntity> findAllByName(@Param("name") String name);


    public Integer countById(Integer id);
    ProductEntity findFirstByCode(String code);
    List<ProductEntity> findByCategoryEntity_Id(Integer id);
   @Query()
    List<ProductEntity> findAllByCategoryEntity_Name(String name);
    @Transactional
    @Modifying
    @Query("DELETE FROM ProductEntity p WHERE p.id = :id")
    void deleteProductById(@Param("id") Integer id);
}
