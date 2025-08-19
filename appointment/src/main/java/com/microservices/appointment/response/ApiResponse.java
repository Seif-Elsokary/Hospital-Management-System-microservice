package com.microservices.appointment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ApiResponse {
    private String message;
    private Object data;
}
