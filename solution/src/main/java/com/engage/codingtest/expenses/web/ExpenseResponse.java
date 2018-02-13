package com.engage.codingtest.expenses.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExpenseResponse {

    private Date date;

    private BigDecimal amount;

    @JsonProperty("vat")
    private BigDecimal vatAmount;

    private String reason;
}
