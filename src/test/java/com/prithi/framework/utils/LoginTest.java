package com.prithi.framework.tests;

import com.prithi.framework.base.BaseTest;
import com.prithi.framework.pages.InventoryPage;
import com.prithi.framework.pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {

    @Test(description = "Valid user can log in and sees the product list")
    public void validLoginShowsProducts() {
        new LoginPage(page()).open().login("standard_user", "secret_sauce");

        InventoryPage inventory = new InventoryPage(page());
        assertThat(page()).hasURL(Pattern.compile(".*inventory.html"));
        assertThat(inventory.title()).hasText("Products");
        assertThat(inventory.items()).hasCount(6);
    }

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() {
        return new Object[][] {
            {"standard_user", "wrong_password", "Username and password do not match"},
            {"locked_out_user", "secret_sauce", "this user has been locked out"},
            {"", "secret_sauce", "Username is required"},
            {"standard_user", "", "Password is required"}
        };
    }

    @Test(dataProvider = "invalidLogins", description = "Invalid logins show the correct error message")
    public void invalidLoginShowsError(String user, String pass, String expectedError) {
        LoginPage login = new LoginPage(page()).open();
        login.login(user, pass);

        assertThat(login.errorMessage()).containsText(expectedError);
    }
}
