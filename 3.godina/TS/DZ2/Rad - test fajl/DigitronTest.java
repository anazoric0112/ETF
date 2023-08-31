package testing;
import calc.Calculator;
//import calc.CustomABOUTDialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import org.mockito.*;

import org.junit.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;



//@RunWith (Parameterized.class)

public class DigitronTest {
	private static Calculator calci = new Calculator();
	private static Map<String, JButton> dugmici=new HashMap<String, JButton>();
	public JLabel autput = (JLabel)calci.getContentPane().getComponent(0);
	
	@BeforeAll
	public static void spremi(){
		calci.setTitle("Java Swing Calculator");
		calci.setSize(241, 217);
		calci.pack();
		calci.setLocation(400, 250);
		calci.setVisible(true);
		calci.setResizable(false);
		
		dugmici.put("backspace", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,0).getComponentAt(0,0));
		dugmici.put("ce", (JButton)calci.getContentPane().getComponent(1).getComponentAt(162,0).getComponentAt(0,0));
		dugmici.put("c", (JButton)calci.getContentPane().getComponent(1).getComponentAt(162,0).getComponentAt(108,0));
		dugmici.put("7", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(0,0));
		dugmici.put("4", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(0,27));
		dugmici.put("1", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(0,54));
		dugmici.put("0", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(0,81));
		dugmici.put("8", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(54,0));
		dugmici.put("5", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(54,27));
		dugmici.put("2", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(54,54));
		dugmici.put("+/-", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(54,81));
		dugmici.put("9", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(108,0));
		dugmici.put("6", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(108,27));
		dugmici.put("3", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(108,54));
		dugmici.put(".", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(108,81));
		dugmici.put("/", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(162,0));
		dugmici.put("*", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(162,27));
		dugmici.put("-", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(162,54));
		dugmici.put("+", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(162,81));
		dugmici.put("sqrt", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(216,0));
		dugmici.put("1/x", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(216,27));
		dugmici.put("%", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(216,54));
		dugmici.put("=", (JButton)calci.getContentPane().getComponent(1).getComponentAt(0,27).getComponentAt(216,81));
		
