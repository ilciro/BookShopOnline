package web;


import laptop.database.RivistaDao;

import laptop.model.raccolta.Rivista;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import web.bean.RivistaBean;
import web.bean.UserBean;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestScrittoreRicerca {
    WebDriver driver;


    private final UserBean uB=UserBean.getInstance();


    private final Rivista r=new Rivista();
    private final RivistaDao rD=new RivistaDao();
    private final RivistaBean rB=new RivistaBean();

    public TestScrittoreRicerca() throws IOException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

    }

    @Test
    void testLoginRicercaScrittoreByTitoloRivista() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        //schermata index
        driver = new ChromeDriver();

        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("BaoPublishing@gmail.com");
        driver.findElement(By.id("passL")).sendKeys("BaoPub2021");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        //schermata scelta
        driver.findElement(By.id("buttonRic")).click();
        //schemata ricerca
        driver.findElement(By.id("buttonR")).click();
        //research in books
        //titolo
        WebElement titoloR=driver.findElement(By.id("cercaL"));
        titoloR.sendKeys("Focus");
        String titoloRivista=titoloR.getAttribute("value");

        driver.findElement(By.id("cercaB")).click();
        PropertyUtils.setProperty(rB,"titoloB",titoloRivista);
        r.setTitolo(PropertyUtils.getProperty(rB,"titoloB").toString());


        assertNotNull(rD.getRivistaIdTitoloAutore(r));


    }

    @Test
    void testLoginRicercaScrittoreByAutoreRivista() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        //schermata index
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("BaoPublishing@gmail.com");
        driver.findElement(By.id("passL")).sendKeys("BaoPub2021");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        //schermata scelta
        driver.findElement(By.id("buttonRic")).click();
        //schemata ricerca
        driver.findElement(By.id("buttonR")).click();
        //research in books
        //titolo
        WebElement autoreR=driver.findElement(By.id("cercaL"));
        autoreR.sendKeys("Panorama");
        String autoreRivista=autoreR.getAttribute("value");

        driver.findElement(By.id("cercaB")).click();
        PropertyUtils.setProperty(rB,"autoreB",autoreRivista);
        r.setAutore(PropertyUtils.getProperty(rB,"autoreB").toString());


        assertNotNull(rD.getRivistaIdTitoloAutore(r));


    }


    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }


}
