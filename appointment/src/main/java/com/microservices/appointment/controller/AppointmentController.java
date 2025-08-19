package com.microservices.appointment.controller;

import com.microservices.appointment.dto.AppointmentDto;
import com.microservices.appointment.dto.DetailsDto;
import com.microservices.appointment.request.*;
import com.microservices.appointment.response.ApiResponse;
import com.microservices.appointment.service.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final IAppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse> createAppointment(@RequestBody AppointmentCreateRequest request) {
        AppointmentDto created = appointmentService.createAppointment(request);
        return new ResponseEntity<>(new ApiResponse("Appointment created successfully", created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateAppointment(@PathVariable Long id, @RequestBody AppointmentUpdateRequest request) {
        AppointmentDto updated = appointmentService.updateAppointment(id, request);
        return ResponseEntity.ok(new ApiResponse("Appointment updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(new ApiResponse("Appointment deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable Long id) {
        AppointmentDto dto = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(new ApiResponse("Appointment retrieved successfully", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllAppointments() {
        List<AppointmentDto> list = appointmentService.getAllAppointments();
        return ResponseEntity.ok(new ApiResponse("Appointments retrieved successfully", list));
    }


}
