package com.ecommerce.Ecommerce.V1.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ConversionUtil {
    public static BigDecimal dollarToReal(BigDecimal dollar){
        return dollar.multiply(BigDecimal.valueOf(5.14));
    }

    public static String formatMoney(BigDecimal value){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00",new DecimalFormatSymbols(Locale.forLanguageTag("pt-BR")));
        return decimalFormat.format(value);
    }
}
