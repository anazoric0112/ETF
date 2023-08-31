<?php include('header.php');
include('meniS.php');?>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="stil.css">
    </head>
    <body>
<?php

session_start();
if($_SESSION['tip']!=1)

   header("Location: index.php");
if(array_key_exists("prijavi", $_POST))
{
	include_once('db/database.inc.php');
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
    $upit2="SELECT * FROM biografije WHERE korime='".$_SESSION['kime']."'";
    $rezultat2=  mysqli_query($konekcija, $upit2)or die("Greska u upitu!");
    if(mysqli_num_rows($rezultat2)==0)
        echo '<div id="regstudent1">Morate uneti biografiju da biste se prijavili na konkurs! To mozete uciniti <a href="CV.php">OVDE</a></div>';
    else{
    $upit1="SELECT * FROM prijavakonkurs WHERE student='".$_SESSION['kime']."'AND konkurs='".$_POST['id']."'";
    $rezultat1=  mysqli_query($konekcija, $upit1)or die("Greska u upitu!");
    if(mysqli_num_rows($rezultat1)!=0)
        echo "<div id='regstudent1'>Vec ste se prijavili na ovaj konkurs! <br><a href='pretraziKompanije.php'>PRETRAZI KOMPANIJE</a><br><a href='pretraziKonkurse.php'>PRETRAZI KONKURSE</a></div>";
    else{
    $upit="INSERT INTO prijavakonkurs(student,konkurs,prihvacen,cover) VALUES('".$_SESSION['kime']."','".$_POST['id']."','','".$_POST['cover']."')" or die("Greska u upitu!");
    $rezultat=  mysqli_query($konekcija, $upit)or die("Greska u upitu!");
    echo '<div id="regstudent1">Uspesno ste se prijavili na konkurs! Vratite se na studentsku stranicu <a href="student.php">OVDE</a></div>';
    
    }
}
}
?>
   

    </body>
</html>
<?php include('footer.php');?>