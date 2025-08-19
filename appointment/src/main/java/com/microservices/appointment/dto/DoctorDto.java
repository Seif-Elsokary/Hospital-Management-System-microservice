package com.microservices.appointment.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DoctorDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private int age;
    private int yearOfExperience;
    private String specialty;
    private String address;
    private String gender;
}
