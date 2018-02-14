package com.engage.codingtest.currencies;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Currency;

@Component
public class CurrenciesConverter {

    private static final Logger logger = LoggerFactory.getLogger(CurrenciesConverter.class);
    private static final String CURRENCY_API_URL_PATTERN = "https://api.fixer.io/latest?base=%s&symbols=%s";

    private ObjectMapper mapper;

    @Autowired
    public CurrenciesConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Converts given amount with currency to other currency. Currency rates are taken from outer API.
     *
     * @param amount value before conversion
     * @param baseCurrency currency of value before conversion
     * @param destinationCurrency currency of value after conversion
     * @return value converted based on currency rates taken from outer API
     * @throws CurrencyConversionException when there is problem with getting currency rates through API
     */

    public BigDecimal convert(BigDecimal amount, Currency baseCurrency, Currency destinationCurrency) throws CurrencyConversionException {
        CurrencyRates currencyRates;
        try {
            URL apiUrl = new URL(String.format(CURRENCY_API_URL_PATTERN, baseCurrency, destinationCurrency));
            currencyRates = mapper.readValue(apiUrl, CurrencyRates.class);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Currency API URL invalid", e);
        } catch (IOException e) {
            logger.warn("Error occurred while getting conversion rates for {} {}, converting to {}", amount, baseCurrency, destinationCurrency);
            throw new CurrencyConversionException(e);
        }
        BigDecimal rate = currencyRates.getRates().get(destinationCurrency.getCurrencyCode());
        BigDecimal converted = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);

        logger.debug("Converted {} {} to {} {}", amount, baseCurrency, converted, destinationCurrency);

        return converted;
    }
}
