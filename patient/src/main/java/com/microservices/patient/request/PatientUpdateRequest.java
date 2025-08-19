package com.microservices.patient.request;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.*;
import lombok.*;


import java.util.Date;

@Data @Builder
public class PatientUpdateRequest {
    @Size(min = 1, message = "Name cannot be empty")
    private String name;

    @Min(value = 0, message = "Age must be greater than or equal to 0")
    private Integer age;

    @Pattern(regexp = "^(01)[0-9]{9}$", message = "Phone number must be a valid Egyptian number")
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    private String password;

    private String address;

    private String disease;

    private String bloodType;

    @PastOrPresent(message = "Date of registration must be in the past or present")
    private Date dateOfRegistration;

    private String gender;
}
