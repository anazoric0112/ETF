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
    <center>
        <form  name="formaPretraziKompanije" action="<?php echo $_SERVER['PHP_SELF'];?>" method="POST">
            <input type="text" name="naziv"><br>
            <input type="submit" name="pretrazi" value="PRETRAZI">
            
            
        </form>
    </center>
    </body>
    
</html>
        

<?php
session_start();
if($_SESSION['tip']!=1)

   header("Location: index.php");
if(array_key_exists('pretrazi', $_POST))
 {    
    $konekcija=mysqli_connect("localhost","root","root","jobfair2")or die("Greska u konekciji");
    $upit="SELECT * FROM kompanije WHERE naziv LIKE '".$_POST['naziv']."%'";
    $rezultat=  mysqli_query($konekcija, $upit);
    if(mysqli_num_rows($rezultat)!=0)
{
    echo "<table width='100%' border='1'>";
    echo "<tr>";
    echo "<td>Naziv</td>";
    echo "<td>Grad</td>";
    echo "<td>Adresa</td>";
    echo "<td>Mejl</td>";
    echo "<td>Sajt</td>";
    echo "<td>Delatnost</td>";
    echo "<td>Specijalnost</td>";
    echo "<td>Pregledaj konkurse</td>";
    echo "</tr>";
    while($niz=  mysqli_fetch_array($rezultat)) 
    {
        echo "<form name='Formapregledajprijave' action='".$_SERVER['PHP_SELF']."' method='POST'>";
        echo "<tr>";
       
        echo '<td><input type="text" name="ime" value="'.$niz['naziv'].'" readonly></td>';
        echo '<td><input type="text" name="ime" value="'.$niz['grad'].'" readonly></td>';
        echo '<td><input type="text" name="ime" value="'.$niz['adresa'].'" readonly></td>';
        echo '<td><input type="text" name="ime" value="'.$niz['email'].'" readonly></td>';
        echo '<td><input type="text" name="ime" value="'.$niz['sajt'].'" readonly></td>';
        echo '<td><input type="text" name="ime" value="'.$niz['delatnost'].'" readonly></td>';
        echo '<td><input type="text" name="ime" value="'.$niz['specijalnost'].'" readonly></td>';
        echo '<td><input type="submit" name="pregledaj" value="PREGLEDAJ"</td>';
        echo '<td><input type="hidden" name="korime" value="'.$niz['korime'].'"></td>';
    }
}
else echo "<div id='regstudent1'>Nema kompanija koje ispunjavaju trazene uslove</div>";
 }




 if(array_key_exists("pregledaj", $_POST))
        
{   
    $datumdanas=date("Y-m-d");
    $vremesad=date("h:i");
     $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
     $upit="SELECT * from konkursi WHERE korime='".$_POST['korime']."' AND (datum>'".$datumdanas."'OR(datum='".$datumdanas."'AND vreme>'".$vremesad."'))";
      $rezultat=  mysqli_query($konekcija, $upit);
      if(mysqli_num_rows($rezultat)!=0)
{
          echo "<table width='100%' border='1'>";
               echo "<tr>";
               echo "<td>Naziv</td>";
               echo "<td>Tekst konkursa</td>";
               
               echo "<td>Datum trajanja</td>";
               echo "<td>Vreme trajanja</td>";
               echo "<td>Konkurs za posao:</td>";
               echo "<td>Konkurs za praksu:</td>";
               echo "<td>Ako zelite da se prijavite,ostavite cover letter:</td>";
               echo "<td>Prijavi se</td>";
               echo "</tr>";
               while($niz=  mysqli_fetch_array($rezultat))
               {
                   
               echo '<form method="POST" action="prijavikonkurs.php" name="formaPrijaviKonkurs">';
                 
                 echo "<tr>";
                 echo '<td><input type="text" name="naziv" value="'.$niz['naziv'].'" readonly></td>';
                 echo '<td><input type="text" name="tekst" value="'.$niz['tekstK'].'" readonly></td>';
                 
                 echo '<td><input type="text" name="datum" value="'.$niz['datum'].'" readonly></td>';
                 echo '<td><input type="text" name="vreme" value="'.$niz['vreme'].'" readonly></td>';
                 echo '<td><input type="text" name="posao" value="'.$niz['posao'].'" readonly></td>';
                 echo '<td><input type="text" name="praksa" value="'.$niz['praksa'].'" readonly></td>';
                 echo '<td align=center><textarea name="cover"></textarea></td>';
                 echo '<td><input type="submit" name="prijavi" value="PRIJAVI SE"></td>';
                 echo '<input type="hidden" name="id" value="'.$niz['id'].'"';
                 echo "</tr>";
               }
               echo "</table>";
}
else echo "<div id='regstudent1'>Ova kompanija nema otvorene konkurse</div>";
}



?>
        
<?php include('footer.php');?>