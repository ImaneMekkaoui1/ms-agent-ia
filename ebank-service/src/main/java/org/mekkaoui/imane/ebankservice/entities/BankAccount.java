package org.mekkaoui.imane.ebankservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;
import org.mekkaoui.imane.ebankservice.model.Customer;

import java.util.Date;
@Entity @AllArgsConstructor @NoArgsConstructor @Builder @Getter @Setter
public class BankAccount {
    @Id
    private String id;
    private Date createdAt;
    private double balance;
    private String type;
    private long customerId;
    @Transient
    private Customer customer;
}
