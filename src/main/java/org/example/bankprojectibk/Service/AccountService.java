package org.example.bankprojectibk.Service;

import org.example.bankprojectibk.Model.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Long userId, String name);
    List<Account> myAccounts(Long userId);
}
