package com.microservices.doctor.controller;

import com.microservices.doctor.dto.DoctorDto;
import com.microservices.doctor.request.DoctorCreateRequest;
import com.microservices.doctor.request.DoctorUpdateRequest;
import com.microservices.doctor.response.ApiResponse;
import com.microservices.doctor.service.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final IDoctorService doctorService;

    @PostMapping
    public ResponseEntity<ApiResponse> createDoctor(@RequestBody DoctorCreateRequest request) {
        DoctorDto createdDoctor = doctorService.createDoctor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Doctor created successfully", createdDoctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDoctor(@RequestBody DoctorUpdateRequest request, @PathVariable Long id) {
        DoctorDto updatedDoctor = doctorService.updateDoctor(request, id);
        return ResponseEntity.ok(new ApiResponse("Doctor updated successfully", updatedDoctor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDoctorById(@PathVariable Long id) {
        DoctorDto doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(new ApiResponse("Doctor retrieved successfully", doctor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDoctorById(@PathVariable Long id) {
        doctorService.deleteDoctorById(id);
        return ResponseEntity.ok(new ApiResponse("Doctor deleted successfully", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllDoctors() {
        List<DoctorDto> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(new ApiResponse("All doctors retrieved successfully", doctors));
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<ApiResponse> getDoctorsByName(@RequestParam String name) {
        List<DoctorDto> doctors = doctorService.getDoctorsByName(name);
        return ResponseEntity.ok(new ApiResponse("Doctors retrieved by name", doctors));
    }

    @GetMapping("/search-by-specialty")
    public ResponseEntity<ApiResponse> getDoctorsBySpecialty(@RequestParam String specialty) {
        List<DoctorDto> doctors = doctorService.getDoctorsBySpecialty(specialty);
        return ResponseEntity.ok(new ApiResponse("Doctors retrieved by specialty", doctors));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer minExperience) {
        List<DoctorDto> doctors = doctorService.searchDoctors(name, specialty, gender, minExperience);
        return ResponseEntity.ok(new ApiResponse("Search results", doctors));
    }
}
