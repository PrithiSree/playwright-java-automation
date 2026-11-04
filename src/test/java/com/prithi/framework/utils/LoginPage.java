package com.prithi.framework.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.prithi.framework.utils.Config;

public class LoginPage {

    private final Page page;
    private final Locator username;
    private final Locator password;
    private final Locator loginButton;
    private final Locator errorMessage;

    public LoginPage(Page page) {
        this.page = page;
        this.username = page.locator("[data-test='username']");
        this.password = page.locator("[data-test='password']");
        this.loginButton = page.locator("[data-test='login-button']");
        this.errorMessage = page.locator("[data-test='error']");
    }

    public LoginPage open() {
        page.navigate(Config.BASE_URL);
        return this;
    }

    public void login(String user, String pass) {
        username.fill(user);
        password.fill(pass);
        loginButton.click();
    }

    public Locator errorMessage() {
        return errorMessage;
    }
}
