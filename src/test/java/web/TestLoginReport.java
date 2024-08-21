package web;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import web.bean.TextAreaBean;
import web.bean.UserBean;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

 class TestLoginReport {
    WebDriver driver;
    private final UserBean uB=UserBean.getInstance();
    private final TextAreaBean tAB=new TextAreaBean();

    public TestLoginReport()
    {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    }

    @Test
    void testLoginAdminReport() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //schermata index
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("admin@admin.com");
        driver.findElement(By.id("passL")).sendKeys("Admin871");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        //schermata admin
        driver.findElement(By.id("reportB")).click();
        //schermata report
       driver.findElement(By.id("buttonT")).click();




        assertNotEquals("",PropertyUtils.getProperty(tAB,"scriviB"));

    }
    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }

}
