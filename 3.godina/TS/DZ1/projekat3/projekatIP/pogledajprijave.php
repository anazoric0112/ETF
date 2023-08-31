<?php include('header.php');
include('meniK.php');
include_once('db/database.inc.php');
?>
<html>
    <head>
    <link rel="stylesheet" type="text/css" href="stil.css">
    </head>
    <body>
<?php

session_start();
if($_SESSION['tip']!=2)

   header("Location: index.php");
if(array_key_exists('pogledaj', $_POST))
{

$konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
$upit1="SELECT * FROM prijavakonkurs WHERE konkurs='".$_POST['id']."' ";
$_SESSION['id']=$_POST['id'];
$rezultat=mysqli_query($konekcija,$upit1)or die("Greska u upitu");
if(mysqli_num_rows($rezultat)!=0)
{    
    echo "<table width='100%'>";
    echo "<tr>";
    echo "<td>Ime</td>";
    echo "<td>Prezime</td>";
    echo "<td>Telefon</td>";
    echo "<td>E-mail</td>";
    echo "<td>Diplomirao</td>";
   
    echo "<td>Pogledaj CV</td>";
    echo "<td>Prihvati aplikaciju</td>";
    echo "<td>Odbij aplikaciju</td>";
    echo "</tr>";
    while($niz=  mysqli_fetch_array($rezultat))        
    {   
        $upit2= "SELECT * FROM student WHERE korime='".$niz['student']."'";
        $rezultat2=mysqli_query($konekcija,$upit2)or die("Greska u upitu");
        $niz2=  mysqli_fetch_array($rezultat2);
        
        echo "<tr>";
       echo "<form name='Formapregledajprijave' action='".$_SERVER['PHP_SELF']."' method='POST'>";
        echo '<td><input type="text" name="ime" value="'.$niz2['ime'].'" readonly></td>';
        echo '<td><input type="text" name="prezime" value="'.$niz2['prezime'].'" readonly></td>';
        echo '<td><input type="text" name="telefon" value="'.$niz2['telefon'].'" readonly></td>';
        echo '<td><input type="text" name="email" value="'.$niz2['email'].'" readonly></td>';
        
        echo '<td><input type="text" name="diplomirao" value="'.$niz2['diplomirao'].'" readonly></td>';
        
         echo '<td><input type="submit" name="CV" value="POGLEDAJ CV"></td>';
       echo '<td><input type="submit" name="prihvati" value="PRIHVATI"></td>';
        echo '<td><input type="submit" name="odbij" value="ODBIJ"></td>';
        echo '<td><input type="hidden" name="korime" value="'.$niz2['korime'].'"></td>';
        echo "</form>";
        echo "</tr>";
        
    }
    
    echo "</table>";
}}
if(array_key_exists('prihvati', $_POST))
{
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
    $upit1="SELECT * FROM prijavakonkurs WHERE student='".$_POST['korime']."' AND konkurs='".$_SESSION['id']."' AND prihvacen!='0'";
    $rezultat1=mysqli_query($konekcija,$upit1)or die("Greska u upitu");
    if(mysqli_num_rows($rezultat1)!=0) 
        echo "<div id='regstudent1'>Vec ste prihvatili ili odbili ovu aplikaciju! Vratite se na stranicu kompanije <a href='kompanija.php'>OVDE</a></div>";
    
        else{$upit="UPDATE prijavakonkurs SET prihvacen=1 WHERE student='".$_POST['korime']."'AND konkurs='".$_SESSION['id']."'";
    $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu");
        echo "<div id='regstudent1'>Uspesno ste prihvatili aplikaciju</div>";}
}
if(array_key_exists('CV', $_POST)){
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
    $upit1="SELECT * FROM biografije WHERE korime='".$_POST['korime']."'";
    $rezultat1=mysqli_query($konekcija,$upit1)or die("Greska u upitu");
    if(mysqli_num_rows($rezultat1)==0)
        echo "<div id='regstudent1'>Ovaj korisnik nije uneo biografiju!<div>";
    else
        {echo "<table width='100%'><tr><td align='center'>";
    while($niz=  mysqli_fetch_array($rezultat1))
    {
    echo "Ime:".$niz['ime']."<br>";
    echo "Prezime:".$niz['prezime']."<br>";
    echo "Adresa:".$niz['adresa']."<br>";
    echo "Postanski broj:".$niz['postanskiBroj']."<br>";
    echo "Grad:".$niz['grad']."<br>";
    echo "Drzava:".$niz['drzava']."<br>";
    echo "Telefon:".$niz['telefon']."<br>";
    echo "Web sajt:".$niz['sajt']."<br>";
    echo "E-mail:".$niz['mejl']."<br>";
    echo "Pol:".$niz['pol']."<br>";
    echo "Datum rodjenja:".$niz['datumrodj']."<br>";
    echo "Nacionalnost:".$niz['nacionalnost']."<br>";
    echo "Maternji jezik:".$niz['mjezik']."<br>";
    echo "Komunikacione vestine:".$niz['kvestine']."<br>";
    echo "Organizacione vestine:".$niz['ovestine']."<br>";
    echo "Menadzerske vestine:".$niz['mvestine']."<br>";
    echo "Druge vestine:".$niz['dvestine']."<br>";
    $upit2="SELECT * FROM radnoiskustvo WHERE korime='".$_POST['korime']."'";
    $rezultat2=mysqli_query($konekcija,$upit2)or die("Greska u upitu");
    echo "</td><td align='center'>";
    if(mysqli_num_rows($rezultat2)==0)
        echo "Radno iskustvo: nema<br>";
    else{
         while($niz2=  mysqli_fetch_array($rezultat2))
    {
             echo "Radno iskustvo:<br>";
             echo "Kompanija:".$niz2['kompanija']."<br>";
             echo "Grad:".$niz2['grad']."<br>";
             echo "Drzava:".$niz2['drzava']."<br>";
             echo "Od:".$niz2['od']."<br>";
             echo "Do:".$niz2['do']."<br>";
             echo "Pozicija:".$niz2['pozicija']."<br>";
    }
    }echo "</td><td align='center'>";
    $upit3="SELECT * FROM obrazovanje WHERE korime='".$_POST['korime']."'";
    $rezultat3=mysqli_query($konekcija,$upit3)or die("Greska u upitu");
    if(mysqli_num_rows($rezultat3)==0)
        echo "Obrazovanje: nema<br>";
    else{
         while($niz3=  mysqli_fetch_array($rezultat3))
    {
             echo "Obrazovanje:<br>";
             echo "Titula:".$niz3['titula']."<br>";
             echo "Fakultet:".$niz3['fakultet']."<br>";
             echo "Grad:".$niz3['grad']."<br>";
             echo "Drzava:".$niz3['fakultet']."<br>";
             echo "Studirao od:".$niz3['od'];
             echo "do:".$niz3['do']."<br>";
             
    }
    }
    echo "</td><td align='center'>";
    $upit4="SELECT * FROM jezici WHERE korime='".$_POST['korime']."'";
    $rezultat4=mysqli_query($konekcija,$upit4)or die("Greska u upitu");
    if(mysqli_num_rows($rezultat4)==0)
        echo "Poznavanje stranih jezika: nema<br>";
    else{
         while($niz4=  mysqli_fetch_array($rezultat4))
    {
             
             echo "Strani jezik:".$niz4['jezik']."<br>";
             echo "Razumevanje:".$niz4['razumevanje']."<br>";
             echo "Citanje:".$niz4['citanje']."<br>";
             echo "Pricanje:".$niz4['pricanje']."<br>";
             echo "Pisanje:".$niz4['pisanje']."<br>";
             
    }
    }echo "</td></tr></table>";
    
    }
    
               }


}
if(array_key_exists('odbij', $_POST))
{
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
    $upit1="SELECT * FROM prijavakonkurs WHERE student='".$_POST['korime']."' AND konkurs='".$_SESSION['id']."' AND prihvacen!=''";
    $rezultat1=mysqli_query($konekcija,$upit1)or die("Greska u upitu");
    if(mysqli_num_rows($rezultat1)!=0) echo "<div id='regstudent1'>Vec ste prihvatili ili odbili ovu aplikaciju!</div>";
    
        else{$upit="UPDATE prijavakonkurs SET prihvacen=-1 WHERE student='".$_POST['korime']."'AND konkurs='".$_SESSION['id']."'";
    $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu");
        echo "<div id='regstudent1'>Uspesno ste odbili aplikaciju</div>";}
}
?>
   </body> </html>
<?php include('footer.php');?>