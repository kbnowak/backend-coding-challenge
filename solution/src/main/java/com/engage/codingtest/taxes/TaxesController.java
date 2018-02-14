package com.engage.codingtest.taxes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/app/taxes")
public class TaxesController {

    private final VatCalculation vatCalculation;

    @Value("${expenses.vatRate}")
    private BigDecimal vatRate;

    @Autowired
    public TaxesController(VatCalculation vatCalculation) {
        this.vatCalculation = vatCalculation;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vat")
    public BigDecimal getVatAmount(@RequestParam double gross) {
        return vatCalculation.getVatAmount(BigDecimal.valueOf(gross), vatRate);
    }
}
