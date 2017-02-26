package com.dan.tests;

import org.junit.Test;

public class AddRemoveProductTest extends TestBasePO {

    @Test
    public void canAddAndRemoveProducts(){
        app.addProductsToCart(3);
        app.removeAllProductsFromCart();
        app.verifyCartIsEmpty();
    }
}
