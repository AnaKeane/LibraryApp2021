package com.LibraryCT.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Driver {
    private Driver(){}

    private static ThreadLocal<WebDriver> driverPool= new ThreadLocal<>();
    public static WebDriver getDriver(){

        if(driverPool.get()==null) {

            synchronized (Driver.class) {
                String browser = ConfigurationReader.getProperty("browser");
                switch (browser) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        driverPool.set(new ChromeDriver());
                        break;
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        driverPool.set(new FirefoxDriver());
                        break;
                    default:
                        WebDriverManager.chromedriver().setup();
                        driverPool.set(new ChromeDriver());
                        break;

                }
            }
        }
        driverPool.get().manage().window().maximize();
        driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);



        return driverPool.get();
    }
    public static void closeDriver(){
        if(driverPool.get()!=null){
            driverPool.get().quit();
            driverPool.remove();
        }
    }

}
