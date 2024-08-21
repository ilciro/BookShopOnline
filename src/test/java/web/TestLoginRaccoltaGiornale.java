package web;

import laptop.database.GiornaleDao;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import web.bean.GiornaleBean;
import web.bean.UserBean;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

 class TestLoginRaccoltaGiornale {
    WebDriver driver;

    private final GiornaleBean gB=new GiornaleBean();
    private final UserBean uB=UserBean.getInstance();
    private final GiornaleDao gD=new GiornaleDao();


    public TestLoginRaccoltaGiornale()
    {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    }




    @Test
    void testLoginAdminRaccoltaGiornale() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

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
        driver.findElement(By.id("buttonG")).click();
        //ho generato la lista
        driver.findElement(By.id("buttonGenera")).click();
        // 1 ) inserisco libro
        driver.findElement(By.id("buttonAdd")).click();
        //insierimento nuovo giornale
        driver.findElement(By.id("titoloG")).sendKeys("provo ad inserire un nuovo giornale");
        driver.findElement(By.id("linguaG")).sendKeys("italiano");
        driver.findElement(By.id("editoreG")).sendKeys("editore Prova");
        driver.findElement(By.id("dataG")).sendKeys("2024/01/08");
        driver.findElement(By.id("copieG")).sendKeys("52");
        driver.findElement(By.id("dispG")).sendKeys("1");
        driver.findElement(By.id("prezzoG")).sendKeys("1.65f");
        driver.findElement(By.id("confermaB")).click();
        driver.findElement(By.id("buttonGenera")).click();
        //get last id

        driver.findElement(By.id("idL")).sendKeys(PropertyUtils.getProperty(gB,"idB").toString());
        driver.findElement(By.id("buttonMod")).click();
        driver.findElement(By.id("listaB")).click();
        //modif
        driver.findElement(By.id("titoloNG")).sendKeys("La gazzetta del pirla");
        driver.findElement(By.id("linguaNG")).sendKeys("italiano");
        driver.findElement(By.id("editoreNG")).sendKeys("mondadori");
        driver.findElement(By.id("dataNG")).sendKeys("2024/02/18");
        driver.findElement(By.id("copieNG")).sendKeys("100");
        driver.findElement(By.id("dispNG")).sendKeys("1");
        driver.findElement(By.id("prezzoNG")).sendKeys("4.56f");
        driver.findElement(By.id("buttonI")).click();
        driver.findElement(By.id("buttonGenera")).click();
        PropertyUtils.setProperty(gB,"idB",gD.getIdMax());

        //delete
        driver.findElement(By.id("idL")).sendKeys(PropertyUtils.getProperty(gB,"idB").toString());
        driver.findElement(By.id("buttonCanc")).click();
        assertNotEquals(0,PropertyUtils.getProperty(gB,"idB"));



    }
    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }
}
