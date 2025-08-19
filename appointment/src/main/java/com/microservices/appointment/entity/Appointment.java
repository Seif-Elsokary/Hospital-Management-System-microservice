package com.microservices.appointment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Date;

@Entity @Table(name = "appointments")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Appointment date is required")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NotBlank(message = "Reason is required")
    private String reason;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;


}
