package com.spring.tiketsys.dto.entity;

import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table(name="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idUser")
    private int idUser;
    @NonNull
    @Column(name="username")
    private String username;
    @NonNull
    @Column(name="password")
    private String password;
    private String name;
    private String lastName;
    private String phone;
    @NonNull
    @ManyToOne
    @JoinColumn(name="userType", referencedColumnName = "idUserType")
    private UserType userType;

    public User() {
    }
    public User(@NonNull String username, @NonNull String password, String name, String lastName, String phone, @NonNull UserType userType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
