package com.microservices.doctor.repository;

import com.microservices.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<Doctor> findByNameContainingIgnoreCase(String name);

    List<Doctor> findBySpecialtyIgnoreCase(String specialty);

    @Query("""
            SELECT d FROM Doctor d
            WHERE (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:specialty IS NULL OR LOWER(d.specialty) = LOWER(:specialty))
              AND (:gender IS NULL OR LOWER(d.gender) = LOWER(:gender))
              AND (:minExperience IS NULL OR d.yearOfExperience >= :minExperience)
           """)
    List<Doctor> searchDoctors(String name, String specialty, String gender, Integer minExperience);
}
