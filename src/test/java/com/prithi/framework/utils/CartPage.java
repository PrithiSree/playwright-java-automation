package com.prithi.framework.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage {

    private final Locator cartItems;
    private final Locator itemNames;

    public CartPage(Page page) {
        this.cartItems = page.locator("[data-test='inventory-item']");
        this.itemNames = page.locator("[data-test='inventory-item-name']");
    }

    public Locator cartItems() {
        return cartItems;
    }

    public Locator itemNames() {
        return itemNames;
    }
}
