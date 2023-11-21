package com.example.Entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin")
public class AdminEntity {
    @Id
    @Column(name = "USERNAME", nullable = false)
    private String username;
    @Basic
    @Column(name = "PASSWORD", nullable = false, length = 45)
    private String password;

}
