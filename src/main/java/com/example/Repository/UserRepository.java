package com.example.Repository;

import com.example.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAllByFullname(String fullname);
    UserEntity findFirstByManv(String manv);
    public Long countById(Integer id);
}
