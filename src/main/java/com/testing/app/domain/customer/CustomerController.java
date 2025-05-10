package com.testing.app.domain.customer;

import com.testing.app.domain.customer.dto.request.CreateCustomerRequest;
import com.testing.app.domain.customer.dto.response.BaseCustomerResponse;
import com.testing.app.exception.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public APIResponse<BaseCustomerResponse> createCustomer(
            @RequestBody @Valid CreateCustomerRequest request
                                                           ) {
        return APIResponse.<BaseCustomerResponse>builder()
                          .httpStatus(HttpStatus.OK)
                          .message("Thêm mới thành công.")
                          .data(customerService.createCustomer(request))
                          .build();
    }
}
