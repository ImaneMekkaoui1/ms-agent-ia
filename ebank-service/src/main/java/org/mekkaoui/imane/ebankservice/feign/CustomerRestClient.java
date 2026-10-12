package org.mekkaoui.imane.ebankservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.mekkaoui.imane.ebankservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerRestClient {

    @GetMapping("/customers/{id}")
    @CircuitBreaker(name = "customerService", fallbackMethod = "getDefaultCustomer")
    Customer getCustomerById(@PathVariable Long id);

    default Customer getDefaultCustomer(Long id, Throwable e) {
        return new Customer(id, "default", "noEmail");
    }
}