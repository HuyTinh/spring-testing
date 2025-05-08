package com.testing.app.domain.loyalty.dto.request;

import com.testing.app.domain.loyalty.LoyaltyTier;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseLoyaltyRequest {
    private Integer pointBalance;

    private LoyaltyTier tier;
}
