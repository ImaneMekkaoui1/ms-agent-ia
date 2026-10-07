package org.mekkaoui.ebankservice.service;


import org.mekkaoui.ebankservice.entities.BankAccount;
import org.mekkaoui.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private BankAccountRepository accountRepository;

    public EbankService(BankAccountRepository accountRepository) {
        this.accountRepository = accountRepository;

    }

    public List<BankAccount> getAllBankAccounts() {
        return accountRepository.findAll();

    }
    public BankAccount getBankAccountById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Bank account not found"));
    }

    public BankAccount saveBankAccount(BankAccount bankAccount) {

        bankAccount.setCreatedAt(new Date());
        return accountRepository.save(bankAccount);
    }
}
