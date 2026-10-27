package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPage {

    private WebDriver driver;

    private By cartIcon = By.className("shopping_cart_link");
    private By cartItemNames = By.className("inventory_item_name");
    private By continueShoppingButton = By.id("continue-shopping");
    private By checkoutButton = By.id("checkout");

    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By itemTotalLabel = By.className("summary_subtotal_label");

    private By linkedinIcon = By.className("social_linkedin");
    private By facebookIcon = By.className("social_facebook");
    private By twitterIcon = By.className("social_twitter");

    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutLink = By.id("logout_sidebar_link");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private By productButton(String productName) {
        return By.xpath("//div[text()='" + productName +
                "']/ancestor::div[@class='inventory_item' or @class='cart_item']//button");
    }

    public void clickProductButton(String productName) {
        driver.findElement(productButton(productName)).click();
    }

    public String getButtonText(String productName) {
        return driver.findElement(productButton(productName)).getText();
    }

    public double getProductPrice(String productName) {

        String price = driver.findElement(
                By.xpath("//div[text()='" + productName +
                        "']/ancestor::div[@class='inventory_item']" +
                        "//div[@class='inventory_item_price']")
        ).getText();

        return Double.parseDouble(price.replace("$", ""));
    }

    public void openCart() {
        driver.findElement(cartIcon).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("cart.html"));
    }

    public List<String> getCartItemNames() {

        List<String> names = new ArrayList<>();

        for (WebElement element : driver.findElements(cartItemNames)) {
            names.add(element.getText());
        }

        return names;
    }

    public void waitForCartItemsCount(int count) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBe(cartItemNames, count));
    }

    public void clickContinueShopping() {
        driver.findElement(continueShoppingButton).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("inventory.html"));
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }

    public boolean isCheckoutPageOpened() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.urlContains("checkout-step-one"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));

        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);

        driver.findElement(continueButton).click();
    }

    public double getItemTotal() {

        String total = driver.findElement(itemTotalLabel).getText();

        return Double.parseDouble(total.replace("Item total: $", ""));
    }

    public void logout() {
        driver.findElement(menuButton).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));
    }

    public String clickLinkedinIcon() {
        return openSocialLink(linkedinIcon);
    }

    public String clickFacebookIcon() {
        return openSocialLink(facebookIcon);
    }

    public String clickTwitterIcon() {
        return openSocialLink(twitterIcon);
    }

    private String openSocialLink(By icon) {

        String originalWindow = driver.getWindowHandle();

        driver.findElement(icon).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String window : driver.getWindowHandles()) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
            }
        }

        wait.until(ExpectedConditions.urlContains("http"));

        String url = driver.getCurrentUrl();

        driver.close();

        driver.switchTo().window(originalWindow);

        return url;
    }
}