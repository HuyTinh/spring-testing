package com.testing.app.customer.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseCustomerResponse {
    private String name;

    private String email;

    private String address;
}
