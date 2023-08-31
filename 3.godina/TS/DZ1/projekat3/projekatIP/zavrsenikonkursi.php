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
$datumdanas=date("Y-m-d");
    $vremesad=date("h:i");
$konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
$upit="SELECT * FROM konkursi WHERE korime='".$_SESSION['kime']."'AND (datum<'".$datumdanas."'OR(datum='".$datumdanas."'AND vreme<'".$vremesad."')) ";
  $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu");
  if(mysqli_num_rows($rezultat)!=0)
           {echo "<table>";
            echo "<tr>";
            echo "<td width='40%'></td>";
            echo "<td width='40%'>";
               echo "<table>";
               echo "<tr>";
               echo "<td>Naziv</td>";
               echo "<td>Tekst konkursa</td>";
               
               echo "<td>Pogledaj prijave</td>";
               echo "</tr>";
            while($niz=  mysqli_fetch_array($rezultat))
                   
               { $naziv=$niz['naziv'];
               $tekst=$niz['tekstK'];   
               $id=$niz['id'];
               echo "<tr>";
               echo '<form method="POST" action="pogledajprijave.php" name="formaZavrseniKonkursi">';
                
                 
                 echo '<td><input type="text" name="naziv" value="'.$naziv.'" readonly></td>';
                 echo '<td><input type="text" name="tekst" value="'.$tekst.'" readonly></td>';
                
                 echo '<td><input type="submit" name="pogledaj" value="POGLEDAJ PRIJAVE"></td>';
                  echo "<td><input type='hidden' name='id' value='".$id."'</td>";
                 echo '</form>';
                 
                 echo "</tr>";
               }
               echo "</table>";
               echo "</td>";
               echo "<td width='20%'></td>";
               echo "</tr>";
               echo "</table>";
           }?>
   </body> </html>
<?php include('footer.php');?>