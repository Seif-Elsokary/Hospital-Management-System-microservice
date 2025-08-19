package com.microservices.patient.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class PatientCreateRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 0, message = "Age must be greater than or equal to 0")
    private int age;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^(01)[0-9]{9}$", message = "Phone number must be a valid Egyptian number")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Disease is required")
    private String disease;

    @NotBlank(message = "Blood type is required")
    private String bloodType;

    @PastOrPresent(message = "Date of registration must be in the past or present")
    private Date dateOfRegistration;

    @NotNull(message = "Gender must be selected")
    private String gender;
}
