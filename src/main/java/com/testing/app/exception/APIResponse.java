package com.testing.app.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class APIResponse<T> {

    private HttpStatus httpStatus;
    private String message;
    private String path;
    private String api;
    @Builder.Default
    private LocalDateTime timeStamp = LocalDateTime.now();
    private T data;
}
