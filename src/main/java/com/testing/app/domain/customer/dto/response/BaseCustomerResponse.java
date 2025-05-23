package com.testing.app.domain.customer.dto.response;

import com.testing.app.common.response.BaseResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseCustomerResponse extends BaseResponse<Long> {
    String name;

    String email;

    String address;
}
