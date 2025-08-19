package com.microservices.appointment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.appointment.dto.AppointmentDto;
import com.microservices.appointment.dto.DetailsDto;
import com.microservices.appointment.dto.DoctorDto;
import com.microservices.appointment.dto.PatientDto;
import com.microservices.appointment.entity.Appointment;
import com.microservices.appointment.exception.AppointmentNotFoundException;
import com.microservices.appointment.repository.AppointmentRepository;
import com.microservices.appointment.request.AppointmentCreateRequest;
import com.microservices.appointment.request.AppointmentUpdateRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;


    @Override
    public AppointmentDto createAppointment(AppointmentCreateRequest request) {
        Appointment appointment = Appointment.builder()
                .date(request.getDate())
                .reason(request.getReason())
                .patientId(request.getPatientId())   // حفظ patientId
                .doctorId(request.getDoctorId())     // حفظ doctorId
                .build();

        Appointment saved = appointmentRepository.save(appointment);
        return mapToDto(saved);
    }

    @Override
    public AppointmentDto updateAppointment(Long id, AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id));

        if (request.getDate() != null) appointment.setDate(request.getDate());
        if (request.getReason() != null) appointment.setReason(request.getReason());
        if (request.getPatientId() != null) appointment.setPatientId(request.getPatientId());
        if (request.getDoctorId() != null) appointment.setDoctorId(request.getDoctorId());

        Appointment updated = appointmentRepository.save(appointment);
        return mapToDto(updated);
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id));
        appointmentRepository.delete(appointment);
    }

    @Override
    public AppointmentDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id));
        return mapToDto(appointment);
    }

    @Override
    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    private AppointmentDto mapToDto(Appointment appointment) {
        return AppointmentDto.builder()
                .id(appointment.getId())
                .date(appointment.getDate())
                .reason(appointment.getReason())
                .build();
    }
}
