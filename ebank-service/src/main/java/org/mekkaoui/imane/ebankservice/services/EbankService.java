package org.mekkaoui.imane.ebankservice.services;

import org.mekkaoui.imane.ebankservice.entities.BankAccount;
import org.mekkaoui.imane.ebankservice.feign.CustomerRestClient;
import org.mekkaoui.imane.ebankservice.model.Customer;
import org.mekkaoui.imane.ebankservice.repository.BankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRestClient customerRestClient;

    public EbankService(BankAccountRepository bankAccountRepository, CustomerRestClient customerRestClient) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRestClient = customerRestClient;
    }
    @McpTool(description = "Get all bank accounts")
    public List<BankAccount> getAllBankAccounts(){
        return bankAccountRepository.findAll();
    }
    @McpTool(description = "Get bank account by id")
    public BankAccount getBankAccountById(@McpToolParam(description = "bank account id") String id){
        BankAccount bankAccount =  bankAccountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account not found"));
        bankAccount.setCustomer(customerRestClient.getCustomerById(bankAccount.getCustomerId()));
        return bankAccount;
    }
    @McpTool(description = "save new bank account")
    public BankAccount save(@McpToolParam(description = "bank account to save(balance,type,customerId)") BankAccount bankAccount){
        try{
            Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreatedAt(new Date());
            return bankAccountRepository.save(bankAccount);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
