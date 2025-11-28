package com.example.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Validation:
 * - phone: required, digits only, length 10-15
 * - name: required
 */
public class RegisterRequest {

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "phone must be 10 to 15 digits")
    private String phone;


    @NotBlank(message = "password is required")
    @Size(max = 8, message = "password must be at most 8 characters")
    private String password;

    public RegisterRequest() { }

    public RegisterRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public RegisterRequest setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequest setPassword(String Password) {
        this.password = Password;
        return this;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "phone='" + phone + '\'' +
                '}';
    }

}