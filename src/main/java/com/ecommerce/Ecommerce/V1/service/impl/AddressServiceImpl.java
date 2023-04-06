package com.ecommerce.Ecommerce.V1.service.impl;

import com.ecommerce.Ecommerce.V1.dto.AddressRequest;
import com.ecommerce.Ecommerce.V1.model.Account;
import com.ecommerce.Ecommerce.V1.model.Address;
import com.ecommerce.Ecommerce.V1.repository.AccountRepository;
import com.ecommerce.Ecommerce.V1.repository.AddressRepository;
import com.ecommerce.Ecommerce.V1.service.interfaces.AddressService;
import com.ecommerce.Ecommerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;

    @Override
    public Address create(AddressRequest addressRequest, Account account) {
        Address address = new Address();
        BeanUtils.copyProperties(addressRequest,address);
        address.setAccount(account);
        addressRepository.save(address);
        return address;
    }

    @Override
    public String remove(Account account, UUID addressId) {
        for (Address address : account.getAddresses()) {
            if (address.getId().equals(addressId)) {
                account.getAddresses().remove(address);
                accountRepository.save(account);
                addressRepository.delete(address);
                return "Endereço removido com sucesso!";
            }
        }

        throw new BadRequestException("Endereço não existe!");
    }

    @Override
    public List<Address> getAddresses(Account account) {
        return account.getAddresses();
    }
}
