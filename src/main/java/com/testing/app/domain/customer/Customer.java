package com.testing.app.domain.customer;

import com.testing.app.common.entity.AbstractEntity;
import com.testing.app.common.entity.watch_set.TrackedSet;
import com.testing.app.domain.loyalty.Loyalty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity<Long> {

    private String name;

    private String email;

    private String address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private TrackedSet<Loyalty> loyalties = new TrackedSet<>();
}
