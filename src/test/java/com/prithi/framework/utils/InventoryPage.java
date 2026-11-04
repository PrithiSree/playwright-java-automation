package com.prithi.framework.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InventoryPage {

    private final Page page;
    private final Locator title;
    private final Locator items;
    private final Locator cartBadge;
    private final Locator cartLink;

    public InventoryPage(Page page) {
        this.page = page;
        this.title = page.locator("[data-test='title']");
        this.items = page.locator("[data-test='inventory-item']");
        this.cartBadge = page.locator("[data-test='shopping-cart-badge']");
        this.cartLink = page.locator("[data-test='shopping-cart-link']");
    }

    public Locator title() {
        return title;
    }

    public Locator items() {
        return items;
    }

    public Locator cartBadge() {
        return cartBadge;
    }

    public void addToCart(String productSlug) {
        page.locator("[data-test='add-to-cart-" + productSlug + "']").click();
    }

    public CartPage openCart() {
        cartLink.click();
        return new CartPage(page);
    }
}
