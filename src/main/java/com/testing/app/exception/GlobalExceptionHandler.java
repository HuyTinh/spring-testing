package com.testing.app.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Xử lý ngoại lệ email khách hàng không hợp lệ.
     *
     * @param ex      là ngoại lệ (exception) email khách hàng không hợp lệ.
     * @param request là yêu cầu (request) của người dùng (client).
     * @param method  là phương thức (method) xử lý yêu cầu (request) của người dùng (client).
     * @return phản hồi {@code ApiErrorResponse} cho người dùng (client).
     **/
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomerEmailUnavailableException.class)
    public ApiErrorResponse handleCustomerEmailUnavailableException(CustomerEmailUnavailableException ex,
                                                                    HttpServletRequest request,
                                                                    HandlerMethod method) {
        return ApiErrorResponse.builder()
                               .httpStatus(HttpStatus.CONFLICT)
                               .message(ex.getMessage())
                               .path(request.getRequestURI())
                               .api(method.getMethod().getName())
                               .timeStamp(ZonedDateTime.now())
                               .build();
    }

    /**
     * Xử lý ngoại lệ không tìm thấy khách hàng.
     *
     * @param ex      là ngoại lệ (exception) không tìm thấy khách hàng.
     * @param request là yêu cầu (request) của người dùng (client).
     * @param method  là phương thức (method) xử lý yêu cầu (request) của người dùng (client).
     * @return phản hồi {@code ApiErrorResponse} cho người dùng (client).
     **/
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public ApiErrorResponse handleCustomerNotFoundException(CustomerNotFoundException ex,
                                                            HttpServletRequest request,
                                                            HandlerMethod method) {
        return ApiErrorResponse.builder()
                               .httpStatus(HttpStatus.NOT_FOUND)
                               .message(ex.getMessage())
                               .path(request.getRequestURI())
                               .api(method.getMethod().getName())
                               .timeStamp(ZonedDateTime.now())
                               .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleValidationException(MethodArgumentNotValidException ex,
                                                      HttpServletRequest request,
                                                      HandlerMethod method) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors().stream()
                                    .collect(Collectors.toList());

        FieldError firstError = errors.getFirst();
        String message = firstError.getDefaultMessage();

        return ApiErrorResponse.builder()
                               .httpStatus(HttpStatus.BAD_REQUEST)
                               .message(message)
                               .path(request.getRequestURI())
                               .api(method.getMethod().getName())
                               .timeStamp(ZonedDateTime.now())
                               .build();
    }
}
