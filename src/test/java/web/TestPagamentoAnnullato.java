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
import web.bean.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

 class TestPagamentoAnnullato {
    WebDriver driver;
    private final UserBean uB= UserBean.getInstance();
    private final RivistaBean rB=new RivistaBean();
    private final RivistaDao rD=new RivistaDao();
    private final AcquistaBean aB=new AcquistaBean();

    public TestPagamentoAnnullato() throws IOException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    }
    private final SystemBean sB= SystemBean.getInstance();
    private final Rivista r=new Rivista();
    private final FatturaBean fB=new FatturaBean();

    @Test
    void testLoginUtenteAnnullaRivista() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //schermata index
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("giuliaConforto@gmail.eu");
        driver.findElement(By.id("passL")).sendKeys("12345678Gc");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        driver.findElement(By.id("buttonR")).click();
        //schermata riviste
        //setto il tipo cosi a rivista

        PropertyUtils.setProperty(rB,"listaRivisteB", rD.getRiviste());
        PropertyUtils.setProperty(sB,"typeB", sB.getTypeB());
        driver.findElement(By.id("idOgg")).sendKeys("2");
        int id=Integer.parseInt(driver.findElement(By.id("idOgg")).getAttribute("value"));
        //aggiunto per passare titiolo
        PropertyUtils.setProperty(rB,"idB",id);
        r.setId((Integer) PropertyUtils.getProperty(rB,"idB"));
        String titolo=rD.getData(r).getTitolo();
        PropertyUtils.setProperty(sB,"idB", id);
        PropertyUtils.setProperty(rB,"idB", id);
        r.setId(id);
        PropertyUtils.setProperty(sB, "titoloB",rD.getData(r).getTitolo());
        PropertyUtils.setProperty(aB, "titoloB",sB.getTitoloB());
        PropertyUtils.setProperty(aB,"prezzoB",rD.getData(r).getPrezzo());
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
        driver.findElement(By.id("nomeT")).sendKeys("marcoB");
        driver.findElement(By.id("cognomeT")).sendKeys("arancioniB");
        driver.findElement(By.id("indirizzoT")).sendKeys("via ciclamini 12");
        driver.findElement(By.id("com")).sendKeys("il cap è 025235 . Chiamare prima al numero 118563");

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
        driver.findElement(By.id("annullaB")).click();



        assertNotEquals(0,PropertyUtils.getProperty(sB,"idB"));



    }
    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }

}
