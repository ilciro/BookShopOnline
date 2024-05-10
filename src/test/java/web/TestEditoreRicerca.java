package web;

import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import web.bean.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestEditoreRicerca {
    WebDriver driver;
    private final Libro l=new Libro();
    private final LibroBean lB=new LibroBean();
    private final LibroDao lD=new LibroDao();

    private final UserBean uB=UserBean.getInstance();

    private final Giornale g=new Giornale();
    private final GiornaleDao gD=new GiornaleDao();
    private final GiornaleBean gB=new GiornaleBean();

    public TestEditoreRicerca() throws IOException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    }

    @Test
    void testLoginRicercaScrittoreByTitoloLibro() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //schermata index
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("zerocalcare@gmail.com");
        driver.findElement(By.id("passL")).sendKeys("Zerocalcare21");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        //schermata scelta
        driver.findElement(By.id("buttonRic")).click();
        //schemata ricerca
        driver.findElement(By.id("buttonL")).click();
        //research in books
        //titolo
        WebElement titoloL=driver.findElement(By.id("cercaL"));
        titoloL.sendKeys("Erasgon Vol 1");
        String titoloLibro=titoloL.getAttribute("value");

        driver.findElement(By.id("cercaB")).click();
        PropertyUtils.setProperty(lB,"titoloB",titoloLibro);
        l.setTitolo(PropertyUtils.getProperty(lB,"titoloB").toString());


        assertNotNull(lD.getLibriIdTitoloAutore(l));



    }
        @Test
    void testLoginRicercaScrittoreByAutoreLibro() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        //schermata index
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("zerocalcare@gmail.com");
        driver.findElement(By.id("passL")).sendKeys("Zerocalcare21");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        //schermata scelta
        driver.findElement(By.id("buttonRic")).click();
        //schemata ricerca
        driver.findElement(By.id("buttonL")).click();
        //research in books
        //titolo
        WebElement titoloL=driver.findElement(By.id("cercaL"));
        titoloL.sendKeys("CiccioGamer89");
        String titoloLibro=titoloL.getAttribute("value");

        driver.findElement(By.id("cercaB")).click();
        PropertyUtils.setProperty(lB,"titoloB",titoloLibro);
        l.setTitolo(PropertyUtils.getProperty(lB,"titoloB").toString());


        assertNotNull(lD.getLibroIdTitoloAutore(l));



    }

    // test for editor
    @Test
    void testLoginRicercaScrittoreByEditoreGiornale() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //schermata index
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("zerocalcare@gmail.com");
        driver.findElement(By.id("passL")).sendKeys("Zerocalcare21");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        //schermata scelta
        driver.findElement(By.id("buttonRic")).click();
        //schemata ricerca
        driver.findElement(By.id("buttonG")).click();
        //research in books
        //titolo
        WebElement editoreG=driver.findElement(By.id("cercaL"));
        editoreG.sendKeys("La Republica");
        String editoreGiornale=editoreG.getAttribute("value");

        driver.findElement(By.id("cercaB")).click();
        PropertyUtils.setProperty(gB,"editoreB",editoreGiornale);
        g.setEditore(PropertyUtils.getProperty(gB,"editoreB").toString());


        assertNotNull(gD.getGiornaliIdTitoloAutore(g));



    }
    @Test
    void testLoginRicercaEditoreByTitoloGiornale() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //schermata index
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("zerocalcare@gmail.com");
        driver.findElement(By.id("passL")).sendKeys("Zerocalcare21");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        //schermata scelta
        driver.findElement(By.id("buttonRic")).click();
        //schemata ricerca
        driver.findElement(By.id("buttonG")).click();
        //research in books
        //titolo
        WebElement titoloG=driver.findElement(By.id("cercaL"));
        titoloG.sendKeys("La Republica");
        String titoloGiornale=titoloG.getAttribute("value");

        driver.findElement(By.id("cercaB")).click();
        PropertyUtils.setProperty(gB,"titoloB",titoloGiornale);
        g.setTitolo(PropertyUtils.getProperty(gB,"titoloB").toString());


        assertNotNull(gD.getGiornaleIdTitoloAutore(g));



    }
    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }



}
