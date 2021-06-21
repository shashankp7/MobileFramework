package core;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.BeforeClass;


import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;

public class DriverTest {
    private AppiumDriver<MobileElement> mobileDriver;

    String appPath= System.getProperty("user.dir")+"/src/main/resources/app/hirevue.apk";

    private static DriverTest driverTest;

    /*** Gets Singleton instance */
    public static synchronized DriverTest getInstance() {
        if (driverTest == null)
            driverTest = new DriverTest();
        return driverTest;
    }

    public AppiumDriver<MobileElement> getMobileDriver() {
        return mobileDriver;
    }

    @BeforeClass
    public AppiumDriver<MobileElement> buildMobileDriver()  {
        AppiumDriver<MobileElement> appiumDriver = null;
        String ipaddress= getLocalIPAddress();
        int port= getFreePort();
        launchAppiumServer(port, ipaddress);
        String OS = "Android";
        switch (OS){
            case "Android":
                appiumDriver = startAndroidDriver(ipaddress,port);
                break;
            case "IOS":
                appiumDriver=startIOSDriver(getLocalIPAddress(),getFreePort());
        }
        return appiumDriver;
    }

    public AndroidDriver<MobileElement> startAndroidDriver(String ipAddress, int systemPort) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "");
        capabilities.setCapability(MobileCapabilityType.UDID, "");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Samsung galaxy S4");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, "80000");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 0);
        capabilities.setCapability(MobileCapabilityType.APP, appPath);
      //  capabilities.setCapability("androidPackage","com.android.dialer");
     //   capabilities.setCapability("appActivity","DialtactsActivity");
        //   capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, ".LoginActivity.*");
        capabilities.setCapability("ignoreUnimportantViews", true);
        capabilities.setCapability("disableAndroidWatchers", true);
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("skipUnlock", true);
        capabilities.setCapability("nativeWebScreenshot", true);
        capabilities.setCapability("skipServerInstallation", false);
        AndroidDriver<MobileElement> driver = null;
        try {
            driver = new AndroidDriver<>(new URL("http://" + ipAddress + ":" + systemPort + "/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.getStackTrace();
        }
        return driver;
    }

    public IOSDriver<MobileElement> startIOSDriver(String ipAddress, int systemPort) {
        DesiredCapabilities driverCapabilities = new DesiredCapabilities();
        driverCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        driverCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        driverCapabilities.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, false);
        driverCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "APP_BUNDLEID");
        driverCapabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, "wdaPort");
        driverCapabilities.setCapability(IOSMobileCapabilityType.SHOW_XCODE_LOG, false);
        driverCapabilities.setCapability(IOSMobileCapabilityType.SHOW_IOS_LOG, false);
        driverCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 0);
        driverCapabilities.setCapability(MobileCapabilityType.UDID, "deviceID");
        driverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "IOS");
        System.out.println("Before start driver ");
        IOSDriver<MobileElement> driver = null;
        try {
            driver = new IOSDriver<>(new URL("http://" + ipAddress + ":" + systemPort + "/wd/hub"), driverCapabilities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Driver started = " + driver.getSessionDetails().toString());
        return driver;
    }


    protected synchronized AppiumDriverLocalService launchAppiumServer(int port, String ipAddress) {
        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
        appiumServiceBuilder.usingPort(port)
                .withIPAddress(ipAddress)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        AppiumDriverLocalService service;
        try {
            service = AppiumDriverLocalService.buildService(appiumServiceBuilder);
            service.start();
            System.out.println("Appium Server started on Url = " + service.getUrl());
        } catch (Exception e) {
            System.out.println("Appium Server Inside Catch Exception");
            service = AppiumDriverLocalService.buildService(appiumServiceBuilder);
            service.start();
        }
        return service;
    }

    public synchronized int getFreePort() {
        try {
            ServerSocket socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            socket.close();
            return port;
        } catch (Exception e) {
            return 5643;
        }
    }

    protected void gotoSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized String getLocalIPAddress() {
        String ipAddress = "LocalHost";
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            ipAddress = localhost.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

}
