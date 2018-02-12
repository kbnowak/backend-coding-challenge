package com.engage.codingtest.expenses;

import com.engage.codingtest.expenses.web.ExpenseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExpensesITests {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getHello() throws Exception {

        LocalDate localDate = LocalDate.of(2015, 4, 24);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.of("GMT")).toInstant());

        ExpenseDTO expenseDTO = new ExpenseDTO(date, BigDecimal.valueOf(64.9), null, "The big expense");
        saveExpense(expenseDTO);

        mvc.perform(MockMvcRequestBuilders.get("/app/expenses").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(DATE_FORMAT.format(expenseDTO.getDate()))))
                .andExpect(content().string(containsString(expenseDTO.getAmount().toString())))
                .andExpect(content().string(containsString(expenseDTO.getReason())));
    }

    private void saveExpense(ExpenseDTO expenseDTO) throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/app/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expenseDTO)))
                .andExpect(status().isOk());
    }
}
