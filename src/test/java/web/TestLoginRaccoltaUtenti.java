package web;

import laptop.database.UsersDao;
import laptop.model.TempUser;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import web.bean.UserBean;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

 class TestLoginRaccoltaUtenti {
    WebDriver driver;
    private final TempUser tUser=TempUser.getInstance();


    private final UserBean uB=UserBean.getInstance();

    public TestLoginRaccoltaUtenti()
    {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    }

    @Test
    void testAdminUtenti() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String email;
        String pass;
        //schermata index
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/original-BookShopOnline/index.jsp");
        driver.findElement(By.id("buttonLogin")).click();
        driver.findElement(By.id("emailL")).sendKeys("admin@admin.com");
        driver.findElement(By.id("passL")).sendKeys("Admin871");
        PropertyUtils.setProperty(uB,"emailB",driver.findElement(By.id("emailL")).getAttribute("value"));
        PropertyUtils.setProperty(uB,"passB",driver.findElement(By.id("passL")).getAttribute("value"));
        driver.findElement(By.id("loginB")).click();
        driver.findElement(By.id("utentiB")).click();
        //schermata profilo
        driver.findElement(By.id("buttonProfilo")).click();
        //schermata utenti
        driver.findElement(By.id("genera lista")).click();
        //insert new user
        driver.findElement(By.id("inserisci")).click();
        driver.findElement(By.id("nomeU")).sendKeys("franco");
        driver.findElement(By.id("cognomeU")).sendKeys("rossi");
        WebElement e=driver.findElement(By.id("emailU"));
        e.sendKeys("frRossi185@libero.it");
        email=e.getAttribute("value");
        WebElement p=driver.findElement(By.id("passU"));
        p.sendKeys("fra185ros");
        pass=p.getAttribute("value");
        driver.findElement(By.id("descU")).sendKeys("user added");
        driver.findElement(By.id("dataU")).sendKeys("1970/02/03");

        PropertyUtils.setProperty(uB,"emailB",email);
        PropertyUtils.setProperty(uB,"passB",pass);


        tUser.setEmailT(PropertyUtils.getProperty(uB,"emailB").toString());

        tUser.setPasswordT(PropertyUtils.getProperty(uB,"passB").toString());



        driver.findElement(By.id("buttonI")).click();

        PropertyUtils.setProperty(uB,"idB",UsersDao.getIdMax());


        //generate list
        driver.findElement(By.id("genera lista")).click();



        driver.findElement(By.id("idOgg")).sendKeys(PropertyUtils.getProperty(uB,"idB").toString());

        driver.findElement(By.id("modifica")).click();
        //modif user
        driver.findElement(By.id("generaB")).click();

        //change parameters
        driver.findElement(By.id("ruoloNL")).sendKeys("W");
        driver.findElement(By.id("nomeNL")).sendKeys("Franco");
        driver.findElement(By.id("cognomeNL")).sendKeys("Rossi");
        driver.findElement(By.id("emailNL")).sendKeys("FRossi195@libero.it");
        driver.findElement(By.id("passNL")).sendKeys("Fra185Ros");
        driver.findElement(By.id("descNL")).sendKeys("scrittore semplice");
        driver.findElement(By.id("buttonI")).click();
        //delete user

        driver.findElement(By.id("idOgg")).sendKeys(PropertyUtils.getProperty(uB,"idB").toString());
        driver.findElement(By.id("elimina")).click();
        driver.findElement(By.id("genera lista")).click();


        assertNotEquals(0,PropertyUtils.getProperty(uB,"idB"));


    }
    @AfterEach
    void chiudiTest()
    {
        driver.close();

    }
}
