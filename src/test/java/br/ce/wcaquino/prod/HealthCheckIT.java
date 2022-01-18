package br.ce.wcaquino.prod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {

    @Test
    public void healthCheck() throws MalformedURLException {

        ChromeOptions browserOptions = new ChromeOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.43.70:4444/wd/hub"), browserOptions);q

        try {

            driver.navigate().to("http://192.168.43.70:9999/tasks");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            String version = driver.findElement(By.id("version")).getText();
            System.out.println(version);
            Assert.assertTrue(version.startsWith("build"));
        } finally {
            driver.quit();
        }
    }
}