package com.testing.app.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse<T> {

    private HttpStatus httpStatus;
    private String message;
    private String path;
    private String api;
    @Builder.Default
    private ZonedDateTime timeStamp = ZonedDateTime.now();
    private T data;
}
