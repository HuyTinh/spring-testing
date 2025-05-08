package com.testing.app.domain.customer.dto.response;

import com.testing.app.common.response.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseCustomerResponse extends BaseResponse<Long> {
    private String name;

    private String email;

    private String address;
}
