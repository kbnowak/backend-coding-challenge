package com.engage.codingtest.taxes;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class VatCalculation {

    public BigDecimal getVatAmount(BigDecimal grossAmount, BigDecimal vatRate) {
        return grossAmount.multiply(vatRate).divide(vatRate.add(BigDecimal.valueOf(100)), 2, RoundingMode.HALF_UP);
    }
}
