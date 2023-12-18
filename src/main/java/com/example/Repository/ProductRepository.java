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
    List<ProductEntity> findAllByName(@Param("name") String name);

    @Query("SELECT u FROM ProductEntity u WHERE CONCAT(u.name,'') LIKE %?1%" )
    List<ProductEntity> searchProduct(String keyword);

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
