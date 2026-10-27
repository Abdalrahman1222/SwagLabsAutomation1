package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import utils.DataDriven;

import static org.testng.Assert.*;

public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin(){
        loginPage.setUsername(DataDriven.jsonReader("validUser", "username"));
        loginPage.setPassword(DataDriven.jsonReader("validUser", "password"));
        var inventoryPage = loginPage.clickLoginButton();
        assertTrue(inventoryPage.getCurrentUrl().contains("/inventory.html"),
                "The user was not redirected to the inventory page");
    }

    @Test
    public void testInvalidLogin(){
        loginPage.setUsername(DataDriven.jsonReader("invalidUser", "username"));
        loginPage.setPassword(DataDriven.jsonReader("invalidUser", "password"));
        loginPage.clickLoginButton();
        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "The error message is not correct");
    }

    @Test
    public void testLoginWithoutPassword(){
        loginPage.setUsername(DataDriven.jsonReader("noPasswordUser", "username"));
        loginPage.setPassword(DataDriven.jsonReader("noPasswordUser", "password"));
        loginPage.clickLoginButton();
        assertTrue(loginPage.getErrorMessage().contains("Password is required"),
                "The error message is not correct");
    }
}