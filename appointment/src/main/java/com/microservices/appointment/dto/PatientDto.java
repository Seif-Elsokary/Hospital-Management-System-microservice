package com.microservices.appointment.dto;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PatientDto {
    private Long id;
    private String name;
    private int age;
    private String phone;
    private String email;
    private String address;
    private String disease;
    private String bloodType;
    private Date dateOfRegistration;
    private String gender;
}
