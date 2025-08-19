package com.microservices.patient.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String s) {
        super(s);
    }
}
