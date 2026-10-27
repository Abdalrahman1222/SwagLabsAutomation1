package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import utils.DataDriven;

public class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    protected void loginWithValidUser(){
        loginPage.setUsername(DataDriven.jsonReader("validUser", "username"));
        loginPage.setPassword(DataDriven.jsonReader("validUser", "password"));
        loginPage.clickLoginButton();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}