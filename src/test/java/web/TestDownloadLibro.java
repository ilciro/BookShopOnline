
package web;

import laptop.database.LibroDao;
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

import static org.junit.jupiter.api.Assertions.assertNotEquals;

 class TestDownloadLibro {
    static WebDriver driver;
    private final UserBean uB= UserBean.getInstance();
    private final LibroDao lD=new LibroDao();
    private final SystemBean sB= SystemBean.getInstance();
    private final AcquistaBean aB=new AcquistaBean();

    public TestDownloadLibro() throws IOException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");


    }
    private final Libro l=new Libro();
    private final LibroBean lB=new LibroBean();
    private final FatturaBean fB=new FatturaBean();

    @Test
    void testLoginUserLibro() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        driver=new ChromeDriver();

        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("giuliaConforto@gmail.eu");
        driver.findElement(By.id("passL")).sendKeys("12345678Gc");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        //schermata utente: libro , giornale , rivista , logout,ricerca
        driver.findElement(By.id("buttonL")).click();


        PropertyUtils.setProperty(lB,"elencoLibriB", lD.getLibri());


        driver.findElement(By.id("idOgg")).sendKeys("2");
        int id=Integer.parseInt(driver.findElement(By.id("idOgg")).getAttribute("value"));
        PropertyUtils.setProperty(lB,"idB",id);
        l.setId((Integer) PropertyUtils.getProperty(lB,"idB"));
        String titolo=lD.getData(l).getTitolo();
        PropertyUtils.setProperty(sB,"idB", id);
        PropertyUtils.setProperty(lB,"idB", id);
        l.setId(id);
        PropertyUtils.setProperty(sB, "titoloB",lD.getData(l).getTitolo());
        PropertyUtils.setProperty(aB, "titoloB",sB.getTitoloB());
        PropertyUtils.setProperty(aB,"prezzoB",lD.getData(l).getPrezzo());
        driver.findElement(By.id("procedi")).click();
        //schermata acquista
        driver.findElement(By.id("quantita")).clear();
        driver.findElement(By.id("quantita")).sendKeys("3");
        int quantita=Integer.parseInt(driver.findElement(By.id("quantita")).getAttribute("value"));
        PropertyUtils.setProperty(sB,"quantitaB",quantita);
        driver.findElement(By.id("totaleB")).click();


        float prezzo=Float.parseFloat(driver.findElement(By.id("totale")).getAttribute("value"));
        PropertyUtils.setProperty(sB,"spesaTB",prezzo);
        PropertyUtils.setProperty(aB,"prezzoB",PropertyUtils.getProperty(sB,"spesaTB"));

        //metodo cash
        WebElement input =driver.findElement(By.xpath("//input[@list='metodi']"));
        WebElement option =driver.findElement(By.xpath("//*[@id='metodi']/option[1]"));
        String value = option.getAttribute("value");
        input.sendKeys(value);
        PropertyUtils.setProperty(sB,"metodoPB",value);
        driver.findElement(By.id("pdfB")).click();
        //schermata fattura
        driver.findElement(By.id("nomeT")).sendKeys("francoB");
        driver.findElement(By.id("cognomeT")).sendKeys("rossiB");
        driver.findElement(By.id("indirizzoT")).sendKeys("via papaveri 12");
        driver.findElement(By.id("com")).sendKeys("il cap è 00005 . Chiamare prima al numero 9411526");

        String nome=driver.findElement(By.name("nomeT")).getAttribute("value");
        String cognome=driver.findElement(By.name("cognomeT")).getAttribute("value");
        String indirizzo=driver.findElement(By.name("indirizzoT")).getAttribute("value");
        String com=driver.findElement(By.name("com")).getAttribute("value");
        //setto fattura
        PropertyUtils.setProperty(fB,"nomeB",nome);
        PropertyUtils.setProperty(fB,"cognomeB",cognome);
        PropertyUtils.setProperty(fB,"indirizzoB",indirizzo);
        PropertyUtils.setProperty(fB,"comunicazioniB",com);
        driver.findElement(By.id("buttonC")).click();
        //schermata download
        driver.findElement(By.id("titoloL")).sendKeys(titolo);
        driver.findElement(By.id("downloadB")).click();
        driver.findElement(By.linkText("open pdf")).click();

        assertNotEquals(0,PropertyUtils.getProperty(sB,"idB"));



    }
    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }


}



