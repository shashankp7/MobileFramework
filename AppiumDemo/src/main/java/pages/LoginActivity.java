package pages;

import core.MobileActivity;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginActivity extends MobileActivity {


  private By openApp_TV = By.xpath("//*[(@text='Open' or @text='OPEN') and @enabled='true']");
  private By install_Btn = By.xpath("//android.widget.Button[@text='INSTALL' or @text='UPDATE' or @text='Install' or @text='Update']");
  private Object HomeActivity;





    public void clickLogin()
    {
       click(openApp_TV);
       waitForElementPresence(openApp_TV);
    }
    public HomeActivity HomePage()
    {
        click(install_Btn);
        waitForElementPresence(install_Btn);
        return (pages.HomeActivity) HomeActivity;
    }

}
