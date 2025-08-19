package com.microservices.patient.service;

import com.microservices.patient.dto.PatientDto;
import com.microservices.patient.entity.Patient;
import com.microservices.patient.exception.*;
import com.microservices.patient.repository.PatientRepository;
import com.microservices.patient.request.PatientCreateRequest;
import com.microservices.patient.request.PatientUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    private PatientDto mapToDto(Patient patient) {
        return PatientDto.builder()
                .patientId(patient.getPatientId())
                .name(patient.getName())
                .age(patient.getAge())
                .phone(patient.getPhone())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .disease(patient.getDisease())
                .bloodType(patient.getBloodType())
                .dateOfRegistration(patient.getDateOfRegistration())
                .gender(patient.getGender())
                .build();
    }

    @Override
    public PatientDto getPatientById(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));
        return mapToDto(patient);
    }

    @Override
    public PatientDto createPatient(PatientCreateRequest request) {
        if (patientRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new AlreadyExistsException("Email already exists: " + request.getEmail());
        }
        if (patientRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already exists: " + request.getPhone());
        }

        Patient patient = Patient.builder()
                .name(request.getName())
                .age(request.getAge())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(request.getPassword())
                .address(request.getAddress())
                .disease(request.getDisease())
                .bloodType(request.getBloodType())
                .dateOfRegistration(request.getDateOfRegistration())
                .gender(request.getGender())
                .build();

        return mapToDto(patientRepository.save(patient));
    }

    @Override
    public PatientDto updatePatient(PatientUpdateRequest request, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(patient.getEmail())
                && patientRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        if (request.getPhone() != null && !request.getPhone().equals(patient.getPhone())
                && patientRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already exists: " + request.getPhone());
        }

        // Update fields if provided
        if (request.getName() != null) patient.setName(request.getName());
        if (request.getAge() != null) patient.setAge(request.getAge());
        if (request.getPhone() != null) patient.setPhone(request.getPhone());
        if (request.getEmail() != null) patient.setEmail(request.getEmail());
        if (request.getPassword() != null) patient.setPassword(request.getPassword());
        if (request.getAddress() != null) patient.setAddress(request.getAddress());
        if (request.getDisease() != null) patient.setDisease(request.getDisease());
        if (request.getBloodType() != null) patient.setBloodType(request.getBloodType());
        if (request.getDateOfRegistration() != null) patient.setDateOfRegistration(request.getDateOfRegistration());
        if (request.getGender() != null) patient.setGender(request.getGender());

        return mapToDto(patientRepository.save(patient));
    }

    @Override
    public void deletePatientById(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new RuntimeException("Patient not found with ID: " + patientId);
        }
        patientRepository.deleteById(patientId);
    }

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> getPatientsByName(String name) {
        return patientRepository.findByNameContainingIgnoreCase(name)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> getPatientsByNameAndDisease(String name, String disease) {
        return patientRepository.findByNameContainingIgnoreCaseAndDiseaseIgnoreCase(name, disease)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> getPatientsByDisease(String disease) {
        return patientRepository.findByDiseaseIgnoreCase(disease)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> getPatientsByDiseaseAndGender(String disease, String gender) {
        return patientRepository.findByDiseaseIgnoreCaseAndGenderIgnoreCase(disease, gender)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> getPatientsByDiseaseAndGenderAndBloodType(String disease, String gender, String bloodType) {
        return patientRepository.findByDiseaseIgnoreCaseAndGenderIgnoreCaseAndBloodTypeIgnoreCase(disease, gender, bloodType)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> getPatientsByDateOfRegistration(Date dateOfRegistration) {
        return patientRepository.findByDateOfRegistration(dateOfRegistration)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> getPatientsByDateOfRegistrationAndDisease(Date dateOfRegistration, String disease) {
        return patientRepository.findByDateOfRegistrationAndDiseaseIgnoreCase(dateOfRegistration, disease)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> searchPatients(String name, String disease, String gender, String bloodType, Date dateOfRegistration) {
        return patientRepository.findAll()
                .stream()
                .filter(p -> name == null || p.getName().equalsIgnoreCase(name))
                .filter(p -> disease == null || p.getDisease().equalsIgnoreCase(disease))
                .filter(p -> gender == null || p.getGender().equalsIgnoreCase(gender))
                .filter(p -> bloodType == null || p.getBloodType().equalsIgnoreCase(bloodType))
                .filter(p -> dateOfRegistration == null || p.getDateOfRegistration().equals(dateOfRegistration))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        return patientRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return patientRepository.existsByPhone(phone);
    }
}
