package com.testing.app.controller.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseCustomerRequest {

    @NotEmpty(message = "Không được để trống tên khách hàng.")
    @Size(min = 5, message = "Tên khách hàng phải có ít nhất 5 ký tự.")
    private String name;

    @NotEmpty(message = "Không được để trống email khách hàng.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Không đúng định dạng email khách hàng.")
    private String email;

    @NotEmpty(message = "Không được để trống địa chỉ khách hàng.")
    @Size(min = 10, message = "Địa chỉ khách hàng phải có ít nhất 10 ký tự.")
    private String address;
}
