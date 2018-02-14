package com.engage.codingtest.expenses.domain;

import com.engage.codingtest.currencies.CurrenciesConverter;
import com.engage.codingtest.currencies.CurrencyConversionException;
import com.engage.codingtest.expenses.web.ExpenseCreateRequest;
import com.engage.codingtest.expenses.web.ExpenseResponse;
import com.engage.codingtest.taxes.VatCalculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpensesService {

    private static final Logger logger = LoggerFactory.getLogger(ExpensesService.class);

    private final ExpensesRepository expensesRepository;
    private final VatCalculation vatCalculation;
    private final CurrenciesConverter currenciesConverter;

    @Value("${expenses.vatRate}")
    private BigDecimal vatRate;

    @Autowired
    public ExpensesService(ExpensesRepository expensesRepository, VatCalculation vatCalculation, CurrenciesConverter currenciesConverter) {
        this.expensesRepository = expensesRepository;
        this.vatCalculation = vatCalculation;
        this.currenciesConverter = currenciesConverter;
    }

    public List<ExpenseResponse> getExpenses() {
        return expensesRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    private ExpenseResponse toResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getDate(),
                expense.getAmount(),
                vatCalculation.getVatAmount(expense.getAmount(), expense.getVatRate()),
                expense.getReason()
        );
    }

    public void saveExpense(ExpenseCreateRequest expenseCreateRequest) throws CurrencyConversionException {
        Currency gbpCurrency = Currency.getInstance("GBP");
        BigDecimal amountInGbp = expenseCreateRequest.getCurrencyCode().equals(gbpCurrency)
                ? expenseCreateRequest.getAmount()
                : currenciesConverter.convert(expenseCreateRequest.getAmount(), expenseCreateRequest.getCurrencyCode(), gbpCurrency);

        Expense expense = new Expense();
        expense.setDate(expenseCreateRequest.getDate());
        expense.setAmount(amountInGbp);
        expense.setVatRate(vatRate);
        expense.setReason(expenseCreateRequest.getReason());
        Expense savedExpense = expensesRepository.save(expense);
        logger.info("Saved new expense with id: {}", savedExpense.getId());
    }
}
