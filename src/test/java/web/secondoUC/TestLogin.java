package web.secondoUC;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import web.bean.SystemBean;
import web.bean.UserBean;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestLogin {
    private static ChromeDriver driver;
    private final SystemBean sB= SystemBean.getInstance();
    private final UserBean uB=UserBean.getInstance();


    @Test
    void testAdminLoginReport() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        //login
        driver.findElement(By.id("login")).click();
        String email="admin@admin.com";
        String pass="Admin871";
        driver.findElement(By.id("emailL")).sendKeys(email);
        driver.findElement(By.id("passL")).sendKeys(pass);
        PropertyUtils.setProperty(uB,"emailB",email);
        PropertyUtils.setProperty(uB,"passB",pass);
        driver.findElement(By.id("login")).click();
        //report
        driver.findElement(By.id("report")).click();
        //report libro
        PropertyUtils.setProperty(sB,"typeB","libro");
        driver.findElement(By.id("libri")).click();
        //report giornale
        PropertyUtils.setProperty(sB,"typeB","giornale");
        driver.findElement(By.id("giornali")).click();
        //report riviste
        PropertyUtils.setProperty(sB,"typeB","rivista");
        driver.findElement(By.id("riviste")).click();
        //report utenti
        driver.findElement(By.id("utenti")).click();
        driver.findElement(By.id("indietro")).click();
        //logout
        driver.findElement(By.id("logout")).click();
        //annullo utente
        PropertyUtils.setProperty(uB,"emailB","");
        PropertyUtils.setProperty(uB,"passB","");
        assertEquals("",PropertyUtils.getProperty(uB,"emailB"));

    }


    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }




}
