package com.microservices.patient.service;



import com.microservices.patient.dto.PatientDto;
import com.microservices.patient.request.*;

import java.util.Date;
import java.util.List;

public interface IPatientService {

    PatientDto getPatientById(Long patientId);

    PatientDto createPatient(PatientCreateRequest request);

    PatientDto updatePatient(PatientUpdateRequest request, Long patientId);

    void deletePatientById(Long patientId);

    List<PatientDto> getAllPatients();

    List<PatientDto> getPatientsByName(String name);

    List<PatientDto> getPatientsByNameAndDisease(String name, String disease);

    List<PatientDto> getPatientsByDisease(String disease);

    List<PatientDto> getPatientsByDiseaseAndGender(String disease, String gender);

    List<PatientDto> getPatientsByDiseaseAndGenderAndBloodType(String disease, String gender, String bloodType);

    List<PatientDto> getPatientsByDateOfRegistration(Date dateOfRegistration);

    List<PatientDto> getPatientsByDateOfRegistrationAndDisease(Date dateOfRegistration, String disease);

    List<PatientDto> searchPatients(String name, String disease, String gender, String bloodType, Date dateOfRegistration);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
