package com.example.web.dto;

import java.util.UUID;

public class UserDTO {//mainly used to share the data at the time of login
    private UUID id;
    private String phone;
    private String name;

    public UserDTO(){}
    public UserDTO(UUID id, String phone, String name) {
        this.id = id;
        this.phone = phone;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
