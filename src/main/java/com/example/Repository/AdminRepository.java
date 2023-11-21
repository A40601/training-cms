package com.example.Repository;

import com.example.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, String> {
    AdminEntity findByUsername(String username);
}
