package com.resumeparser.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HRDTO {
    @NotBlank(message = "name is requried")
    private String name;

    @NotBlank(message = "Email is requried")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", 
             message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Password is requried")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,}$", 
        message = "Please Chosee a strong password containing atleast 8 characters with one lowercase, uppercase, digit and spical character")
    private String password;

    @NotBlank(message = "Reenter password is requried")
    private String reenterPassword;

    private MultipartFile profilePhoto;

    @AssertTrue(message = "please agree to terms and conditions")
    private boolean tac;

    @NotBlank(message = "company name is requried")
    private String companyName;

    @NotBlank(message = "company address is requried")
    private String companyAddress;
}
