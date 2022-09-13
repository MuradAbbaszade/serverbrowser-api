package com.company.adapter.account.jpa.entity;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import account.model.Account;
import com.company.adapter.role.jpa.entity.RoleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Data
@ToString
@Table(name = "user")
@EqualsAndHashCode
public class AccountEntity implements Serializable {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RoleEntity role;

    public AccountEntity() {
    }

    public AccountEntity(Integer id) {
        this.id = id;
    }

    public AccountEntity(Integer id, String name, String password, String email, RoleEntity role) {
        this.id = id;
        this.name = name;
        this.password = passwordEncoder().encode(password);
        this.email = email;
        this.role = role;
    }

    public Account toModel() {
        return Account.builder()
                .id(id)
                .name(name)
                .password(password)
                .email(email)
                .role(role.toModel())
                .build();
    }
}