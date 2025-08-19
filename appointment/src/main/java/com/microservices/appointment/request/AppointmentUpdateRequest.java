package com.microservices.appointment.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentUpdateRequest {
    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in the future")
    private Date date;

    @NotBlank(message = "Reason is required")
    private String reason;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

}
