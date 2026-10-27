package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
    private WebDriver driver;
    private By cartIcon = By.className("shopping_cart_link");
    private By products = By.className("inventory_item");

    public InventoryPage(WebDriver driver){
        this.driver = driver;
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

    public boolean isCartIconDisplayed(){
        return driver.findElement(cartIcon).isDisplayed();
    }

    public int getProductsCount(){
        return driver.findElements(products).size();
    }
}