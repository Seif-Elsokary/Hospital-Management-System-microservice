package com.microservices.doctor.exception;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String doctorNotFound) {
        super(doctorNotFound);
    }
}
