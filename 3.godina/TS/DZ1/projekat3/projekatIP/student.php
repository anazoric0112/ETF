<?php
include('header.php');
include('meniS.php');
include_once('db/database.inc.php');
?>
<html>
    <head>
    <link rel="stylesheet" type="text/css" href="stil.css">
    </head>
<?php session_start();


if($_SESSION['tip']!=1)

   header("Location: index.php");
   
$konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
    $upit="SELECT * FROM student WHERE korime='".$_SESSION['kime']."'";
    $rezultat=  mysqli_query($konekcija, $upit)or die("Greska u upitu!");
    $niz=  mysqli_fetch_array($rezultat);
echo "<div id='dobrodosao'>Dobrodosao ".$niz['ime']." ".$niz['prezime']."</div>";
   
  

?></html>
<?php include('footer.php');?>