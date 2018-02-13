package com.engage.codingtest.currencies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CurrencyRates {

    private String base;

    private Date date;

    private Map<String, BigDecimal> rates;
}

