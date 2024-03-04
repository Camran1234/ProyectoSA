package com.spring.tiketsys.dto.model;

public class UserDTO {
    private int idUser;
    private String name;
    private String lastName;
    private String phone;
    private int userType;

    public UserDTO(int idUser, String name, String lastName, String phone, int userType) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.userType = userType;
    }

    public UserDTO() {
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
