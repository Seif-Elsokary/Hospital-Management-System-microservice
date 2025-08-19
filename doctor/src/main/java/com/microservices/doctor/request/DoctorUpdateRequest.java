package com.microservices.doctor.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@Data
public class DoctorUpdateRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    private String password;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\d{10,15}$", message = "Phone must be between 10 and 15 digits")
    private String phone;

    @Min(value = 25, message = "Doctor must be at least 25 years old")
    @Max(value = 80, message = "Doctor's age must be less than or equal to 80")
    private int age;

    @Min(value = 0, message = "Years of experience must be positive")
    private int yearOfExperience;

    @NotBlank(message = "Specialty is required")
    private String specialty;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Gender is required")
    private String gender;
}
