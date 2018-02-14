package com.engage.codingtest.currencies;

/**
 * Signals that converting currency cannot be accomplished.
 */
public class CurrencyConversionException extends Exception {

    CurrencyConversionException(Throwable cause) {
        super(cause);
    }
}
