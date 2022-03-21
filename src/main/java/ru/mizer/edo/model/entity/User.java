package ru.mizer.edo.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "shop")
    private String shop;


}