package web;

import laptop.database.GiornaleDao;
import laptop.database.NegozioDao;
import laptop.model.raccolta.Giornale;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import web.bean.*;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class TestPagamentoCartaCredito {
    WebDriver driver;
    private final UserBean uB=UserBean.getInstance();
    private final Giornale g=new Giornale();
    private final GiornaleBean gB=new GiornaleBean();
    private final GiornaleDao gD=new GiornaleDao();
    private final SystemBean sB= SystemBean.getInstance();
    private final AcquistaBean aB=new AcquistaBean();
    private final CartaCreditoBean cCB=new CartaCreditoBean();
    private final PagamentoBean pB=new PagamentoBean();
    private final NegozioDao nD=new NegozioDao();
    private final NegozioBean nB=new NegozioBean();

    public TestPagamentoCartaCredito()
    {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    }
    @Test
    void testLoginUserGiornale() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,  ParseException {
        //schermata index
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("giuliaConforto@gmail.eu");
        driver.findElement(By.id("passL")).sendKeys("12345678Gc");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        driver.findElement(By.id("buttonG")).click();
        //schermata giornali
        PropertyUtils.setProperty(gB,"listaGiornaliB", gD.getGiornali());
        PropertyUtils.setProperty(sB,"typeB", sB.getTypeB());
        driver.findElement(By.id("idOgg")).sendKeys("1");
        int id=Integer.parseInt(driver.findElement(By.id("idOgg")).getAttribute("value"));
        PropertyUtils.setProperty(sB,"categoriaB","QUOTIDIANO");
        PropertyUtils.setProperty(sB,"idB", id);
        PropertyUtils.setProperty(gB,"idB", id);
        g.setId(id);
        PropertyUtils.setProperty(sB, "titoloB",gD.getData(g).getTitolo());
        PropertyUtils.setProperty(aB, "titoloB",sB.getTitoloB());
        PropertyUtils.setProperty(aB,"prezzoB",gD.getData(g).getPrezzo());
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
        //metodo cCredito
        WebElement input =driver.findElement(By.xpath("//input[@list='metodi']"));
        WebElement option =driver.findElement(By.xpath("//*[@id='metodi']/option[2]"));
        String value = option.getAttribute("value");
        input.sendKeys(value);
        PropertyUtils.setProperty(sB,"metodoPB",value);
        driver.findElement(By.id("negozioB")).click();
        //schermata cartacredito

        driver.findElement(By.id("nomeL")).sendKeys("luigiB");
        driver.findElement(By.id("cognomeL")).sendKeys("neriB");
        driver.findElement(By.id("cartaL")).sendKeys("1995-8412-6632-2500");
        driver.findElement(By.id("scadL")).sendKeys("2028/08/01");
        driver.findElement(By.id("passL")).sendKeys("185");

        String nome=driver.findElement(By.id("nomeL")).getAttribute("value");
        String cognome=driver.findElement(By.id("cognomeL")).getAttribute("value");
        String numeroCarta=driver.findElement(By.id("cartaL")).getAttribute("value");
        String scadenza=driver.findElement(By.id("scadL")).getAttribute("value");
        String civ=driver.findElement(By.id("passL")).getAttribute("value");

        cCB.setNomeB(nome);
        cCB.setCognomeB(cognome);
        cCB.setNumeroCCB(numeroCarta);

        PropertyUtils.setProperty(cCB,"nomeB",nome);
        PropertyUtils.setProperty(cCB,"cognomeB",cognome);
        PropertyUtils.setProperty(cCB,"numeroCCB",numeroCarta);


        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date utilDate = format.parse(scadenza);

        cCB.setDataScadB(utilDate);
        PropertyUtils.setProperty(cCB,"dataScadB",utilDate);
        cCB.setPrezzoTransazioneB(sB.getSpesaTB());
        PropertyUtils.setProperty(cCB,"prezzoTransazioneB",PropertyUtils.getProperty(sB,"spesaTB"));
        cCB.setCivB(civ);
        PropertyUtils.setProperty(cCB,"civB",civ);

        pB.setIdOggettoB(0);
        pB.setMetodoB(value);
        pB.setNomeUtenteB(cCB.getNomeB());
        pB.setAmmontareB(sB.getSpesaTB());
        pB.setTipoB(sB.getCategoriaB());
        pB.setIdB(sB.getIdB());

        PropertyUtils.setProperty(pB,"idB",0);
        PropertyUtils.setProperty(pB,"metodoB",value);
        PropertyUtils.setProperty(pB,"nomeUtenteB",PropertyUtils.getProperty(cCB,"nomeB"));
        PropertyUtils.setProperty(pB,"ammontareB",PropertyUtils.getProperty(sB,"spesaTB"));
        PropertyUtils.setProperty(pB,"tipoB",PropertyUtils.getProperty(sB,"categoriaB"));
        PropertyUtils.setProperty(pB,"idOggettoB",PropertyUtils.getProperty(sB,"idB"));

        driver.findElement(By.id("buttonI")).click();
        //schermata negozio

        //Negozio A
        PropertyUtils.setProperty(nB,"nomeB",nD.getNegozi().get(0).getNome());
        PropertyUtils.setProperty(nB,"openB",nD.getNegozi().get(0).getIsOpen());
        PropertyUtils.setProperty(nB,"validB",nD.getNegozi().get(0).getIsValid());
        PropertyUtils.setProperty(nB,"mexB","negozio non disponibile e/o chiuso");
        driver.findElement(By.id("buttonNeg1")).click();
        //Negozio C
        PropertyUtils.setProperty(nB,"nomeB",nD.getNegozi().get(2).getNome());
        PropertyUtils.setProperty(nB,"openB",nD.getNegozi().get(2).getIsOpen());
        PropertyUtils.setProperty(nB,"validB",nD.getNegozi().get(2).getIsValid());
        PropertyUtils.setProperty(nB,"mexB","negozio non disponibile e/o chiuso");
        driver.findElement(By.id("buttonNeg3")).click();
        //Negozio D
        PropertyUtils.setProperty(nB,"nomeB",nD.getNegozi().get(3).getNome());
        PropertyUtils.setProperty(nB,"openB",nD.getNegozi().get(3).getIsOpen());
        PropertyUtils.setProperty(nB,"validB",nD.getNegozi().get(3).getIsValid());
        PropertyUtils.setProperty(nB,"mexB","negozio non disponibile e/o chiuso");
        driver.findElement(By.id("buttonNeg4")).click();
        //Negozio B
        PropertyUtils.setProperty(nB,"nomeB",nD.getNegozi().get(1).getNome());
        PropertyUtils.setProperty(nB,"openB",nD.getNegozi().get(1).getIsOpen());
        PropertyUtils.setProperty(nB,"validB",nD.getNegozi().get(1).getIsValid());
        driver.findElement(By.id("buttonNeg2")).click();



        assertEquals(1,PropertyUtils.getProperty(sB,"idB"));
    }
    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }
}
