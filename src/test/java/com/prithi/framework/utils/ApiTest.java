package com.prithi.framework.tests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.prithi.framework.utils.Config;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ApiTest {

    private Playwright playwright;
    private APIRequestContext api;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        api = playwright.request().newContext(
                new APIRequest.NewContextOptions().setBaseURL(Config.API_BASE_URL));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (api != null) api.dispose();
        if (playwright != null) playwright.close();
    }

    @Test(description = "GET a post returns 200 and the expected fields")
    public void getPostReturnsExpectedData() {
        APIResponse response = api.get("/posts/1");

        Assert.assertEquals(response.status(), 200);
        JsonObject body = JsonParser.parseString(response.text()).getAsJsonObject();
        Assert.assertEquals(body.get("id").getAsInt(), 1);
        Assert.assertTrue(body.has("title"), "Response should contain a title");
        Assert.assertTrue(body.has("userId"), "Response should contain a userId");
    }

    @Test(description = "POST creates a resource and returns 201")
    public void createPostReturns201() {
        JsonObject payload = new JsonObject();
        payload.addProperty("title", "QA automation");
        payload.addProperty("body", "Created by Playwright API test");
        payload.addProperty("userId", 1);

        APIResponse response = api.post("/posts", RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(payload.toString()));

        Assert.assertEquals(response.status(), 201);
        JsonObject body = JsonParser.parseString(response.text()).getAsJsonObject();
        Assert.assertEquals(body.get("title").getAsString(), "QA automation");
    }

    @Test(description = "GET a non-existent post returns 404")
    public void missingPostReturns404() {
        APIResponse response = api.get("/posts/999999");

        Assert.assertEquals(response.status(), 404);
    }
}
