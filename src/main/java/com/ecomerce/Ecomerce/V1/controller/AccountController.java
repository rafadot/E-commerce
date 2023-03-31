package com.ecomerce.Ecomerce.V1.controller;

import com.ecomerce.Ecomerce.V1.dto.account.AccountRequest;
import com.ecomerce.Ecomerce.V1.dto.account.AccountResponse;
import com.ecomerce.Ecomerce.V1.service.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/get")
    public ResponseEntity<List<AccountResponse>> getAll(){
        return new ResponseEntity<>(accountService.getAll(),HttpStatus.OK);
    }

}
