package org.mekkaoui.ebankservice.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;
import org.mekkaoui.ebankservice.model.Customer;

import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccount {
    @Id @GeneratedValue
    private String id;
    private Date createdAt;
    private double balance;
    private String type;
    private Long customerId;

    @Transient
    private Customer customer;
}