		dugmici.get("c").doClick();
	}
	
	
	
	//nevalidan i validan unos za 1/x 
	//validan unos za procenat
	//+/- kada je u input modu i input je pozitivan
	//nevalidan unos za sqrt
	//dugmici sqrt, 1/x, %, backspace, +, -, *, /, +/-, = kada je error_mode ukljucen
	//dugme ce
	@Test 
	public void Test1() {
		dugmici.get("1/x").doClick();
		assertEquals("Cannot divide by zero!",autput.getText());
		
		dugmici.get("5").doClick();
		assertEquals("5",autput.getText());
		
		dugmici.get("1/x").doClick();
		assertEquals("0.2",autput.getText());
		
		dugmici.get("%").doClick();
		assertEquals("0.002",autput.getText());
		
		dugmici.get("+/-").doClick();
		assertEquals("-0.002",autput.getText());
		
		dugmici.get("sqrt").doClick();
		assertEquals("Invalid input for function!",autput.getText());

		dugmici.get("1/x").doClick();
		assertEquals("Invalid input for function!",autput.getText());
		
		dugmici.get("sqrt").doClick();
		assertEquals("Invalid input for function!",autput.getText());
		
		dugmici.get("backspace").doClick();
		assertEquals("Invalid input for function!",autput.getText());
		
		dugmici.get("%").doClick();
		assertEquals("Invalid input for function!",autput.getText());
		
		dugmici.get("=").doClick();
		assertEquals("Invalid input for function!",autput.getText());
		
		dugmici.get("+/-").doClick();
		assertEquals("Invalid input for function!",autput.getText());
		
		dugmici.get("/").doClick();
		assertEquals("Invalid input for function!",autput.getText());
		
		dugmici.get("+").doClick();
		assertEquals("Invalid input for function!",autput.getText());
		
		dugmici.get("-").doClick();
		assertEquals("Invalid input for function!",autput.getText());
		
		dugmici.get("*").doClick();
		assertEquals("Invalid input for function!",autput.getText());
		
		dugmici.get("ce").doClick();
		assertEquals("0",autput.getText());
		
		
	}
	
	//testiranje tacke (kada nista nije uneto, kada vec postoji u inputu)
	//i kada nije vec uneta i ima inputa
	//+/- kada je input mode i input je pozitivan i negativan
	@Test
	public void Test2() {
		dugmici.get("+/-").doClick();
		assertEquals("0",autput.getText());
		
		dugmici.get(".").doClick();
		assertEquals(".",autput.getText());
		
		dugmici.get("9").doClick();
		assertEquals("9",autput.getText());
		
		dugmici.get(".").doClick();
		assertEquals("9.",autput.getText());
		
		dugmici.get("7").doClick();
		assertEquals("9.7",autput.getText());
		
		dugmici.get(".").doClick();
		assertEquals("9.7",autput.getText());
		
		dugmici.get("+/-").doClick();
		assertEquals("-9.7",autput.getText());
		
		dugmici.get("+/-").doClick();
		assertEquals("9.7",autput.getText());
		
		dugmici.get("-").doClick();
		assertEquals("9.7",autput.getText());
		
		dugmici.get("8").doClick();
		assertEquals("8",autput.getText());
		
		dugmici.get("=").doClick();
		assertEquals("1.7",autput.getText());
	}
	
	//ispravno oduzimanje, +/- kada je result mode i rezultat je negativan
	//deljenje nulom kada je to poslednja operacija
	@Test
	public void Test3() {
		dugmici.get("2").doClick();
		assertEquals("2",autput.getText());
		
		dugmici.get("-").doClick();
		assertEquals("2",autput.getText());
		
		dugmici.get("3").doClick();
		assertEquals("3",autput.getText());
		
		dugmici.get("=").doClick();
		assertEquals("-1.0",autput.getText());
		
		dugmici.get("+/-").doClick();
		assertEquals("1.0",autput.getText());
		
		dugmici.get("/").doClick();
		assertEquals("1.0",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("0",autput.getText());
		
		dugmici.get("=").doClick();
		assertEquals("Cannot divide by zero!",autput.getText());
	}
	
	
	//ispravne aritmeticke operacije -,+,*,/
	//+/- u result_mode kada je rezultat 0
	//testiranje backspace-a
	@Test
	public void Test4() {
		dugmici.get("0").doClick();
		assertEquals("0",autput.getText());
		
		dugmici.get(".").doClick();
		assertEquals("0.",autput.getText());
		
		dugmici.get("4").doClick();
		assertEquals(".4",autput.getText());
		
		dugmici.get("*").doClick();
		assertEquals(".4",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("0",autput.getText());
		
		dugmici.get("=").doClick();
		assertEquals("0.0",autput.getText());
		
		dugmici.get("+/-").doClick();
		assertEquals("0.0",autput.getText());
		
		dugmici.get("+").doClick();
		assertEquals("0.0",autput.getText());
		
		dugmici.get("6").doClick();
		assertEquals("6",autput.getText());
		
		dugmici.get("/").doClick();
		assertEquals("6.0",autput.getText());
		
		dugmici.get("3").doClick();
		assertEquals("3",autput.getText());
		
		dugmici.get("=").doClick();
		assertEquals("2.0",autput.getText());
		
		dugmici.get("backspace").doClick();
		assertEquals("2.",autput.getText());
			
		dugmici.get("backspace").doClick();
		assertEquals("2",autput.getText());
			
		dugmici.get("backspace").doClick();
		assertEquals("0",autput.getText());
	}

	//nevalidan unos za %
	@Test
	public void Test5() {
		dugmici.get(".").doClick();
		assertEquals(".",autput.getText());
		
		dugmici.get("%").doClick();
		assertEquals("Invalid input for function!",autput.getText());

	}
	
	//predugacak input
	@Test
	public void Test6() {
		dugmici.get("2").doClick();
		assertEquals("2",autput.getText());
		
		dugmici.get("3").doClick();
		assertEquals("23",autput.getText());
		
		dugmici.get("4").doClick();
		assertEquals("234",autput.getText());
		
		dugmici.get("2").doClick();
		assertEquals("2342",autput.getText());
		
		dugmici.get("3").doClick();
		assertEquals("23423",autput.getText());
		
		dugmici.get("4").doClick();
		assertEquals("234234",autput.getText());
		
		dugmici.get("6").doClick();
		assertEquals("2342346",autput.getText());
		
		dugmici.get("6").doClick();
		assertEquals("23423466",autput.getText());
		
		dugmici.get("6").doClick();
		assertEquals("234234666",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("2342346660",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("23423466600",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("234234666000",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("2342346660000",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("23423466600000",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("234234666000000",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("2342346660000000",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("23423466600000000",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("234234666000000000",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("2342346660000000000",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("23423466600000000000",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("23423466600000000000",autput.getText());
	}
	
	//deljenje sa nulom a da nije poslednja operacija u nizu
	@Test
	public void Test7() {
		dugmici.get("8").doClick();
		assertEquals("8",autput.getText());
		
		dugmici.get("/").doClick();
		assertEquals("8",autput.getText());
		
		dugmici.get("0").doClick();
		assertEquals("0",autput.getText());
		
		dugmici.get("-").doClick();
		assertEquals("0",autput.getText());
		
		dugmici.get("9").doClick();
		assertEquals("9",autput.getText());
		
	}
	
	//otvaranje 'About Calculator' opcije
	//napomena: posto iz nekog razloga kada dohvatim OK dugme, .doClick() se pise da se izvrsilo ali dugme se ne klikne, 
	//prozor mora rucno da se ugasi, i zamislila sam da u ovom test primeru to bude na dugme OK a u sledecem na X
	@Test
	public void Test8() {
	    JMenuItem meni = (JMenuItem)calci.getJMenuBar().getMenu(1).getMenuComponent(0);
	    meni.doClick();
	    assertEquals(true, meni.isEnabled());
	    JDialog dlgAbout = calci.dlgAbout;
	    JButton okdugme=(JButton)dlgAbout.getContentPane().getComponent(1).getComponentAt(80, 5);
	    //System.out.println(okdugme.getAccessibleContext().getAccessibleName());
	    okdugme.doClick();
	    assertEquals(false,calci.dlgAbout.isActive());
	}
	
	//Otvaranje 'About Calculator', ali gasenje na x, posto .dispose ne radi
	@Test
	public void Test9() {
	    JMenuItem meni = (JMenuItem)calci.getJMenuBar().getMenu(1).getMenuComponent(0);
	    meni.doClick();
	    assertEquals(true, meni.isEnabled());
	}
	
	//parametrizovan test, sqrt sa ispravnim unosom
	@ParameterizedTest
	@CsvFileSource(resources = "Book1.csv", numLinesToSkip = 0)
	public void Test10(String u, String i) {
		System.out.println(u+" "+i);
		dugmici.get(u).doClick();
		assertEquals(u,autput.getText());
			
		dugmici.get("sqrt").doClick();
		assertEquals(i,autput.getText());
	}
	
	
	//----mock test
	//testiranje getNumberInDisplay
	@Test
	public void TestM1() {
		Calculator mo=mock(Calculator.class);
		when (mo.dohvatiBroj()).thenReturn(0.0);
		double rez=mo.dohvatiBroj();
		assertEquals(0.0,rez);
		
	}
		
	//----mock test
	//dodavanje broja u input
	@Test
	public void TestM2() {
		Calculator mo=mock(Calculator.class);
		when (mo.dodajBroj(7)).thenReturn(7);
		int rez=mo.dodajBroj(7);
		assertEquals(7,rez);
	}
	
	//----mock test
	//postavljanje inputa
	@Test
	public void TestM3() {
		Calculator mo=mock(Calculator.class);
		when(mo.postaviInput("7")).thenReturn("7");
		String input=mo.postaviInput("7");
		assertEquals("7",input);
	}
	
	
	@After
	public void kraj() {
		dugmici.get("c").doClick();
	}
	
	@AfterClass
	public static void ugasi() {
		JMenuItem m=(JMenuItem)calci.getJMenuBar().getMenu(0).getMenuComponent(0);
		m.doClick(); //kada se pokrene test, ova linija bude crvena kod rada sa coverage alatom,
					//medjutim kada je nema linija 234 klase Calculator je zuta, a kada je ova linija tu
					//onda je ta linija u klasi zelena 
	}
	
}


