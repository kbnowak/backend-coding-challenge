package com.engage.codingtest.expenses;

import com.engage.codingtest.currencies.CurrenciesConverter;
import com.engage.codingtest.currencies.CurrencyConversionException;
import com.engage.codingtest.expenses.web.ExpenseCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExpensesITests {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrenciesConverter currenciesConverter;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReadSavedExpense() throws Exception {
        ExpenseCreateRequest expenseCreateRequest = buildRequest(Currency.getInstance("GBP"));
        saveExpense(expenseCreateRequest);

        mvc.perform(MockMvcRequestBuilders.get("/app/expenses").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(DATE_FORMAT.format(expenseCreateRequest.getDate()))))
                .andExpect(content().string(containsString(expenseCreateRequest.getAmount().toString())))
                .andExpect(content().string(containsString(expenseCreateRequest.getReason())));
    }

    @Test
    public void shouldNotSaveExpenseWhenCannotConvertCurrency() throws Exception {
        ExpenseCreateRequest expenseCreateRequest = buildRequest(Currency.getInstance("EUR"));

        doThrow(CurrencyConversionException.class).when(currenciesConverter).convert(expenseCreateRequest.getAmount(),
                expenseCreateRequest.getCurrencyCode(), Currency.getInstance("GBP"));

        mvc.perform(MockMvcRequestBuilders.post("/app/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expenseCreateRequest)))
                .andExpect(status().isConflict());
    }

    private ExpenseCreateRequest buildRequest(Currency currency) {
        LocalDate localDate = LocalDate.of(2015, 4, 24);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.of("GMT")).toInstant());

        return new ExpenseCreateRequest(date, BigDecimal.valueOf(64.9), currency, "The big expense");
    }

    private void saveExpense(ExpenseCreateRequest expenseCreateRequest) throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/app/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expenseCreateRequest)))
                .andExpect(status().isOk());
    }
}
