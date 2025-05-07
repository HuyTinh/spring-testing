package com.testing.app.customer;

import com.testing.app.customer.dto.request.CreateCustomerRequest;
import com.testing.app.customer.dto.response.BaseCustomerResponse;
import com.testing.app.exception.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ApiResponse<BaseCustomerResponse> createCustomer(
            @RequestBody @Valid CreateCustomerRequest request
                                                           ) {
        return ApiResponse.<BaseCustomerResponse>builder()
                          .httpStatus(HttpStatus.OK)
                          .message("Thêm mới thành công.")
                          .data(customerService.createCustomer(request))
                          .build();
    }
}
