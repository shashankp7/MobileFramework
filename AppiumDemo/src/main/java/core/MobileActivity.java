package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MobileActivity {

    private AppiumDriver<MobileElement> mobileDriver;
    private final WebDriverWait wait;


    public MobileActivity() {
        mobileDriver = DriverTest.getInstance().getMobileDriver();
        wait = new WebDriverWait(mobileDriver, 30);
    }

    /**
     * Wait for Element to be clickable
     *
     */
    protected void waitForElementToBeClickable(By by) {
        waitForElementPresence(by);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }


    public void click(By by) {
        try {
            waitForElementToBeClickable(by);
            mobileDriver.findElement(by).click();
        } catch (StaleElementReferenceException se) {
            waitForElementToBeClickable(by);
            mobileDriver.findElement(by).click();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    protected void waitForElementPresence(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException | NoSuchElementException se) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            } catch (Exception e) {
                Assert.fail(e.getMessage());
            }
        }
    }

}
