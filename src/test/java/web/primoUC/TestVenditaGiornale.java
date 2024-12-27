package web.primoUC;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import web.bean.*;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

 class TestVenditaGiornale {

    private static ChromeDriver driver;
    private final SystemBean sB= SystemBean.getInstance();
    private final AcquistaBean aB=new AcquistaBean();
    private final GiornaleBean gB=new GiornaleBean();
    private final FatturaBean fB=new FatturaBean();

    @Test
    void testAcquistoGiornaleNegozio() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        //libro
        driver.findElement(By.id("giornali")).click();
        PropertyUtils.setProperty(sB,"typeB","giornale");
        //giornale.jsp genra lista
        driver.findElement(By.id("genera lista")).click();
        //assegno id
        driver.findElement(By.id("idOgg")).clear();
        driver.findElement(By.id("idOgg")).sendKeys("1");
        String id= Objects.requireNonNull(driver.findElement(By.id("idOgg")).getDomProperty("value"));
        int idG=Integer.parseInt(id);
        PropertyUtils.setProperty(gB,"idB",idG);
        PropertyUtils.setProperty(sB,"idB",PropertyUtils.getProperty(gB,"idB"));
        driver.findElement(By.id("procedi")).click();
        //acquista
        PropertyUtils.setProperty(sB,"titoloB",driver.findElement(By.id("nome")).getDomProperty("value"));
        driver.findElement(By.id("quantita")).clear();
        driver.findElement(By.id("quantita")).sendKeys("1");
        driver.findElement(By.id("totaleB")).click();

        String prezzo=driver.findElement(By.id("totale")).getDomProperty("value");
        assert prezzo != null;
        float p=Float.parseFloat(prezzo);
        PropertyUtils.setProperty(sB,"spesaTB",p);
        PropertyUtils.setProperty(aB,"prezzoB",PropertyUtils.getProperty(sB,"spesaTB"));
        WebElement input =driver.findElement(By.xpath("//input[@list='metodi']"));
        WebElement option =driver.findElement(By.xpath("//*[@id='metodi']/option[1]"));
        String value = option.getDomProperty("value");
        assert value != null;
        input.sendKeys(value);
        PropertyUtils.setProperty(sB,"metodoPB",value);
        driver.findElement(By.id("negozioB")).click();
        //fattura
        driver.findElement(By.id("nomeL")).clear();
        driver.findElement(By.id("cognomeL")).clear();
        driver.findElement(By.id("indirizzoL")).clear();
        driver.findElement(By.id("com")).clear();
        driver.findElement(By.id("nomeL")).sendKeys("francoB");
        driver.findElement(By.id("cognomeL")).sendKeys("rossiB");
        driver.findElement(By.id("indirizzoL")).sendKeys("via papaveri 12");
        driver.findElement(By.id("com")).sendKeys("il cap è 00005 . Chiamare prima al numero 9411526");
        String nome=driver.findElement(By.name("nomeL")).getDomProperty("value");
        String cognome=driver.findElement(By.name("cognomeL")).getDomProperty("value");
        String indirizzo=driver.findElement(By.name("indirizzoL")).getDomProperty("value");
        String com=driver.findElement(By.name("com")).getDomProperty("value");
        //setto fattura
        PropertyUtils.setProperty(fB,"nomeB",nome);
        PropertyUtils.setProperty(fB,"cognomeB",cognome);
        PropertyUtils.setProperty(fB,"indirizzoB",indirizzo);
        PropertyUtils.setProperty(fB,"comunicazioniB",com);
        driver.findElement(By.id("buttonC")).click();
        //negozio
        
        driver.findElement(By.id("buttonNeg2")).click();


        assertNotEquals(0,PropertyUtils.getProperty(sB,"idB"));
    }



    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }



}
