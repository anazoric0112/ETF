<?php
include('header.php');
include('meniK.php');
include_once('db/database.inc.php');
?>
<html>
      <head>
    <link rel="stylesheet" type="text/css" href="stil.css">
    <script>
    function mojafja()
    {
        var n=0;
        if(document.formaKonkurs.naziv.value==''){alert('Polje naziv ne sme biti prazno!'); n++;}
        if(!document.getElementById("posao").checked&&!document.getElementById("praksa").checked)
        {n++; alert("Konkurs mora biti bilo za posao bilo za praksu");}
        if(document.formaKonkurs.datum.value==''){alert('Polje datum ne sme biti prazno!'); n++;}
        if(document.formaKonkurs.vreme.value==''){alert('Polje vreme ne sme biti prazno!'); n++;}
           if(n==0)
             document.forms["formaKonkurs"].submit()
    }
    </script>
    </head>
    <body>
        <table><tr><td width="50%"></td>
                <td width="25%">
        <form name="formaKonkurs" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
            Naziv:<input type="text" name="naziv"><br>
            Posao ili Praksa:<input type="checkbox" id="posao" name="posao">Posao
            <input type="checkbox" id="praksa" name="praksa">Praksa<br>
            Tekst:<textarea name="tekstK" rows="2" cols="25"></textarea><br>
            Rok za prijavu:<br><input type="date" name="datum">
            <input type="time" name="vreme">
            <center> <input type="button" name="potvrdi" value="POTVRDI" onClick="mojafja()"></center>
            </td>
            <td width="25%"></td></tr></table>
            
<?php

session_start();
if($_SESSION['tip']!=2)

   header("Location: index.php");
$posao=0;
$praksa=0;
 if(array_key_exists("naziv", $_POST))
    { if(isset($_POST['posao'])) $posao=1;
    if(isset($_POST['praksa'])) $praksa=1;
     $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
     $upit="INSERT INTO konkursi(korime,naziv,tekstK,datum,vreme,posao,praksa)VALUES('".$_SESSION['kime']."','".$_POST['naziv']."','".$_POST['tekstK']."','".$_POST['datum']."','".$_POST['vreme']."','".$posao."','".$praksa."')";
    $rezultat=mysqli_query($konekcija, $upit)or die("Greska u upitu");
    echo '<div id="regstudent1">Uspesno ste napravili konkurs. Vratite se na stranicu vase kompanije <a href="kompanija.php">OVDE</a></div>';
     
    }?></body></html>
    <?php include('footer.php');?>