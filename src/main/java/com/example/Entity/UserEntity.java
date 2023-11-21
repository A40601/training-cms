package com.example.Entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "MANV", nullable = true, length = 45)
    private String manv;
    @Basic
    @Column(name = "FULLNAME", nullable = true, length = 45)
    private String fullname;
    @Basic
    @Column(name = "AGE", nullable = true, length = 45)
    private Integer age;
    @Basic
    @Column(name = "ADDRESS", nullable = true, length = 45)
    private String address;
    @Basic
    @Column(name = "PHONE", nullable = true, length = 45)
    private String phone;
    @Basic
    @Column(name = "EMAIL", nullable = true, length = 45)
    private String email;
    @Column(name = "GENDER", nullable = true, length = 10)
    private String gender;


}
