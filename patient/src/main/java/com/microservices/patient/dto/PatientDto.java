package com.microservices.patient.dto;



import lombok.*;


import java.util.Date;

@Data
@Builder
public class PatientDto {
    private Long patientId;
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
