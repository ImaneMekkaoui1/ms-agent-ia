package org.mekkaoui.imane.customerservice.service;

import org.mekkaoui.imane.customerservice.entities.Customer;
import org.mekkaoui.imane.customerservice.reposistory.CustomerRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @McpTool(description="Get All customers")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    @McpTool(description="Find customer by id")
    public Customer findCustomerById(@McpToolParam(description="the customer id") Long id){
        return customerRepository.findById(id)
                .orElseThrow(()->new RuntimeException("customer not found"));
    }
    @McpTool(description="save a new customer")
    public Customer saveCustomer(@McpToolParam(description = "the customer to save (name,email)") Customer customer){
        return customerRepository.save(customer);
    }
}
