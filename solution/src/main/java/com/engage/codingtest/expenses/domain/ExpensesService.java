package com.engage.codingtest.expenses.domain;

import com.engage.codingtest.expenses.web.ExpenseDTO;
import com.engage.codingtest.taxes.VatCalculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpensesService {

    private static final Logger logger = LoggerFactory.getLogger(ExpensesService.class);

    private final ExpensesRepository expensesRepository;
    private final VatCalculation vatCalculation;

    @Value("${expenses.vatRate}")
    private BigDecimal vatRate;

    @Autowired
    public ExpensesService(ExpensesRepository expensesRepository, VatCalculation vatCalculation) {
        this.expensesRepository = expensesRepository;
        this.vatCalculation = vatCalculation;
    }

    public List<ExpenseDTO> getExpenses() {
        return expensesRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ExpenseDTO toDTO(Expense expense) {
        return new ExpenseDTO(
                expense.getDate(),
                expense.getAmount(),
                vatCalculation.getVatAmount(expense.getAmount(), expense.getVatRate()),
                expense.getReason()
        );
    }

    public void saveExpense(ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setDate(expenseDTO.getDate());
        expense.setAmount(expenseDTO.getAmount());
        expense.setVatRate(vatRate);
        expense.setReason(expenseDTO.getReason());
        Expense savedExpense = expensesRepository.save(expense);
        logger.info("Saved new expense with id: {}", savedExpense.getId());
    }
}
