/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package Tests;

import static a.testici.Testovi.baseUrl;
import static a.testici.Testovi.driver;
import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeDriver;
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
public class EdgeNeU {
    
    public EdgeNeU() {
        System.setProperty("webdriver.edge.driver","D:\\TS SVE NOVO\\exe i sl\\msedgedriver.exe");
        driver= new EdgeDriver();
        driver.get(baseUrl);
        driver.manage().window().maximize();
    }
    @Test 
    public void LoginNU1(){
        String korime="nepostojim123";
        String pass="Sifra123#";
        String poruka="Pogresno korisnicko ime";
        LoginTest(korime,pass,poruka);
    }
    @Test 
    public void LoginNU2(){
        String korime="";
        String pass="Sifra123#";
        String poruka="Pogresno korisnicko ime";
        LoginTest(korime,pass,poruka);
        
    }
    @Test 
    public void LoginNU3(){
        String korime="aleksa";
        String pass="";
        String poruka="Pogresno korisnicko ime ili sifra";
        LoginTest(korime,pass,poruka);
        
    }
    @Test 
    public void LoginNU4(){
        String korime="aleksa";
        String pass="sIfra123";
        String poruka="Pogresno korisnicko ime ili sifra";
        LoginTest(korime,pass,poruka);
        if (driver!=null) driver.quit();
    }
    public static void LoginTest(String user, String pass, String poruka){
        driver.findElement(By.name("user")).sendKeys(user);
        driver.findElement(By.name("pass")).sendKeys(pass);
        driver.findElement(By.name("potvrdi")).click();
        String msg = driver.findElement(By.id("regstudent1")).getText();
        Assert.assertTrue(msg.contains(poruka));
        driver.navigate().back();
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
