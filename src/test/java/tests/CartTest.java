package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.CartPage;
import utils.DataDriven;

import java.util.List;

import static org.testng.Assert.*;

public class CartTest extends BaseTest {

    private void addProducts(CartPage cartPage, List<String> products){
        for (String product : products){
            cartPage.clickProductButton(product);
        }
    }

    @Test
    public void testLinkedinLink(){
        loginWithValidUser();
        var cartPage = new CartPage(driver);
        assertTrue(cartPage.clickLinkedinIcon().contains("linkedin"),
                "The LinkedIn URL is not correct");
    }

    @Test
    public void testFacebookLink(){
        loginWithValidUser();
        var cartPage = new CartPage(driver);
        assertTrue(cartPage.clickFacebookIcon().contains("facebook"),
                "The Facebook URL is not correct");
    }

    @Test
    public void testXLink(){
        loginWithValidUser();
        var cartPage = new CartPage(driver);
        assertTrue(cartPage.clickTwitterIcon().contains("x.com"),
                "The X URL is not correct");
    }

    @Test
    public void testCartIsEmpty(){
        loginWithValidUser();
        var cartPage = new CartPage(driver);
        cartPage.openCart();
        assertTrue(cartPage.getCartItemNames().isEmpty(),
                "The cart is not empty");
    }

    @Test
    public void testAddThreeProducts(){
        loginWithValidUser();
        var cartPage = new CartPage(driver);
        List<String> products = DataDriven.jsonReaderList("cartProducts");

        addProducts(cartPage, products);
        cartPage.openCart();

        assertEquals(cartPage.getCartItemNames(), products,
                "The cart items are not the same or not in the same order");
    }

    @Test
    public void testRemoveOneProduct(){
        loginWithValidUser();
        var cartPage = new CartPage(driver);
        List<String> products = DataDriven.jsonReaderList("cartProducts");

        addProducts(cartPage, products);
        cartPage.openCart();

        String removed = products.stream()
                .filter(p -> p.contains("Bolt T-Shirt"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Bolt T-Shirt is not in cartProducts"));

        cartPage.clickProductButton(removed);
        cartPage.waitForCartItemsCount(products.size() - 1);
        assertFalse(cartPage.getCartItemNames().contains(removed),
                "The removed product is still in the cart");

        cartPage.clickContinueShopping();

        assertEquals(cartPage.getButtonText(removed), "Add to cart",
                "The removed product button is not Add to cart");
        for (String product : products){
            if (!product.equals(removed)){
                assertEquals(cartPage.getButtonText(product), "Remove",
                        "The button of " + product + " is not Remove");
            }
        }
    }

    @Test
    public void testCartTotalPrice(){
        loginWithValidUser();
        var cartPage = new CartPage(driver);
        List<String> products = DataDriven.jsonReaderList("cartProducts");

        double expectedTotal = 0;
        for (String product : products){
            expectedTotal += cartPage.getProductPrice(product);
        }

        addProducts(cartPage, products);
        cartPage.openCart();
        cartPage.clickCheckout();
        cartPage.fillCheckoutInfo("Test", "User", "12345");

        assertEquals(cartPage.getItemTotal(), expectedTotal, 0.01,
                "The item total does not match the sum of the prices");
    }

    @Test
    public void testCheckoutWithEmptyCart(){
        loginWithValidUser();
        var cartPage = new CartPage(driver);
        cartPage.openCart();
        assertTrue(cartPage.getCartItemNames().isEmpty(),
                "The cart is not empty");

        cartPage.clickCheckout();

        assertTrue(cartPage.isCheckoutPageOpened(),
                "The checkout page did not open with an empty cart");
    }

    @Test
    public void testCartAfterLogoutAndLogin(){
        loginWithValidUser();
        var cartPage = new CartPage(driver);
        List<String> products = DataDriven.jsonReaderList("cartProducts").subList(0, 2);

        addProducts(cartPage, products);
        cartPage.logout();
        loginWithValidUser();
        cartPage.openCart();

        assertEquals(cartPage.getCartItemNames(), products,
                "The cart items were not kept after logout and login");
    }
}