package com.ecomerce.Ecomerce.V1.service.interfaces;

import com.ecomerce.Ecomerce.V1.dto.account.AccountRequest;
import com.ecomerce.Ecomerce.V1.dto.account.AccountResponse;
import com.ecomerce.Ecomerce.V1.model.Account;

import java.util.List;

public interface AccountService {
    AccountResponse create(AccountRequest request);
    List<AccountResponse> getAll();
    Account accountContext();
    Account accountByEmail(String email);
}
