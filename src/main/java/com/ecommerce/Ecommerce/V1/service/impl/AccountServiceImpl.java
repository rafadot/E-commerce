package com.ecommerce.Ecommerce.V1.service.impl;

import com.ecommerce.Ecommerce.V1.dto.account.AccountRequest;
import com.ecommerce.Ecommerce.V1.dto.account.AccountResponse;
import com.ecommerce.Ecommerce.V1.dto.AddressRequest;
import com.ecommerce.Ecommerce.V1.model.*;
import com.ecommerce.Ecommerce.V1.model.enums.RoleType;
import com.ecommerce.Ecommerce.V1.repository.AccountRepository;
import com.ecommerce.Ecommerce.V1.repository.BrandRepository;
import com.ecommerce.Ecommerce.V1.repository.ProductsCartRepository;
import com.ecommerce.Ecommerce.V1.repository.RoleRepository;
import com.ecommerce.Ecommerce.V1.service.interfaces.AccountService;
import com.ecommerce.Ecommerce.V1.service.interfaces.AddressService;
import com.ecommerce.Ecommerce.V1.service.interfaces.BrandService;
import com.ecommerce.Ecommerce.V1.util.AccountUtil;
import com.ecommerce.Ecommerce.V1.util.CloudUtil;
import com.ecommerce.Ecommerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final BrandService brandService;
    private final BrandRepository brandRepository;
    private final AddressService addressService;
    private final ProductsCartRepository cartRepository;

    @Override
    public AccountResponse create(AccountRequest request) {
        if(accountRepository.findByEmail(request.getEmail()).isPresent())
            throw new BadRequestException("E-mail já cadastrado");

        PasswordRecovery passwordRecovery = PasswordRecovery
                .builder()
                .verified(false)
                .build();

        Account account = new Account();
        BeanUtils.copyProperties(request,account);
        account.getRole().add(roleRepository.findByName("USER"));
        account.setPassword(encoder.encode(account.getPassword()));
        account.setPasswordRecovery(passwordRecovery);
        accountRepository.save(account);

        AccountResponse response = new AccountResponse();
        BeanUtils.copyProperties(account,response,"role");
        response.setRole(account.getRole());
        return response;
    }

    @Override
    public Map<String, String> addRole(RoleType type , String nameIfBrand) {
        Account account = accountContext();
        if(account.getRole().size() == 3)
            throw new BadRequestException("Você ja possui todas as funções");

        if(AccountUtil.containRole(account.getRole(),type.toString()))
            throw new BadRequestException("Você já possui esta Role");

        if(type.toString().equals("BRAND") && nameIfBrand.equals(""))
            throw new BadRequestException("Para se tornar uma marca você precisa informar um nome para ela!");

        if(!type.toString().equals("BRAND")){
            account.getRole().add(roleRepository.findByName(type.toString()));
            accountRepository.save(account);

            Map<String,String> response = new HashMap<>();
            response.put("message", account.getFullName().split(" ")[0] + ", " + type + " foi adcionado a sua lista de funções!");
            response.put("roles", account.getRole().stream().map(Role::getName).collect(Collectors.toList()).toString());

            return response;
        }

        Brand brand = brandService.create(nameIfBrand);
        account.setBrand(brand);
        account.getRole().add(roleRepository.findByName(type.toString()));
        accountRepository.save(account);

        Map<String,String> response = new HashMap<>();
        response.put("brandMessage",nameIfBrand + " criado(a) com sucesso!");
        response.put("message", account.getFullName().split(" ")[0] + ", " + type + " foi adcionado a sua lista de funções!");
        response.put("roles", account.getRole().stream().map(Role::getName).collect(Collectors.toList()).toString());

        return response;
    }

    @Transactional
    @Override
    public Map<String, String> removeRole(RoleType type) throws IOException {
        Account account = accountContext();

        if(!AccountUtil.containRole(account.getRole(),type.toString()))
            throw new BadRequestException("Você não possui esta Role");

        if(account.getRole().size() == 1) {
            throw new BadRequestException("Você precisa ter ao menos 1 função");
        }

        Map<String,String> response = new HashMap<>();

        if(type.toString().equals("BRAND")){
            Brand brand = account.getBrand();
            response.put("brandMessage",brand.getName() + " deixou de ser uma marca!");

            for(Product product : brand.getProduct()){
                cartRepository.deleteAllByProductId(product.getId());
                if(product.getProfile() != null)
                    CloudUtil.deleteImage(product.getProfile());
            }

            account.setBrand(null);
            accountRepository.save(account);
            brandRepository.delete(brand);
        }

        account.getRole().remove(roleRepository.findByName(type.toString()));
        accountRepository.save(account);

        response.put("message", account.getFullName().split(" ")[0] + ", " + type + " foi removido(a) da sua lista de funções!");
        response.put("roles", account.getRole().stream().map(Role::getName).collect(Collectors.toList()).toString());

        return response;
    }

    @Override
    public Account accountContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null  && !authentication.isAuthenticated())
            throw new BadRequestException("Sua sessão expirou. Por favor faça o login novamente!");

        return accountRepository
                .findByEmail(authentication.getName())
                .orElseThrow(()-> new BadRequestException("Email não cadastrado!"));
    }

    @Override
    public Account accountByEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isEmpty())
            throw new BadRequestException("E-mail não cadastrado!");

        return account.get();
    }

    @Transactional
    @Override
    public String accountDelete() {
        Account account = accountContext();
        account.getRole().clear();
        cartRepository.deleteAllByAccountId(account.getId());
        accountRepository.delete(account);
        return "Sua conta foi deletado com sucesso!";
    }

    @Override
    public Address createAddress(AddressRequest addressRequest) {
        Account account = accountContext();
        return addressService.create(addressRequest,account);
    }

    @Override
    public String removeAddress(UUID addressID) {
        return addressService.remove(accountContext(),addressID);
    }

    @Override
    public List<Address> getAddresses() {
        return addressService.getAddresses(accountContext());
    }

}
