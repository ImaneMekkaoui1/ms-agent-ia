package org.mekkaoui.imane.customerservice.reposistory;

import org.mekkaoui.imane.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
