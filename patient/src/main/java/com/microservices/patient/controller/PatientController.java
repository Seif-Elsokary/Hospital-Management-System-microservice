package com.microservices.patient.controller;

import com.microservices.patient.dto.*;
import com.microservices.patient.request.*;
import com.microservices.patient.response.*;
import com.microservices.patient.service.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final IPatientService patientService;

    // 🔹 Get patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPatientById(@PathVariable Long id) {
        PatientDto patient = patientService.getPatientById(id);
        return ResponseEntity.ok(new ApiResponse("Patient retrieved successfully", patient));
    }

    // 🔹 Create patient
    @PostMapping
    public ResponseEntity<ApiResponse> createPatient(@Valid @RequestBody PatientCreateRequest request) {
        PatientDto created = patientService.createPatient(request);
        return ResponseEntity.ok(new ApiResponse("Patient created successfully", created));
    }

    // 🔹 Update patient
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientUpdateRequest request) {
        PatientDto updated = patientService.updatePatient(request, id);
        return ResponseEntity.ok(new ApiResponse("Patient updated successfully", updated));
    }

    // 🔹 Delete patient
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePatient(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.ok(new ApiResponse("Patient deleted successfully", null));
    }

    // 🔹 Get all patients
    @GetMapping
    public ResponseEntity<ApiResponse> getAllPatients() {
        List<PatientDto> patients = patientService.getAllPatients();
        return ResponseEntity.ok(new ApiResponse("Patients retrieved successfully", patients));
    }

    // 🔹 Search with multiple filters
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchPatients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String disease,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String bloodType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfRegistration) {

        List<PatientDto> patients = patientService.searchPatients(name, disease, gender, bloodType, dateOfRegistration);
        return ResponseEntity.ok(new ApiResponse("Search results", patients));
    }

    // 🔹 Get by name
    @GetMapping("/by-name")
    public ResponseEntity<ApiResponse> getPatientsByName(@RequestParam String name) {
        List<PatientDto> patients = patientService.getPatientsByName(name);
        return ResponseEntity.ok(new ApiResponse("Patients retrieved by name", patients));
    }

    // 🔹 Get by name and disease
    @GetMapping("/by-name-disease")
    public ResponseEntity<ApiResponse> getPatientsByNameAndDisease(@RequestParam String name, @RequestParam String disease) {
        List<PatientDto> patients = patientService.getPatientsByNameAndDisease(name, disease);
        return ResponseEntity.ok(new ApiResponse("Patients retrieved by name and disease", patients));
    }

    // 🔹 Get by disease
    @GetMapping("/by-disease")
    public ResponseEntity<ApiResponse> getPatientsByDisease(@RequestParam String disease) {
        List<PatientDto> patients = patientService.getPatientsByDisease(disease);
        return ResponseEntity.ok(new ApiResponse("Patients retrieved by disease", patients));
    }

    // 🔹 Get by disease and gender
    @GetMapping("/by-disease-gender")
    public ResponseEntity<ApiResponse> getPatientsByDiseaseAndGender(@RequestParam String disease, @RequestParam String gender) {
        List<PatientDto> patients = patientService.getPatientsByDiseaseAndGender(disease, gender);
        return ResponseEntity.ok(new ApiResponse("Patients retrieved by disease and gender", patients));
    }

    // 🔹 Get by disease, gender, and blood type
    @GetMapping("/by-disease-gender-blood")
    public ResponseEntity<ApiResponse> getPatientsByDiseaseAndGenderAndBloodType(
            @RequestParam String disease,
            @RequestParam String gender,
            @RequestParam String bloodType) {
        List<PatientDto> patients = patientService.getPatientsByDiseaseAndGenderAndBloodType(disease, gender, bloodType);
        return ResponseEntity.ok(new ApiResponse("Patients retrieved by disease, gender, and blood type", patients));
    }

    // 🔹 Get by registration date
    @GetMapping("/by-date")
    public ResponseEntity<ApiResponse> getPatientsByDateOfRegistration(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfRegistration) {
        List<PatientDto> patients = patientService.getPatientsByDateOfRegistration(dateOfRegistration);
        return ResponseEntity.ok(new ApiResponse("Patients retrieved by date of registration", patients));
    }

    // 🔹 Get by date and disease
    @GetMapping("/by-date-disease")
    public ResponseEntity<ApiResponse> getPatientsByDateOfRegistrationAndDisease(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfRegistration,
            @RequestParam String disease) {
        List<PatientDto> patients = patientService.getPatientsByDateOfRegistrationAndDisease(dateOfRegistration, disease);
        return ResponseEntity.ok(new ApiResponse("Patients retrieved by date and disease", patients));
    }
}
