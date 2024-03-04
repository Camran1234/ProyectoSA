package com.spring.tiketsys.dto.model;

public class UserTypeDTO {
    private int idUserType;
    private String type;

    public UserTypeDTO(int idUserType, String type) {
        this.idUserType = idUserType;
        this.type = type;
    }
    public UserTypeDTO() {
    }
    public int getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(int idUserType) {
        this.idUserType = idUserType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
