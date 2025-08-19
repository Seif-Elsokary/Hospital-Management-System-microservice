package com.microservices.doctor.service;

import com.microservices.doctor.dto.DoctorDto;
import com.microservices.doctor.request.DoctorCreateRequest;
import com.microservices.doctor.request.DoctorUpdateRequest;

import java.util.List;

public interface IDoctorService {

    DoctorDto createDoctor(DoctorCreateRequest request);

    DoctorDto updateDoctor(DoctorUpdateRequest request, Long id);

    DoctorDto getDoctorById(Long id);

    void deleteDoctorById(Long id);

    List<DoctorDto> getAllDoctors();

    List<DoctorDto> getDoctorsByName(String name);

    List<DoctorDto> getDoctorsBySpecialty(String specialty);

    List<DoctorDto> searchDoctors(String name, String specialty, String gender, Integer minExperience);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
