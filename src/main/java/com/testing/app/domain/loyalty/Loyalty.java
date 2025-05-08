package com.testing.app.domain.loyalty;

import com.testing.app.common.entity.AbstractEntity;
import com.testing.app.domain.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "loyalties")
public class Loyalty extends AbstractEntity<Long> {

    @Column(name = "point_balance", columnDefinition = "INT4")
    private Integer pointBalance;

    private LoyaltyTier tier;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
