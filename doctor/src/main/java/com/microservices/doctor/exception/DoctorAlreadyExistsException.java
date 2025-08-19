package com.microservices.doctor.exception;

public class DoctorAlreadyExistsException extends RuntimeException {
    public DoctorAlreadyExistsException(String doctorWithEmailAlreadyExists) {
        super(doctorWithEmailAlreadyExists);
    }
}
