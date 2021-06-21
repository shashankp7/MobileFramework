import core.DriverTest;
import org.testng.annotations.Test;
import pages.HomeActivity;
import pages.LoginActivity;

public class TestClass extends DriverTest {
    LoginActivity loginActivity = new LoginActivity();
    HomeActivity homeActivity =new HomeActivity();


    @Test
    public void testLogin(){
        loginActivity.clickLogin();
    }

    @Test
    public void testHomePage(){
        loginActivity.HomePage();
    }

    @Test
    public void testWelcomePage()
    {
        homeActivity.clickHomebtn();
    }
}
