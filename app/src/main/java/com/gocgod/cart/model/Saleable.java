package com.gocgod.cart.model;

import java.math.BigDecimal;

/**
 * Implements this interface for any product which can be added to shopping cart
 */
public interface Saleable {
    BigDecimal getPrice();

    String getName();
}
