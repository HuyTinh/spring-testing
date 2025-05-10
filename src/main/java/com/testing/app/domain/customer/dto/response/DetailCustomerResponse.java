package com.testing.app.domain.customer.dto.response;

import com.testing.app.domain.loyalty.dto.response.BaseLoyaltyResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailCustomerResponse extends BaseCustomerResponse {
    @Builder.Default
    Set<BaseLoyaltyResponse> loyalties = new HashSet<>();
}
