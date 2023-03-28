package com.ecomerce.Ecomerce.V1.service.interfaces;

import com.ecomerce.Ecomerce.V1.dto.account.AccountRequest;
import com.ecomerce.Ecomerce.V1.dto.account.AccountResponse;
import com.ecomerce.Ecomerce.V1.model.Account;
import com.ecomerce.Ecomerce.V1.model.enums.Role;

import java.util.List;

public interface AccountService {
    AccountResponse create(AccountRequest request);
    List<AccountResponse> getAll();
    Account accountContext();
    String selectRole(Role role);
    Account accountByEmail(String email);
}
