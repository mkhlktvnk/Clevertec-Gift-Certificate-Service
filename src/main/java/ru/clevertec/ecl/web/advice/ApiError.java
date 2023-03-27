package ru.clevertec.ecl.web.advice;

import lombok.Builder;

@Builder
public class ApiError {
    private String message;
    private Integer statusCode;
}
