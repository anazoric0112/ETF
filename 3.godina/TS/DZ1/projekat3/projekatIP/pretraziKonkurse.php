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
        <table><tr><td width="50%"></td>
                <td width="25%">
        <form name="formaPretraziKonkurse" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
            Naziv:<input type="text" name="naziv"><br>
            Naziv kompanije:<input type="text" name="nazivkompanije"><br>
            Posao ili Praksa:<input type="checkbox" name="posao">Posao
            <input type="checkbox" name="praksa">Praksa
            <center><input type="submit" name="potvrdi" value="POTVRDI"></center>
            </td>
            <td width="25%"></td></tr></table>
        </form>
    </body>
</html>

<?php

session_start();
if($_SESSION['tip']!=1)

   header("Location: index.php");
$posao=0;
$praksa=0;
 if(array_key_exists("potvrdi", $_POST))
         { if(isset($_POST['posao'])) $posao=1;
    if(isset($_POST['praksa'])) $praksa=1;
    if($posao==0&&$praksa==0)
        echo "<div id='regstudent1'>Odaberite da li hocete konkurs za posao, praksu, ili oba!</div>";
       else{
    $datumdanas=date("Y-m-d");
    $vremesad=date("h:i");
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
    
    $upit="SELECT * FROM konkursi WHERE id>(-1) AND (datum>'".$datumdanas."'OR(datum='".$datumdanas."'AND vreme>'".$vremesad."'))";
            if($_POST['naziv']!="")
                $upit.=" AND naziv LIKE '".$_POST['naziv']."%'";
            if($_POST['nazivkompanije']!="")
            {
                $upit1= "SELECT * FROM kompanije WHERE naziv='".$_POST['nazivkompanije']."'";
                $rezultat1=mysqli_query($konekcija,$upit1)or die("Greska u upitu");
                $niz1=  mysqli_fetch_array($rezultat1) ;
                $kime=$niz1['korime'];
            $upit.="AND korime='".$kime."'";}
            if($posao==1&&$praksa==0)
                $upit.="AND posao='1'";
            if($posao==0&&$praksa==1)
                $upit.="AND praksa='1'";
            
           $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu");
           if(mysqli_num_rows($rezultat)!=0)
           {
               echo "<table>";
               echo "<tr>";
               echo "<td>Naziv</td>";
               echo "<td>Tekst konkursa</td>";
               echo "<td>Ime kompanije</td>";
               echo "<td>Datum trajanja</td>";
               echo "<td>Vreme trajanja</td>";
               echo "<td>Ako zelite da se prijavite,ostavite cover letter</td>";
               echo "<td>Prijavi se</td>";
               echo "</tr>";
           
           while($niz=  mysqli_fetch_array($rezultat))
                   
               { $naziv=$niz['naziv'];
                 $tekst=$niz['tekstK'];            
                 $upit2= "SELECT * FROM kompanije WHERE korime='".$niz['korime']."'";
                 $rezultat2=mysqli_query($konekcija,$upit2)or die("Greska u upitu");
                 $niz2=  mysqli_fetch_array($rezultat2);
                 $nazivK=$niz2['naziv'];
                 $datum=$niz['datum'];
                 $vreme=$niz['vreme'];
                 $id=$niz['id'];
                 
                 echo '<form method="POST" action="prijavikonkurs.php" name="formaPrijaviKonkurs">';
                 echo '<input type="hidden" name="id" value="'.$id.'"';
                 echo "<tr>";
                 echo '<td><input type="text" name="naziv" value="'.$naziv.'" readonly></td>';
                 echo '<td><input type="text" name="tekst" value="'.$tekst.'" readonly></td>';
                 echo '<td><input type="text" name="nazivK" value="'.$nazivK.'" readonly></td>';
                 echo '<td><input type="text" name="datum" value="'.$datum.'" readonly></td>';
                 echo '<td><input type="text" name="vreme" value="'.$vreme.'" readonly></td>';
                 echo '<td align="center"><input type="text" name="cover"></td>';
                 echo '<td><input type="submit" name="prijavi" value="PRIJAVI SE"></td>';
                 echo '</form>';
                 echo "</tr>";
               }
               echo "</table>";
         }         
         else echo "<div id='regstudent1'>Nema konkursa koji zadovoljavaju parametre vase pretrage</div>";
         
           }
         }
         ?>
<?php include('footer.php');?>