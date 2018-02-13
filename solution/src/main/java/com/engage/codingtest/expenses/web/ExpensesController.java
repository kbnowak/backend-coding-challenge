package com.engage.codingtest.expenses.web;

import com.engage.codingtest.currencies.CurrencyConversionException;
import com.engage.codingtest.expenses.domain.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/app/expenses")
public class ExpensesController {

    private final ExpensesService expensesService;

    @Autowired
    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExpenseResponse> getExpenses() {
        return expensesService.getExpenses();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveExpense(@RequestBody @Valid ExpenseCreateRequest expenseCreateRequest) throws CurrencyConversionException {
        expensesService.saveExpense(expenseCreateRequest);
    }

    @ResponseStatus(value= HttpStatus.CONFLICT, reason="Currency cannot be converted")
    @ExceptionHandler(CurrencyConversionException.class)
    public void conflict() {
    }
}
