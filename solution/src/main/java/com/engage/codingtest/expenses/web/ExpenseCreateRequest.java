package com.engage.codingtest.expenses.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExpenseCreateRequest {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;

    private BigDecimal amount;

    @JsonProperty("currency")
    @JsonDeserialize(using = CurrencyDeserializer.class)
    private Currency currencyCode;

    @Size(max = 255)
    private String reason;
}
