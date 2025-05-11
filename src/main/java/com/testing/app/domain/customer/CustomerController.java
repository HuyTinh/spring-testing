package com.testing.app.domain.customer;

import com.testing.app.domain.customer.dto.request.CreateCustomerRequest;
import com.testing.app.domain.customer.dto.response.BaseCustomerResponse;
import com.testing.app.exception.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public APIResponse<List<BaseCustomerResponse>> getAllCustomer() {
        return APIResponse.<List<BaseCustomerResponse>>builder()
                          .httpStatus(HttpStatus.OK)
                          .message("Lấy danh sách khách hàng thành công.")
                          .data(customerService.getCustomers())
                          .build();
    }

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
