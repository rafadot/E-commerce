package com.ecomerce.Ecomerce.V1.service.impl;

import com.ecomerce.Ecomerce.V1.dto.voucher.VoucherRequest;
import com.ecomerce.Ecomerce.V1.dto.voucher.VoucherResponse;
import com.ecomerce.Ecomerce.V1.model.Account;
import com.ecomerce.Ecomerce.V1.model.Voucher;
import com.ecomerce.Ecomerce.V1.repository.VoucherRepository;
import com.ecomerce.Ecomerce.V1.service.interfaces.AccountService;
import com.ecomerce.Ecomerce.V1.service.interfaces.VoucherService;
import com.ecomerce.Ecomerce.V1.util.AccountUtil;
import com.ecomerce.Ecomerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final AccountService accountService;
    private final VoucherRepository voucherRepository;

    @Override
    public VoucherResponse create(VoucherRequest voucherRequest) {
        Account account = accountService.accountContext();

        if(AccountUtil.containRole(account.getRole(),"ADMIN"))
            throw new BadRequestException("Apenas ADMS podem criar cupons de desconto!");

        if(voucherRepository.findByName(voucherRequest.getName()).isPresent())
            throw new BadRequestException("Nome escolido para o cupom ja existe!");

        if(voucherRequest.getDateExpiration().isBefore(LocalDateTime.now()))
            throw new BadRequestException("Informe uma data de expiração válida!");

        Voucher voucher = Voucher
                .builder()
                .name(voucherRequest.getName())
                .creatorId(account.getId())
                .dateExpiration(voucherRequest.getDateExpiration())
                .discountPercentage(voucherRequest.getDiscountPercentage())
                .build();
        voucherRepository.save(voucher);

        return VoucherResponse
                .builder()
                .id(voucher.getId())
                .creatorName(account.getFullName())
                .dateExpiration(voucher.getDateExpiration())
                .discountPercentage(voucher.getDiscountPercentage())
                .name(voucher.getName())
                .build();
    }
}
