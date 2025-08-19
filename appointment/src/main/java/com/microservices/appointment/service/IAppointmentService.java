package com.microservices.appointment.service;

import com.microservices.appointment.dto.AppointmentDto;
import com.microservices.appointment.dto.DetailsDto;
import com.microservices.appointment.request.*;

import java.util.List;

public interface IAppointmentService {

    AppointmentDto createAppointment(AppointmentCreateRequest appointmentCreateRequest);

    AppointmentDto updateAppointment(Long id, AppointmentUpdateRequest appointmentUpdateRequest);

    void deleteAppointment(Long id);

    AppointmentDto getAppointmentById(Long id);

    List<AppointmentDto> getAllAppointments();

}
