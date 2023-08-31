/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Ana Zoric
 */
public class Main {

    private static void sendGet(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { 
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) 
                response.append(inputLine+"\n");
            in.close();
            System.out.println(response.toString());
        } else {
            System.out.println("GET neuspesan");
        }

    }
    private static void sendPost(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        int responseCode = con.getResponseCode();
        System.out.println("POST: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) 
                response.append(inputLine+"\n");
            in.close();
            System.out.println(response.toString());
        } else {
            System.out.println("POST neuspesan");
        }
    }
    private static void sendPut(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("PUT");

        int responseCode = con.getResponseCode();
        System.out.println("PUT: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) 
                response.append(inputLine+"\n");
            in.close();
            System.out.println(response.toString());
        } else {
            System.out.println("PUT neuspesan");
        }
    }
    private static void meni(){
        System.out.println("Unesite redni broj operacije koju zelite da izvrsite");
        System.out.println("1. Kreiranje korisnika");
        System.out.println("2. Kreiranje grada");
        System.out.println("3. Dodavanje novca korisniku");
        System.out.println("4. Promena adrese korisnika");
        System.out.println("5. Dohvatanje svih gradova");
        System.out.println("6. Dohvatanje svih korisnika");
        System.out.println("7. Kreirajte kategoriju proizvoda");
        System.out.println("8. Kreirajte artikal");
        System.out.println("9. Promenite cenu artikla");
        System.out.println("10. Promenite popust artiklu");
        System.out.println("11. Dodajte artikal u nekoj kolicini u korpu");
        System.out.println("12. Izbrisite artikal u nekoj kolicini iz korpe");
        System.out.println("13. Dohvatanje svih kategorija");
        System.out.println("14. Dohvatanje svih artikala iz korpe korisnika");
        System.out.println("15. Dohvatanje svih artikala koje korisnik prodaje");
        System.out.println("16. Placanje");
        System.out.println("17. Dohvatanje svih narudzbina korisnika");
        System.out.println("18. Dohvatanje svih narudzbina");
        System.out.println("19. Dohvatanje svih transakcija");
        System.out.println("20. Kraj rada");
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean end=false;
        while (!end){
            meni();
            int izbor; izbor=Integer.parseInt(in.readLine());
            String grad,u,pw,ime,prezime,adresa;
            int novac;
            String kat,potkat,naziv,opis,cena,popust,cnt;
            switch(izbor){
                case 1:
                    System.out.println("Unesite username, password, ime, prezime, adresu, naziv grada"
                            + "i kolicinu novca u razlicitim redovima");
                    u=in.readLine()+"/";
                    pw=in.readLine()+"/";
                    ime=in.readLine()+"/";
                    prezime=in.readLine()+"/";
                    adresa=in.readLine()+"/";
                    grad=in.readLine()+"/";
                    novac=Integer.parseInt(in.readLine());
                    Main.sendPost("http://localhost:8080/Server/resources/korisnici/"+u+pw+ime+prezime+adresa+grad+novac);
                    break;
                case 2:
                    System.out.println("Unesite naziv grada");
                    grad=in.readLine();
                    Main.sendPost("http://localhost:8080/Server/resources/gradovi/"+grad);
                    break;
                case 3:
                    System.out.println("Unesite username korisnika i sumu novca u razlicitim redovima");
                    u=in.readLine()+"/";
                    novac=Integer.parseInt(in.readLine());
                    Main.sendPut("http://localhost:8080/Server/resources/korisnici/"+u+novac);
                    break;
                case 4:
                    System.out.println("Unesite username korisnika, adresu i grad u razlicitim redovima");
                    u=in.readLine()+"/";
                    adresa=in.readLine()+"/";
                    grad=in.readLine();
                    Main.sendPut("http://localhost:8080/Server/resources/korisnici/"+u+adresa+grad);
                    break;
                case 5:
                    Main.sendGet("http://localhost:8080/Server/resources/gradovi");
                    break;
                case 6:
                    Main.sendGet("http://localhost:8080/Server/resources/korisnici");
                    break;
                case 7:
                    System.out.println("Unesite naziv kategorije");
                    kat=in.readLine();
                    System.out.println("Da li je data kategorija potkategorija neke kategorije? Upisite da/ne");
                    String je_li=in.readLine();
                    if (je_li.equals("da")){
                        System.out.println("Unesite naziv potkategorije");
                        potkat=in.readLine();
                        Main.sendPost("http://localhost:8080/Server/resources/kategorije?naziv="+kat+"&potkat="+potkat);
                    }else Main.sendPost("http://localhost:8080/Server/resources/kategorije?naziv="+kat);
                    break;
                case 8:
                    System.out.println("Unesite naziv, opis, cenu, popust, kategoriju i vas username i password u reazlicitim redovima");
                    naziv=in.readLine()+"/";
                    opis=in.readLine()+"/";
                    cena=in.readLine()+"/";
                    popust=in.readLine()+"/";
                    kat=in.readLine()+"/";
                    u=in.readLine()+"/";
                    pw=in.readLine();
                    Main.sendPost("http://localhost:8080/Server/resources/artikli/"+naziv+opis+cena+popust+kat+u+pw);
                    break;
                case 9:
                    System.out.println("Unesite naziv artikla i cenu i vas username i password razlicitim redovima");
                    naziv=in.readLine()+"/";
                    cena=in.readLine()+"/";
                    u=in.readLine()+"/";
                    pw=in.readLine();
                    Main.sendPut("http://localhost:8080/Server/resources/artikli/cena/"+naziv+cena+u+pw);
                    break;
                case 10:
                    System.out.println("Unesite naziv artikla i popust i vas username i password u razlicitim redovima");
                    naziv=in.readLine()+"/";
                    popust=in.readLine()+"/";
                    u=in.readLine()+"/";
                    pw=in.readLine();
                    Main.sendPut("http://localhost:8080/Server/resources/artikli/popust/"+naziv+popust+u+pw);
                    break;
                case 11:
                    System.out.println("Unesite naziv artikla, kolicinu i svoj username i password u reazlicitim redovima");
                    naziv=in.readLine()+"/";
                    cnt=in.readLine()+"/";
                    u=in.readLine()+"/";
                    pw=in.readLine();
                    Main.sendPost("http://localhost:8080/Server/resources/korpa/dodaj/"+naziv+cnt+u+pw);
                    break;
                case 12:
                    System.out.println("Unesite naziv artikla, kolicinu i svoj username i password u reazlicitim redovima");
                    naziv=in.readLine()+"/";
                    cnt=in.readLine()+"/";
                    u=in.readLine()+"/";
                    pw=in.readLine();
                    Main.sendPut("http://localhost:8080/Server/resources/korpa/izbaci/"+naziv+cnt+u+pw);
                    break;
                case 13:
                    Main.sendGet("http://localhost:8080/Server/resources/kategorije");
                    break;
                case 14:
                    System.out.println("Unesite svoj username i password u razlicitim redovima");
                    u=in.readLine()+"/";
                    pw=in.readLine();
                    Main.sendGet("http://localhost:8080/Server/resources/korpa/"+u+pw);
                    break;
                case 15:
                    System.out.println("Unesite svoj username i password u razlicitim redovima");
                    u=in.readLine()+"/";
                    pw=in.readLine();
                    Main.sendGet("http://localhost:8080/Server/resources/artikli/"+u+pw);
                    break;
                case 16:
                    System.out.println("Unesite svoj username i password u razlicitim redovima");
                    u=in.readLine()+"/";
                    pw=in.readLine();
                    Main.sendPost("http://localhost:8080/Server/resources/transakcije/"+u+pw);
                    break;
                case 17:
                    System.out.println("Unesite svoj username i password u razlicitim redovima");
                    u=in.readLine()+"/";
                    pw=in.readLine();
                    Main.sendGet("http://localhost:8080/Server/resources/narudzbine/"+u+pw);
                    break;
                case 18:
                    Main.sendGet("http://localhost:8080/Server/resources/narudzbine");
                    break;
                case 19:
                    Main.sendGet("http://localhost:8080/Server/resources/transakcije");
                    break;
                case 20:
                    Main.sendPost("http://localhost:8080/Server/resources/kraj");
                    end=true;
            }
        }
        System.out.print("Aplikacija uspesno zavrsila sa radom");
    }
    
}
