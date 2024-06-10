package com.shyam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordDTO {
    @NotBlank(message = "Password is requried")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,}$", 
        message = "Please Chosee a strong password containing atleast 8 characters with one lowercase, uppercase, digit and spical character")
    private String password;

    @NotBlank(message = "Reenter password is requried")
    private String reenterPassword;
}
