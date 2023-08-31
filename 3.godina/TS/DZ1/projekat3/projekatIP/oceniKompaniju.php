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
$datumdanas=date("Y-m-d");
$konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji!");
$upit="SELECT * FROM radi WHERE student='".$_SESSION['kime']."' AND DATE_ADD(od,INTERVAL 30 day)<('".$datumdanas."')";
$rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu");
if(mysqli_num_rows($rezultat)==0)
    echo "<div id='regstudent1'>Ne postoji nijedna kompanija u kojoj ste zaposleni duze od 30 dana! Vratite se na studentsku stranicu <a href='student.php'>OVDE</a></div>";
    else 
    {   echo "<table width='100%'>";
        echo "<tr>";
        echo "<td>Naziv kompanije</td>";
        echo "<td>Grad</td>";
        echo "<td>Adresa</td>";
        echo "<td>Delatnost</td>";
        echo "<td>Uza specijanost</td>";
        echo "<td>Ocena</td>";
        echo "<td>Oceni</td>";
        echo "</tr>";
        while($niz= mysqli_fetch_array($rezultat)){
        $upit2= "SELECT * FROM kompanije WHERE korime='".$niz['kompanija']."'";
        $rezultat2=mysqli_query($konekcija,$upit2)or die("Greska u upitu");
        while($niz2= mysqli_fetch_array($rezultat2)){
            echo "<tr>";
            echo "<form name='FormaOcena' method='POST' action='".$_SERVER['PHP_SELF']."'>";
            echo "<td><input type='text' name='naziv' value='".$niz2['naziv']."'</td>";
            echo "<td><input type='text' name='grad' value='".$niz2['grad']."'</td>";
            echo "<td><input type='text' name='adresa' value='".$niz2['adresa']."'</td>";
            echo "<td><input type='text' name='delatnost' value='".$niz2['delatnost']."'</td>";
            echo "<td><input type='text' name='specijalnost' value='".$niz2['specijalnost']."'</td>";
            echo "<td><select name='ocena'>";
            echo "<option value='1'>1";
            echo "<option value='2'>2";
            echo "<option value='3'>3";
            echo "<option value='4'>4";
            echo "<option value='5'>5";
            echo "<option value='6'>6";
            echo "<option value='7'>7";
            echo "<option value='8'>8";
            echo "<option value='9'>9";
            echo "<option value='10'>10";
            echo "</select></td>";
            echo "<td><input type='submit' name='oceni' value='OCENI'></td>";
            echo "<td><input type='hidden' name='korime' value='".$niz2['korime']."'</td>";
            echo "</form>";
            echo "</tr>";
        }
        
        }
  echo "</table>";
    }
    if(array_key_exists('oceni', $_POST))
    {
        $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
        $upit="SELECT * FROM ocene WHERE student='".$_SESSION['kime']."'AND kompanija='".$_POST['korime']."'";
        $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu");
        if(mysqli_num_rows($rezultat)!=0)
            echo "<div id='regstudent1'>Vec ste ocenili ovu kompaniju!Vratite se na studentsku stranicu <a href='student.php'>OVDE</a></div>";
        else{
        $upit1="INSERT INTO ocene(student,kompanija,ocena) VALUES('".$_SESSION['kime']."','".$_POST['korime']."','".$_POST['ocena']."')";
       $rezultat1=mysqli_query($konekcija,$upit1)or die("Greska u upitu");
       echo "<div id='regstudent1'>Uspesno ste ocenili kompaniju! Vratite se na studentsku stranicu <a href='student.php'>OVDE</a></div>";
    }}
    ?>
 </body>
</html>
<?php include('footer.php');?>