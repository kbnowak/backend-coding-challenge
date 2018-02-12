package com.engage.codingtest.taxes;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

public class VatCalculationTest {

    private VatCalculation vatCalculation;

    @BeforeTest
    public void setUp() {
        this.vatCalculation = new VatCalculation();
    }

    @DataProvider(name = "taxes")

    public static Object[][] taxes() {

        return new Object[][] {
                {100, 20, 16.67},
                {123, 20, 20.5},
                {600, 20, 100},
                {1000, 23, 186.99}
        };

    }

    @Test(dataProvider = "taxes")
    public void shouldCalculateNetAmount(double gross, double vatRate, double net) {
        // when
        BigDecimal netAmount = vatCalculation.getVatAmount(BigDecimal.valueOf(gross), BigDecimal.valueOf(vatRate));

        // then
        assertThat(netAmount).isEqualTo(new BigDecimal(net).setScale(2, RoundingMode.HALF_UP));
    }
}