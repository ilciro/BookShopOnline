package web;


import laptop.database.*;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import web.bean.*;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


import static org.junit.jupiter.api.Assertions.*;


class TestLoginRaccoltaLibro {
    WebDriver driver;
    private final LibroBean lB=new LibroBean();

    private final UserBean uB=UserBean.getInstance();
    private final LibroDao lD=new LibroDao();


    TestLoginRaccoltaLibro() throws IOException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    }


    // Test for Admin all functionalities



  @Test
    void testLoginAdminRaccoltaLibro() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        //usato per prendere id


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
        driver.findElement(By.id("raccoltaB")).click();
        //schermata raccolta
        driver.findElement(By.id("buttonL")).click();
        //ho generato la lista
        driver.findElement(By.id("buttonGenera")).click();
        // 1 ) inserisco libro
        driver.findElement(By.id("buttonAdd")).click();
        //insierimento nuovo libro
        driver.findElement(By.id("titoloL")).sendKeys("provo ad inserire un nuovo libro");
        driver.findElement(By.id("nrPagL")).sendKeys("150");
        driver.findElement(By.id("codL")).sendKeys("18552963");
        driver.findElement(By.id("autoreL")).sendKeys("autore Prova");
        driver.findElement(By.id("editoreL")).sendKeys("editore Prova");
        driver.findElement(By.id("linguaL")).sendKeys("italiano");
        driver.findElement(By.id("catS")).sendKeys("ARTE");
        driver.findElement(By.id("dataL")).sendKeys("2024/11/03");
        driver.findElement(By.id("recensioneL")).sendKeys(" questo e un libro inserito");
        driver.findElement(By.id("descL")).sendKeys("libro inserito per test");
        //non disponibile -> non clicco su checkbox
        driver.findElement(By.id("prezzoL")).sendKeys("5f");
        driver.findElement(By.id("copieL")).sendKeys("16");
        driver.findElement(By.id("confermaB")).click();


        //previous page

        driver.findElement(By.id("buttonGenera")).click();
        //get last id

        driver.findElement(By.id("idL")).sendKeys(PropertyUtils.getProperty(lB,"idB").toString());
        driver.findElement(By.id("buttonMod")).click();
        //schermata modifica

        driver.findElement(By.id("listaB")).click();
        //update
        driver.findElement(By.id("titoloNL")).sendKeys("un bel libro aggironato");
        driver.findElement(By.id("pagineNL")).sendKeys("180");
        driver.findElement(By.id("codiceNL")).sendKeys("16632510");
        driver.findElement(By.id("editoreNL")).sendKeys("hoepli");
        driver.findElement(By.id("autoreNL")).sendKeys("hoepli");
        driver.findElement(By.id("linguaNL")).sendKeys("italiano");
        driver.findElement(By.id("categoriaNL")).sendKeys("SCIENZE");
        driver.findElement(By.id("dataNL")).sendKeys("02/05/2024");
        driver.findElement(By.id("recNL")).sendKeys("libro aggiornato");
        driver.findElement(By.id("copieNL")).sendKeys("75");
        driver.findElement(By.id("descNL")).sendKeys("un bel libro aggironato");
        driver.findElement(By.id("dispNL")).sendKeys("0");
        driver.findElement(By.id("prezzoNL")).sendKeys("7.52f");
        driver.findElement(By.id("buttonI")).click();
        //updated book
        driver.findElement(By.id("buttonGenera")).click();

        PropertyUtils.setProperty(lB,"idB",lD.getIdMax());
        driver.findElement(By.id("idL")).sendKeys(String.valueOf(PropertyUtils.getProperty(lB,"idB")));
        //delete
        driver.findElement(By.id("buttonCanc")).click();
        driver.findElement(By.id("buttonGenera")).click();


        assertNotEquals(0,PropertyUtils.getProperty(lB,"idB"));

    }
    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }



}


