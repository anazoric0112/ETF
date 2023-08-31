package Tests;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */

import static a.testici.Testovi.baseUrl;
import static a.testici.Testovi.driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author anazo
 */
public class UspesnoOtvaranjeKonkursa {
    
    public UspesnoOtvaranjeKonkursa() {
        
            System.setProperty("webdriver.chrome.driver", "D:\\TS SVE NOVO\\exe i sl\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.get(baseUrl);
            driver.manage().window().maximize();
            Login("pericadj","Sifra123$","Dobrodosli");
            driver.findElement(By.linkText("OTVORI KONKURS")).click();
    }

    @Test
    public void OtvaranjeKonkursaTest1(){
        try{

            String imeKonkursa = "Konkurs 100";
            String Tekst = "Prijavite se";
            boolean praksa= false; boolean posao = true;
            String datum= "12/25/2021";
            String vreme="12:00AM";
            OtvoriKonkursTest(imeKonkursa,Tekst,praksa,posao,datum,vreme);
        }catch (Throwable exc){
            System.out.println("Test 1 Fail: "+exc);
        }
    }
    
    @Test
    public void OtvaranjeKonkursaTest2(){
        try{
            String imeKonkursa = "Konkurs za praksu";
            String Tekst = "Dodjite";
            boolean praksa= true; boolean posao = false;
            String datum= "12/25/2021";
            String vreme="12:00AM";
            OtvoriKonkursTest(imeKonkursa,Tekst,praksa,posao,datum,vreme);
        }catch (Throwable exc){
            System.out.println("Test 2 Fail: "+exc);
        }
    }
    
    @Test
    public void OtvaranjeKonkursaTest3(){
        try{
            String imeKonkursa = "Poslovi i prakse";
            String Tekst = "Opis posla";
            boolean praksa= true; boolean posao = true;
            String datum= "12/31/2021";
            String vreme="12:00AM";
            OtvoriKonkursTest(imeKonkursa,Tekst,praksa,posao,datum,vreme);
        }catch (Throwable exc){
            System.out.println("Test 3 Fail: "+exc);
        }
        if (driver!=null) driver.quit();
    }
    
    public static void OtvoriKonkursTest(String ime, String t, boolean pr, boolean po,
                    String dat, String vr){
        driver.findElement(By.name("naziv")).sendKeys(ime);
        if (po) driver.findElement(By.id("posao")).click();
        if (pr) driver.findElement(By.id("praksa")).click();
        driver.findElement(By.name("tekstK")).sendKeys(t);
        driver.findElement(By.name("datum")).sendKeys(dat);
        driver.findElement(By.name("vreme")).sendKeys(vr);
        driver.findElement(By.name("potvrdi")).click();
        
        String msg=driver.findElement(By.id("regstudent1")).getText();
        Assert.assertTrue(msg.contains("Uspesno"));
    }
    
    
    public static void Login(String user, String pass, String poruka){
        driver.findElement(By.name("user")).sendKeys(user);
        driver.findElement(By.name("pass")).sendKeys(pass);
        driver.findElement(By.name("potvrdi")).click();
        
        String welcomeMsg = driver.findElement(By.id("dobrodosao")).getText();
        Assert.assertTrue(welcomeMsg.contains(poruka));
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
