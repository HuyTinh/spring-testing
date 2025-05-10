package com.testing.app.domain.customer.dto.request;

import jakarta.validation.Valid;
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
    private Set<@Valid CreateCustomerLoyaltyRequest> loyalties = new HashSet<>();
}
