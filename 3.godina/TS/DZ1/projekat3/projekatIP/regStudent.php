<?php 
include('header1.php');
include_once('db/database.inc.php');
?>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="stil.css">
    </head>
    <body>
<?php

if(array_key_exists('kime', $_POST))
{
    $kime=$_POST['kime'];
    $sifra1=$_POST['sifra'];
    $sifra=md5($sifra1);
    if($_POST['diploma']=="da")
        $diploma=1;
    else
        $diploma=0;
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die('Neuspela konekcija');
   $upit= "SELECT * FROM korisnici WHERE korisnickoime='".$kime."'";
   $rezultat=mysqli_query($konekcija,$upit) or die('Greska u upitu');
    if(mysqli_num_rows($rezultat)>0)
    {echo "<div id='regstudent1'>Postoji ovo korisnicko ime u sistemu!<br>";
     echo '<a href="registrujse.php">Vrati se na prethodnu stranicu</a></div>';
    }
   
    else
    {   $upit1="INSERT INTO korisnici(korisnickoime,password, tip) VALUES('".$kime."','".$sifra."',1)";
    $rezultat1=mysqli_query($konekcija,$upit1) or die('Greska u upitu');
        $upit2= "INSERT into student(ime,prezime,telefon,email,godstudija,diplomirao,korime,slika)
            VALUES('".$_POST['ime']."','".$_POST['prezime']."','".$_POST['telefon']."','".$_POST['mejl']."','".$_POST['godstudija']."','".$diploma."','".$_POST['kime']."','".$_POST['slika']."')";
$rezultat2=mysqli_query($konekcija,$upit2) or die('Greska u upitu');
  echo "<div id='regstudent1'>Uspesno ste se registrovali!<br>";
  echo 'Sada se &nbsp;<a href="index.php">ulogujte</a>&nbsp; i pocnite sa radom!</div>';
        
    }
}
?>
    </body></html>
<?php include('footer.php');?>