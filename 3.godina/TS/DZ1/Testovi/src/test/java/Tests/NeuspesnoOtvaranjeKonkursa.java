/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package Tests;

import static Tests.UspesnoOtvaranjeKonkursa.Login;
import static Tests.UspesnoOtvaranjeKonkursa.OtvoriKonkursTest;
import static a.testici.Testovi.baseUrl;
import static a.testici.Testovi.driver;
import org.openqa.selenium.By;
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
public class NeuspesnoOtvaranjeKonkursa {
    
    public NeuspesnoOtvaranjeKonkursa() {
            System.setProperty("webdriver.chrome.driver", "D:\\TS SVE NOVO\\exe i sl\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.get(baseUrl);
            driver.manage().window().maximize();
            Login("pericadj","Sifra123$","Dobrodosli");
            driver.findElement(By.linkText("OTVORI KONKURS")).click();
    }

    @Test
    public void OtvaranjeKonkursaTest4(){
        //try{

            String imeKonkursa = "Nesto";
            String Tekst = "Nesto";
            boolean praksa= false; boolean posao = false;
            String datum= "12/22/2021";
            String vreme="12:00AM";
            String p="Konkurs mora biti bilo za posao bilo za praksu";
            OtvoriKonkursTest(imeKonkursa,Tekst,praksa,posao,datum,vreme,p);
//        }catch (Throwable exc){
//            System.out.println("Test 4 Fail: "+exc);
//        }
    }
    
    @Test
    public void OtvaranjeKonkursaTest5(){
        //try{

            String imeKonkursa = "";
            String Tekst = "Zaposlite se";
            boolean praksa= false; boolean posao = true;
            String datum= "12/27/2021";
            String vreme="12:00AM";
            String p="Polje naziv ne sme biti prazno!";
            OtvoriKonkursTest(imeKonkursa,Tekst,praksa,posao,datum,vreme,p);
//        }catch (Throwable exc){
//            System.out.println("Test 5 Fail: "+exc);
//        }
    }
    
    @Test
    public void OtvaranjeKonkursaTest6(){
        //try{

            String imeKonkursa = "Konkursi 1";
            String Tekst = "";
            boolean praksa= false; boolean posao = true;
            String datum= "12/20/2021";
            String vreme="12:00AM";
            String p="Polje tekst ne sme biti prazno!";
            OtvoriKonkursTest(imeKonkursa,Tekst,praksa,posao,datum,vreme,p);
//        }catch (Throwable exc){
//            System.out.println("Test 6 Fail: "+exc);
//        }
    }
    
    @Test
    public void OtvaranjeKonkursaTest7(){
        //try{

            String imeKonkursa = "Novo";
            String Tekst = "Prijavite se za posao";
            boolean praksa= false; boolean posao = true;
            String datum= "11/11/2021";
            String vreme="11:00AM";
            String p="Datum ne sme biti u proslosti";
            OtvoriKonkursTest(imeKonkursa,Tekst,praksa,posao,datum,vreme,p);
//        }catch (Throwable exc){
//            System.out.println("Test 7 Fail: "+exc);
//        }
        if (driver!=null) driver.quit();
    }
    
    public static void OtvoriKonkursTest(String ime, String t, boolean pr, boolean po,
                    String dat, String vr,String poruka){
        driver.findElement(By.name("naziv")).sendKeys(ime);
        if (po) driver.findElement(By.id("posao")).click();
        if (pr) driver.findElement(By.id("praksa")).click();
        driver.findElement(By.name("tekstK")).sendKeys(t);
        driver.findElement(By.name("datum")).sendKeys(dat);
        driver.findElement(By.name("vreme")).sendKeys(vr);
        driver.findElement(By.name("potvrdi")).click();
        
        try {
            String msg=driver.switchTo().alert().getText();
            Assert.assertTrue(msg.contains(poruka));
            driver.switchTo().alert().accept();
            //System.out.println("popup");
        } catch (Throwable exc){
            String msg=driver.findElement(By.id("regstudent1")).getText();
            Assert.assertTrue(msg.contains(poruka));
            //System.out.println("poruka");
        }
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
