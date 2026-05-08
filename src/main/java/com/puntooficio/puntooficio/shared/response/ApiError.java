package com.puntooficio.puntooficio.shared.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ApiError {

    private final int status;
    private final String error;
    private final String message;
    private final List<String> details;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    public ApiError(HttpStatus httpStatus, String message, List<String> details) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, String message) {
        this(httpStatus, message, List.of());
    }
}