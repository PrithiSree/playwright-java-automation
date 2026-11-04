package com.prithi.framework.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.prithi.framework.utils.Config;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Paths;

public class BaseTest {

    private static final ThreadLocal<Playwright> PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    protected Page page() {
        return PAGE.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        Playwright playwright = Playwright.create();
        BrowserType type;
        switch (Config.browser()) {
            case "firefox": type = playwright.firefox(); break;
            case "webkit":  type = playwright.webkit(); break;
            default:        type = playwright.chromium();
        }
        Browser browser = type.launch(new BrowserType.LaunchOptions().setHeadless(Config.headless()));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1366, 768));
        Page page = context.newPage();
        page.setDefaultTimeout(15_000);

        PLAYWRIGHT.set(playwright);
        BROWSER.set(browser);
        CONTEXT.set(context);
        PAGE.set(page);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE && PAGE.get() != null) {
                String name = result.getMethod().getMethodName() + "-" + System.currentTimeMillis() + ".png";
                PAGE.get().screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get("target", "screenshots", name))
                        .setFullPage(true));
            }
        } finally {
            if (CONTEXT.get() != null) CONTEXT.get().close();
            if (BROWSER.get() != null) BROWSER.get().close();
            if (PLAYWRIGHT.get() != null) PLAYWRIGHT.get().close();
            PAGE.remove();
            CONTEXT.remove();
            BROWSER.remove();
            PLAYWRIGHT.remove();
        }
    }
}
