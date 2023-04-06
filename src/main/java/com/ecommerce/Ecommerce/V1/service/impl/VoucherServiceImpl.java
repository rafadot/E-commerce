package com.ecommerce.Ecommerce.V1.service.impl;

import com.ecommerce.Ecommerce.V1.dto.voucher.VoucherRequest;
import com.ecommerce.Ecommerce.V1.dto.voucher.VoucherTypePercentage;
import com.ecommerce.Ecommerce.V1.dto.voucher.VoucherTypeValue;
import com.ecommerce.Ecommerce.V1.model.Account;
import com.ecommerce.Ecommerce.V1.model.Voucher;
import com.ecommerce.Ecommerce.V1.model.enums.VoucherType;
import com.ecommerce.Ecommerce.V1.repository.VoucherRepository;
import com.ecommerce.Ecommerce.V1.service.interfaces.AccountService;
import com.ecommerce.Ecommerce.V1.service.interfaces.VoucherService;
import com.ecommerce.Ecommerce.V1.util.AccountUtil;
import com.ecommerce.Ecommerce.V1.util.ConversionUtil;
import com.ecommerce.Ecommerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final AccountService accountService;
    private final VoucherRepository voucherRepository;

    @Override
    public Object create(VoucherRequest voucherRequest,
                         VoucherType type,
                         Integer discountPercentage,
                         BigDecimal discountValueDollar) {
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
                .type(type)
                .dateExpiration(voucherRequest.getDateExpiration())
                .discountPercentage(type == VoucherType.PERCENTAGE ? discountPercentage : null)
                .discountValueDollar(type == VoucherType.VALUE ? discountValueDollar : null)
                .discountValueReal(type == VoucherType.VALUE ? ConversionUtil.dollarToReal(discountValueDollar) : null)
                .build();
        voucherRepository.save(voucher);

        if(type == VoucherType.PERCENTAGE){
            return VoucherTypePercentage
                    .builder()
                    .id(voucher.getId())
                    .name(voucher.getName())
                    .type(voucher.getType())
                    .dateExpiration(voucher.getDateExpiration())
                    .discountPercentage(voucher.getDiscountPercentage())
                    .creatorName(account.getFullName())
                    .build();
        }

        return VoucherTypeValue
                .builder()
                .id(voucher.getId())
                .name(voucher.getName())
                .type(voucher.getType())
                .dateExpiration(voucher.getDateExpiration())
                .discountValueDollar("$" + ConversionUtil.formatMoney(voucher.getDiscountValueDollar()))
                .discountValueReal("R$" + ConversionUtil.formatMoney(voucher.getDiscountValueReal()))
                .creatorName(account.getFullName())
                .build();
    }
}
