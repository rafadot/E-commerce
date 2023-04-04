package com.ecomerce.Ecomerce.V1.controller;

import com.ecomerce.Ecomerce.V1.dto.account.AccountRequest;
import com.ecomerce.Ecomerce.V1.dto.account.AccountResponse;
import com.ecomerce.Ecomerce.V1.dto.address.AddressRequest;
import com.ecomerce.Ecomerce.V1.model.Address;
import com.ecomerce.Ecomerce.V1.model.enums.RoleType;
import com.ecomerce.Ecomerce.V1.service.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> create(@RequestBody @Valid AccountRequest request){
        return new ResponseEntity<>(accountService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/add-role")
    public ResponseEntity<Map<String, String>> addRole(@RequestParam RoleType type, @RequestParam(defaultValue = "") String nameIfBrand){
        return new ResponseEntity<>(accountService.addRole(type,nameIfBrand),HttpStatus.OK);
    }

    @GetMapping("/remove-role")
    public ResponseEntity<Map<String, String>> removeRole(@RequestParam RoleType type) throws IOException {
        return new ResponseEntity<>(accountService.removeRole(type),HttpStatus.OK);
    }

    @DeleteMapping("/account-delete")
    public ResponseEntity<String> accountDelete(){
        return new ResponseEntity<>(accountService.accountDelete(),HttpStatus.OK);
    }

    @PostMapping("/create-address")
    public ResponseEntity<Address> createAddress(@RequestBody @Valid AddressRequest addressRequest){
        return new ResponseEntity<>(accountService.createAddress(addressRequest),HttpStatus.CREATED);
    }

    @DeleteMapping("/remove-address")
    public ResponseEntity<String> removeAddress(@RequestParam UUID addressId){
        return new ResponseEntity<>(accountService.removeAddress(addressId),HttpStatus.OK);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAddresses(){
        return new ResponseEntity<>(accountService.getAddresses(),HttpStatus.OK);
    }
}
