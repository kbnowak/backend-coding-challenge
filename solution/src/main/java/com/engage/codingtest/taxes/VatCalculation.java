package com.engage.codingtest.taxes;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class VatCalculation {

    /**
     * Calculates VAT amount based on given {@code grossAmount} and {@code vatRate}. VAT amount is gross amount - net amount.
     *
     * @param grossAmount value from which vat amount is calculated
     * @param vatRate value representing percentages of VAT, e.g. for 20% VAT {@code vatRate} is 20
     * @return calculated VAT amount rounded to 2 decimal places
     */
    public BigDecimal getVatAmount(BigDecimal grossAmount, BigDecimal vatRate) {
        return grossAmount.multiply(vatRate).divide(vatRate.add(BigDecimal.valueOf(100)), 2, RoundingMode.HALF_UP);
    }
}
