package com.testing.app.domain.customer;

import com.testing.app.common.entity.AbstractEntity;
import com.testing.app.domain.loyalty.Loyalty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity<Long> {

    private String name;

    private String email;

    private String address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<Loyalty> loyalties = new HashSet<>();
}
