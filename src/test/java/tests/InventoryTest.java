package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import utils.DataDriven;

import static org.testng.Assert.*;

public class InventoryTest extends BaseTest {

     @Test
    public void testInventoryPageElements(){
        loginPage.setUsername(DataDriven.jsonReader("validUser", "username"));
        loginPage.setPassword(DataDriven.jsonReader("validUser", "password"));
        var inventoryPage = loginPage.clickLoginButton();

        assertEquals(inventoryPage.getPageTitle(), "Swag Labs",
                "The page title is not correct");
        assertTrue(inventoryPage.isCartIconDisplayed(),
                "The cart icon is not displayed");
        assertEquals(inventoryPage.getProductsCount(), 6,
                "The number of products is not correct");
    }
}