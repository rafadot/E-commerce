package com.ecomerce.Ecomerce.V1.service.interfaces;

import com.ecomerce.Ecomerce.V1.dto.address.AddressRequest;
import com.ecomerce.Ecomerce.V1.model.Account;
import com.ecomerce.Ecomerce.V1.model.Address;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    Address create(AddressRequest addressRequest, Account account);
    String remove(Account account, UUID addressId);
    List<Address> getAddresses(Account account);
}
