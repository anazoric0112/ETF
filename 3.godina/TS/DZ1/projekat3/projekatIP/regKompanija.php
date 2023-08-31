<?php include('header1.php');?>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="stil.css">
    </head>
    <body>
<?php
include_once('db/database.inc.php');
if(array_key_exists('kime', $_POST))
{
    $kime=$_POST['kime'];
    $sifra1=$_POST['sifra'];
    $sifra=md5($sifra1);
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die('Neuspela konekcija');
   $upit= "SELECT * FROM korisnici WHERE korisnickoime='".$kime."'";
   $rezultat=mysqli_query($konekcija,$upit) or die('Greska u upitu');
    if(mysqli_num_rows($rezultat)>0)
    {echo "<div id='regstudent1'>Postoji ovo korisnicko ime u sistemu!<br>";
     echo '<a href="registrujse.php">Vrati se na prethodnu stranicu</a></div>';
    }
    //sifra!
    else
    {   $upit1="INSERT INTO korisnici(korisnickoime,password, tip) VALUES('".$kime."','".$sifra."',2)";
    $rezultat1=mysqli_query($konekcija,$upit1) or die('Greska u upitu');
        $upit2= "INSERT into kompanije(korime,naziv,grad,adresa,PIB,brzaposlenih,email,sajt,delatnost,specijalnost,logo)
            VALUES('".$_POST['kime']."','".$_POST['naziv']."','".$_POST['grad']."','".$_POST['adresa']."','".$_POST['pib']."','".$_POST['brzaposlenih']."','".$_POST['mejl']."','".$_POST['sajt']."','".$_POST['delatnost']."','".$_POST['specijalnost']."','".$_POST['slika']."')";
$rezultat2=mysqli_query($konekcija,$upit2) or die('Greska u upitu');
      echo "<div id='regstudent1'>Uspesno ste se registrovali!<br>";
      echo 'Sada se&nbsp;<a href="index.php">ulogujte</a>&nbsp; i pocnite sa radom</div>';
    }
}
?>
</body></html>
<?php include('footer.php');?>