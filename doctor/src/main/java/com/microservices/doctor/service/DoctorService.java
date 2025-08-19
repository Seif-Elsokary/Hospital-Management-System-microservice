package com.microservices.doctor.service;

import com.microservices.doctor.dto.DoctorDto;
import com.microservices.doctor.entity.Doctor;

import com.microservices.doctor.exception.DoctorAlreadyExistsException;
import com.microservices.doctor.exception.DoctorNotFoundException;
import com.microservices.doctor.request.DoctorCreateRequest;
import com.microservices.doctor.request.DoctorUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {

    private final com.microservices.doctor.repository.DoctorRepository doctorRepository;

    @Override
    public DoctorDto createDoctor(DoctorCreateRequest request) {
        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new DoctorAlreadyExistsException("Doctor with email already exists");
        }
        if (doctorRepository.existsByPhone(request.getPhone())) {
            throw new DoctorAlreadyExistsException("Doctor with phone already exists");
        }

        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phone(request.getPhone())
                .age(request.getAge())
                .yearOfExperience(request.getYearOfExperience())
                .specialty(request.getSpecialty())
                .address(request.getAddress())
                .gender(request.getGender())
                .build();

        return toDto(doctorRepository.save(doctor));
    }

    @Override
    public DoctorDto updateDoctor(DoctorUpdateRequest request, Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        doctor.setName(request.getName());
        doctor.setEmail(request.getEmail());
        doctor.setPassword(request.getPassword());
        doctor.setPhone(request.getPhone());
        doctor.setAge(request.getAge());
        doctor.setYearOfExperience(request.getYearOfExperience());
        doctor.setSpecialty(request.getSpecialty());
        doctor.setAddress(request.getAddress());
        doctor.setGender(request.getGender());

        return toDto(doctorRepository.save(doctor));
    }

    @Override
    public DoctorDto getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
    }

    @Override
    public void deleteDoctorById(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new DoctorNotFoundException("Doctor not found");
        }
        doctorRepository.deleteById(id);
    }

    @Override
    public List<DoctorDto> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDto> getDoctorsByName(String name) {
        return doctorRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDto> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialtyIgnoreCase(specialty).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDto> searchDoctors(String name, String specialty, String gender, Integer minExperience) {
        return doctorRepository.searchDoctors(name, specialty, gender, minExperience).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        return doctorRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return doctorRepository.existsByPhone(phone);
    }

    private DoctorDto toDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getDoctorId())
                .name(doctor.getName())
                .email(doctor.getEmail())
                .phone(doctor.getPhone())
                .age(doctor.getAge())
                .yearOfExperience(doctor.getYearOfExperience())
                .specialty(doctor.getSpecialty())
                .address(doctor.getAddress())
                .gender(doctor.getGender())
                .build();
    }
}
