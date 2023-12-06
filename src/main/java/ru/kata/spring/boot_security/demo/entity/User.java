package ru.kata.spring.boot_security.demo.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import javax.validation.constraints.*;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull(message = "У User нет имени")
    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]*$", message = "Имя должно содержать только буквы")
    private String name;

    @Column
    @NotNull(message = "У User нет фамилии")
    @Size(min = 2, max = 50, message = "Фамилия должна содержать от 2 до 50 символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]*$", message = "Фамилия должна содержать только буквы")
    private String surname;

    @Column
    @NotNull(message = "У User нет возраста")
    @Min(value = 1, message = "Возраст должен быть больше 0")
    private int age;

    @Column(name = "username", unique = true)
    @NotEmpty(message = "username не может быть пустым")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "password не может быть пустым")
    private String password;

    @Fetch(FetchMode.JOIN)
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @NotEmpty(message = "Отсутствует роль")
    private Collection<Roles> roles;

    public String toStringUserRoles() {
        return roles.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}