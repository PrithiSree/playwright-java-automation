package com.prithi.framework.tests;

import com.prithi.framework.base.BaseTest;
import com.prithi.framework.pages.CartPage;
import com.prithi.framework.pages.InventoryPage;
import com.prithi.framework.pages.LoginPage;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartTest extends BaseTest {

    private InventoryPage loginAsStandardUser() {
        new LoginPage(page()).open().login("standard_user", "secret_sauce");
        return new InventoryPage(page());
    }

    @Test(description = "Adding one product updates the cart badge")
    public void addSingleItemUpdatesBadge() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.addToCart("sauce-labs-backpack");

        assertThat(inventory.cartBadge()).hasText("1");
    }

    @Test(description = "Added products appear in the cart")
    public void addedItemsAppearInCart() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.addToCart("sauce-labs-backpack");
        inventory.addToCart("sauce-labs-bike-light");
        assertThat(inventory.cartBadge()).hasText("2");

        CartPage cart = inventory.openCart();
        assertThat(cart.cartItems()).hasCount(2);
        assertThat(cart.itemNames().first()).hasText("Sauce Labs Backpack");
    }
}
