package com.microservices.appointment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailsDto {
    private Long id;
    private PatientDto patient;
    private DoctorDto doctor;
    private AppointmentDto appointment;
}
