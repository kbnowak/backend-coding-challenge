package com.engage.codingtest.expenses.domain;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Expense {

    @Id
    @GeneratedValue
    private Long id;

    @Type(type="date")
    private Date date;

    private BigDecimal amount;

    private BigDecimal vatRate;

    private String reason;
}
