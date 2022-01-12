package br.ce.wcaquino.functionaltest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class tasksTest {

    public WebDriver acessarAplicacao() throws MalformedURLException{
        //WebDriver driver = new ChromeDriver();
        ChromeOptions browserOptions = new ChromeOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.42.70:4444/wd/hub"), browserOptions);
        driver.navigate().to("http://192.168.42.70:8001/tasks");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException{
        
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Task via Selenium");            
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = localDate.format(formatter);
            driver.findElement(By.id("dueDate")).sendKeys(formattedString);
            driver.findElement(By.id("saveButton")).click();
            String msg = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", msg);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException{
        
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2022");
            driver.findElement(By.id("saveButton")).click();
            String msg = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", msg);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException{
        
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Task via Selenium");
            driver.findElement(By.id("saveButton")).click();
            String msg = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", msg);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException{
        
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Task via Selenium");
            driver.findElement(By.id("dueDate")).sendKeys("01/01/2021");
            driver.findElement(By.id("saveButton")).click();
            String msg = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", msg);
        } finally {
            driver.quit();
        }
    }
}
