package com.ecommerce.Ecommerce.V1.service.interfaces;

import com.ecommerce.Ecommerce.V1.dto.AddressRequest;
import com.ecommerce.Ecommerce.V1.model.Account;
import com.ecommerce.Ecommerce.V1.model.Address;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    Address create(AddressRequest addressRequest, Account account);
    String remove(Account account, UUID addressId);
    List<Address> getAddresses(Account account);
}
