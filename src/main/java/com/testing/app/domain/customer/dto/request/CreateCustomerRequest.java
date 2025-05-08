package com.testing.app.domain.customer.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
public class CreateCustomerRequest extends BaseCustomerRequest {
    @Builder.Default
    private Set<CreateCustomerLoyaltyRequest> loyalties = new HashSet<>();
}
