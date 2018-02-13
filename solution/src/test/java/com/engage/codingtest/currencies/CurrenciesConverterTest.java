package com.engage.codingtest.currencies;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class CurrenciesConverterTest {

    @InjectMocks
    private CurrenciesConverter currenciesConverter;

    @Mock
    private ObjectMapper mapperMock;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider(name = "amounts")
    private Object[][] amounts() {
        return new Object[][] {
                {9, "GBP", "EUR", 1.1231, 10.11},
                {20, "GBP", "CHF", 1.2969, 25.94},
                {8.33, "USD", "CHF", 0.9345, 7.78},
                {8.33, "USD", "JPY", 133.130388, 1108.98}
        };
    }

    @Test(dataProvider = "amounts")
    public void shouldConvertCurrencyValues(double amount, String amountCurrency, String destinationCurrency,
                                            double rate, double expectedConvertedValue) throws Exception {
        // given
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put(destinationCurrency, BigDecimal.valueOf(rate));
        CurrencyRates currencies = new CurrencyRates(amountCurrency, new Date(), rates);

        when(mapperMock.readValue(any(URL.class), any(Class.class))).thenReturn(currencies);

        // when
        BigDecimal convertedValue = currenciesConverter.convert(BigDecimal.valueOf(amount),
                Currency.getInstance(amountCurrency), Currency.getInstance(destinationCurrency));

        // then
        assertThat(convertedValue).isEqualTo(BigDecimal.valueOf(expectedConvertedValue));
    }
}