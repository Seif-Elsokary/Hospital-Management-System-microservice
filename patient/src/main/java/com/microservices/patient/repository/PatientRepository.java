package com.microservices.patient.repository;

import com.microservices.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Basic finders
    List<Patient> findByNameContainingIgnoreCase(String name);

    List<Patient> findByNameContainingIgnoreCaseAndDiseaseIgnoreCase(String name, String disease);

    List<Patient> findByDiseaseIgnoreCase(String disease);

    List<Patient> findByDiseaseIgnoreCaseAndGenderIgnoreCase(String disease, String gender);

    List<Patient> findByDiseaseIgnoreCaseAndGenderIgnoreCaseAndBloodTypeIgnoreCase(String disease, String gender, String bloodType);

    List<Patient> findByDateOfRegistration(Date dateOfRegistration);

    List<Patient> findByDateOfRegistrationAndDiseaseIgnoreCase(Date dateOfRegistration, String disease);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhone(String phone);

    // Optimized search query
    @Query("""
           SELECT p FROM Patient p
           WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
           AND (:disease IS NULL OR LOWER(p.disease) = LOWER(:disease))
           AND (:gender IS NULL OR LOWER(p.gender) = LOWER(:gender))
           AND (:bloodType IS NULL OR LOWER(p.bloodType) = LOWER(:bloodType))
           AND (:dateOfRegistration IS NULL OR p.dateOfRegistration = :dateOfRegistration)
           """)
    List<Patient> searchPatients(
            @Param("name") String name,
            @Param("disease") String disease,
            @Param("gender") String gender,
            @Param("bloodType") String bloodType,
            @Param("dateOfRegistration") Date dateOfRegistration
    );
}
