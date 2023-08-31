<?php
include('header.php');
include('meniS.php');
include_once('db/database.inc.php');
?>
<html>
    <head>
    <link rel="stylesheet" type="text/css" href="stil.css">
    </head>
    <body>
<?php

session_start();
if($_SESSION['tip']!=1)

   header("Location: index.php");
$konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
$upit="SELECT * FROM prijavakonkurs WHERE student='".$_SESSION['kime']."'";
$rezultat=  mysqli_query($konekcija, $upit)or die("Greska u upitu1!");
if(mysqli_num_rows($rezultat)==0)
        echo "<div id='regstudent1'>Niste se prijavili ni na jedan konkurs! Vratite se na studentsku stranu <a href='student.php'>OVDE</a></div>";
else{
    $prvi=0;
    $prvi2=0;
     
    while($niz=  mysqli_fetch_array($rezultat)){
        $datumdanas=date("Y-m-d");
        $vremesad=date("h:i");
        $upit2="SELECT * FROM konkursi WHERE id='".$niz['konkurs']."'AND (datum<'".$datumdanas."'OR(datum='".$datumdanas."'AND vreme<'".$vremesad."'))";
        $rezultat2=  mysqli_query($konekcija, $upit2)or die("Greska u upitu2!");
        if(mysqli_num_rows($rezultat2)!=0)
        {
    if($prvi2==0)
    {
         echo "<table width='100%'>";
         echo "<tr>";
         echo "<td>Naziv</td>";
         echo "<td>Tekst konkursa</td>";
         echo "<td>Naziv kompanije</td>";
         echo "<td>Posao</td>";
         echo "<td>Praksa</td>";
         echo "<td>Pogledaj rezultate</td>";
    
         echo "</tr>";
    $prvi2++;
    }
        $niz2=  mysqli_fetch_array($rezultat2);
        $upit3="SELECT * FROM kompanije WHERE korime='".$niz2['korime']."'";
        $rezultat3=  mysqli_query($konekcija, $upit3)or die("Greska u upitu3!");
        $niz3=  mysqli_fetch_array($rezultat3);
        echo "<form name='Formapregledajsvojekonkurse' action='".$_SERVER['PHP_SELF']."' method='POST'>";
        echo "<tr>";
       
        echo '<td><input type="text" name="naziv" value="'.$niz2['naziv'].'" readonly></td>';
        echo '<td><input type="text" name="tekst" value="'.$niz2['tekstK'].'" readonly></td>';
        echo '<td><input type="text" name="kompanija" value="'.$niz3['naziv'].'" readonly></td>';
        echo '<td><input type="text" name="posao" value="'.$niz2['posao'].'" readonly></td>';
        echo '<td><input type="text" name="praksa" value="'.$niz2['praksa'].'" readonly></td>';
        echo '<td><input type="submit" name="pogledaj" value="POGLEDAJ REZULTATE"></td>';
        echo '<td><input type="hidden" name="id" value="'.$niz2['id'].'"></td>';
        
        echo "</tr>";
        echo "</form>";
    }}
    echo "</table>";
    if($prvi2==0){ echo "<div id='regstudent1'>Konkursi na koje ste se prijavili su jos u toku! Vratite se na studentsku stranu <a href='student.php'>OVDE</a></div>";}
}

if(array_key_exists('pogledaj', $_POST))
{$konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
 $upit4="SELECT * FROM prijavakonkurs WHERE konkurs='".$_POST['id']."' AND prihvacen=1";
 
 $rezultat4=  mysqli_query($konekcija, $upit4)or die("Greska u upitu!");
 echo "<table width='100%'>";
 echo "<tr>";
 echo "<td width='50%' align='center'>";
 if(mysqli_num_rows($rezultat4)!=0)
 { 
     echo "Primljene aplikacije:";
 echo "<table border='1'>";
    echo "<tr>";
    echo "<td>Ime</td>";
    echo "<td>Prezime</td>";
    echo "<td>Telefon</td>";
    echo "<td>E-mail</td>";
    echo "</tr>";
    
 while($niz4= mysqli_fetch_array($rezultat4)){
     $upit5="SELECT * FROM student WHERE korime='".$niz4['student']."'";
    
     $rezultat5=  mysqli_query($konekcija, $upit5)or die("Greska u upitu!");
     while($niz5=  mysqli_fetch_array($rezultat5)){
         echo '<tr>';
         echo "<td>'".$niz5['ime']."'</td>";
         echo "<td>'".$niz5['prezime']."'</td>";
         echo "<td>'".$niz5['telefon']."'</td>";
         echo "<td>'".$niz5['email']."'</td>";
         echo "</tr>";
     }
 }
 echo "</table>";
 } echo "</td>";
 $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
 $upit6="SELECT * FROM prijavakonkurs WHERE konkurs='".$_POST['id']."' AND prihvacen='-1'";
 
 $rezultat6=  mysqli_query($konekcija, $upit6)or die("Greska u upitu!");
 echo "<td width='50%' align='center'>";
 if(mysqli_num_rows($rezultat6)!=0){
     echo "Odbijene aplikacije:";
 echo "<table border='1'>";
    echo "<tr>";
    echo "<td>Ime</td>";
    echo "<td>Prezime</td>";
    echo "<td>Telefon</td>";
    echo "<td>E-mail</td>";
     echo "</tr>";
     while($niz6=  mysqli_fetch_array($rezultat6)){
     $upit7="SELECT * FROM student WHERE korime='".$niz6['student']."'";
     
     $rezultat7=  mysqli_query($konekcija, $upit7)or die("Greska u upitu7!");
     while($niz7=  mysqli_fetch_array($rezultat7)){
         echo "<tr>";
         echo "<td>'".$niz7['ime']."'</td>";
         echo "<td>'".$niz7['prezime']."'</td>";
         echo "<td>'".$niz7['telefon']."'</td>";
         echo "<td>'".$niz7['email']."'</td>";
         echo "</tr>";
     }
 }
     echo "</td></table>";
     echo "</tr>";
     echo "</table>";
 }
}
?>
    </body>
</html>
<?php include('footer.php');?>