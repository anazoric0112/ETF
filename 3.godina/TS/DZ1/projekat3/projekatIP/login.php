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

if(array_key_exists('potvrdi', $_POST))
{
    $kime=$_POST['user'];
    $sifra1=$_POST['pass'];
    $sifra=md5($sifra1);
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die('Neuspela konekcija');
    $upit= "SELECT * FROM korisnici WHERE korisnickoime='".$kime."' AND password='".$sifra."'";
    $rezultat=mysqli_query($konekcija,$upit) or die('Greska u upitu');
    if(mysqli_num_rows($rezultat)==0)
    {echo "<div id='regstudent1'>Pogresno korisnicko ime ili sifra</div>";}
    else
    {
        echo "Ulogovali ste se";
        session_start();
        $_SESSION['kime']=$kime;
        $_SESSION['sifra']=$sifra;
        $niz=  mysqli_fetch_array($rezultat);
        $_SESSION['tip']=$niz['tip'];
       if($_SESSION['tip']==1)
       { 
       header("Location: student.php");}
        if($_SESSION['tip']==2)
        {
            $upit= "SELECT * FROM kompanije WHERE korime='".$kime."'";
            $rezultat=mysqli_query($konekcija,$upit) or die('Greska u upitu');
            $niz=  mysqli_fetch_array($rezultat);
            $_SESSION['naziv']=$niz['naziv'];
            header("Location: kompanija.php");
        }
        if($_SESSION['tip']==3)
            header("Location: admin.php");
    }
}
else {header("Location: index.php");}
?>
</body></html>
<?php include('footer.php');?>
