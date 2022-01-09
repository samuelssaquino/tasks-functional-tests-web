package br.ce.wcaquino.functionaltest;

import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class tasksTest {

    public WebDriver acessarAplicacao(){
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8001/tasks");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso(){
        
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Task via Selenium");
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2022");
            driver.findElement(By.id("saveButton")).click();
            String msg = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", msg);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao(){
        
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
    public void naoDeveSalvarTarefaSemData(){
        
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
    public void naoDeveSalvarTarefaComDataPassada(){
        
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
