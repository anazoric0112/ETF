<?php include('header.php');
include('meniA.php');?>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="stil.css">
    </head>
    <body>
<?php
session_start();
if($_SESSION['tip']!=3)

   header("Location: index.php");
$datumdanas=date("Y-m-d");
include_once('db/database.inc.php');
$konekcija = mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
$upit = "SELECT * FROM sajam WHERE datumdo>='" . $datumdanas . "'";
$rezultat = mysqli_query($konekcija, $upit) or die("Greska u upitu0");
$niz = mysqli_fetch_array($rezultat);
$_SESSION['sajam'] = $niz['id'];
$upit32="SELECT * FROM predavanja WHERE dodeljena='0' AND sajam='".$_SESSION['sajam']."'";
$rezultat32 = mysqli_query($konekcija, $upit32) or die("Greska u upitu32");
echo "<center><font size='4' color='orange'>Slobodni slotovi za predavanja:</font>";
if (mysqli_num_rows($rezultat32) != 0) {
    echo "<table>";
    echo "<tr>";
    echo "<td>Vreme pocetka</td>";
    echo "<td>Vreme kraja</td>";
    echo "<td>Prostorija</td>";
    echo "<td>Datum</td>";
    echo "<td>Dodeli</td>";
    echo "</tr>";
    while ($niz32 = mysqli_fetch_array($rezultat32)) {
        echo "<tr>";
        echo "<form name='Formapredavanja' action='" . $_SERVER['PHP_SELF'] . "' method='POST'>";
        echo "<td><input type='text' readonly name='vremeod' value='" . $niz32['vremepocetka'] . "'></td>";
        echo "<td><input type='text' readonly name='vremedo' value='" . $niz32['vremekraja'] . "'></td>";
        echo "<td><input type='text' readonly name='prostorija' value='" . $niz32['prostorija'] . "'></td>";
        echo "<td><input type='text' readonly name='datum' value='" . $niz32['datum'] . "'></td>";
        echo "<td><input type='submit' readonly name='promenipredavanja' value='PROMENI'></td>";
        echo "<td><input type='hidden' readonly name='id' value='" . $niz32['id'] . "'></td>";
        
        echo "</form>";
        echo "</tr>";
    }
    echo "</table></center>";
}
else echo "<div id='regstudent1'>Nema slobodnih slotova<br></div>";
$upit32="SELECT * FROM prezentacije WHERE dodeljena='0' AND sajam='".$_SESSION['sajam']."'";
$rezultat32 = mysqli_query($konekcija, $upit32) or die("Greska u upitu32");
echo "<center><font size='4' color='orange'>Slobodni slotovi za prezentacije:</font>";
if (mysqli_num_rows($rezultat32) != 0) {
    echo "<table>";
    echo "<tr>";
    echo "<td>Vreme pocetka</td>";
    echo "<td>Vreme kraja</td>";
    echo "<td>Prostorija</td>";
    echo "<td>Datum</td>";
    echo "<td>Dodeli</td>";
    echo "</tr>";
    while ($niz32 = mysqli_fetch_array($rezultat32)) {
        echo "<tr>";
        echo "<form name='Formaprezentacije' action='" . $_SERVER['PHP_SELF'] . "' method='POST'>";
        echo "<td><input type='text' readonly name='vremeod' value='" . $niz32['vremepocetka'] . "'></td>";
        echo "<td><input type='text' readonly name='vremedo' value='" . $niz32['vremekraja'] . "'></td>";
        echo "<td><input type='text' readonly name='prostorija' value='" . $niz32['prostorija'] . "'></td>";
        echo "<td><input type='text' readonly name='datum' value='" . $niz32['datum'] . "'></td>";
        echo "<td><input type='submit' readonly name='promeniprezentacija' value='PROMENI'></td>";
        echo "<td><input type='hidden' readonly name='id' value='" . $niz32['id'] . "'></td>";
        
        echo "</form>";
        echo "</tr>";
    }
    echo "</table></center>";
}
else echo "<div id='regstudent1'>Nema slobodnih slotova<br></div>";
$upit32="SELECT * FROM radionice WHERE dodeljena='0' AND sajam='".$_SESSION['sajam']."'";
$rezultat32 = mysqli_query($konekcija, $upit32) or die("Greska u upitu32");
echo "<center><font size='4' color='orange'>Slobodni slotovi za radionice:</font>";
if (mysqli_num_rows($rezultat32) != 0) {
    echo "<table>";
    echo "<tr>";
    echo "<td>Vreme pocetka</td>";
    echo "<td>Vreme kraja</td>";
    echo "<td>Prostorija</td>";
    echo "<td>Datum</td>";
    echo "<td>Dodeli</td>";
    echo "</tr>";
    while ($niz32 = mysqli_fetch_array($rezultat32)) {
        echo "<tr>";
        echo "<form name='Formapredavanja' action='" . $_SERVER['PHP_SELF'] . "' method='POST'>";
        echo "<td><input type='text' readonly name='vremeod' value='" . $niz32['vremepocetka'] . "'></td>";
        echo "<td><input type='text' readonly name='vremedo' value='" . $niz32['vremekraja'] . "'></td>";
        echo "<td><input type='text' readonly name='prostorija' value='" . $niz32['prostorija'] . "'></td>";
        echo "<td><input type='text' readonly name='datum' value='" . $niz32['datum'] . "'></td>";
        echo "<td><input type='submit' readonly name='promeniradionica' value='PROMENI'></td>";
        echo "<td><input type='hidden' readonly name='id' value='" . $niz32['id'] . "'></td>";
       
        echo "</form>";
        echo "</tr>";
    }
    echo "</table></center>";
}
else echo "<div id='regstudent1'>Nema slobodnih slotova</div><br>";
echo "<center><font size='4' color='orange'>Dodaj novi slot!<br></font>";
?>
        <form name="formaOtvoriJF2" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
            
             
             Datum odrzavanja:
             <input type="date" name="ndatum"><br>
             Vreme pocetka:
             <input type="time" name="nvremepoc"><br>
             Vreme kraja:
             <input type="time" name="nvremekraj"><br>
             Da li je u pitanju predavanje, prezentacija ili radionica?
             <select name="PPR">
                 <option value="predavanje">Predavanje:</option>
                 <option value="prezentacija">Prezentacija:</option>
                 <option value="radionica">Radionica</option>
             </select><br>
             U kojoj prostoriji se odrzava?
             <input type="text" name="nprostorija"><br>
            
             <input type="submit" name="potvrdinovi" value="POTVRDI"><br>
             
             
         </form>
<?php
echo "</center>";
if(array_key_exists('promenipredavanja', $_POST))
{    echo "<center>";
    echo "<form method='POST' name='formappred' action='".$_SERVER['PHP_SELF']."'><br>";
    echo "Novi datum:<input type='date' name='ndatum' value='".$_POST['datum']."'><br>";
    echo "Novo vreme pocetka:<input type='time' name='nvremepoc' value='".$_POST['vremeod']."'><br>";
    echo "Novo vreme kraja:<input type='time' name='nvremekraj' value='".$_POST['vremedo']."'><br>";
    echo "Nova prostorija:<input type='text' name='nprostorija' value='".$_POST['prostorija']."'><br>";
    echo "<input type='submit' name='promenipredsubmit' value='PROMENI'>"; 
    echo "<input type='hidden' name='id' value='" . $_POST['id'] . "'>";
    echo "</form>";
     echo "</center>";
}
if(array_key_exists('promeniradionica', $_POST))
{   echo "<center>";
    echo "<form method='POST' name='formapprez' action='".$_SERVER['PHP_SELF']."'><br>";
    echo "Novi datum:<input type='date' name='ndatum' value='".$_POST['datum']."'><br>";
    echo "Novo vreme pocetka:<input type='time' name='nvremepoc' value='".$_POST['vremeod']."'><br>";
    echo "Novo vreme kraja:<input type='time' name='nvremekraj' value='".$_POST['vremedo']."'><br>";
    echo "Nova prostorija:<input type='text' name='nprostorija' value='".$_POST['prostorija']."'><br>";
    echo "<input type='submit' name='promeniradsubmit' value='PROMENI'>"; 
    echo "<input type='hidden' name='id' value='" . $_POST['id'] . "'>";
    echo "</form>";
     echo "</center>";
}
if(array_key_exists('promeniprezentacija', $_POST))
{    echo "<center>";
    echo "<form method='POST' name='formaprad' action='".$_SERVER['PHP_SELF']."'><br>";
    echo "Novi datum:<input type='date' name='ndatum' value='".$_POST['datum']."'><br>";
    echo "Novo vreme pocetka:<input type='time' name='nvremepoc' value='".$_POST['vremeod']."'><br>";
    echo "Novo vreme kraja:<input type='time' name='nvremekraj' value='".$_POST['vremedo']."'><br>";
    echo "Nova prostorija:<input type='text' name='nprostorija' value='".$_POST['prostorija']."'><br>";
    echo "<input type='submit' name='promeniprezsubmit' value='PROMENI'>"; 
    echo "<input type='hidden' name='id' value='" . $_POST['id'] . "'>";
    echo "</form>";
     echo "</center>";
}

  if(array_key_exists('promenipredsubmit', $_POST)) {
      $upit3="SELECT * FROM radionice WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."')) AND id!='".$_POST['id']."'";
         $rezultat3=mysqli_query($konekcija, $upit3) or die("Greska u upitu3");
         $upit31="SELECT * FROM predavanja WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))AND id!='".$_POST['id']."'";
         $rezultat31=mysqli_query($konekcija, $upit31) or die("Greska u upitu31");
         $upit32="SELECT * FROM prezentacije WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))AND id!='".$_POST['id']."'";
         $rezultat32=mysqli_query($konekcija, $upit32) or die("Greska u upitu32");
         if(mysqli_num_rows($rezultat3)!=0||mysqli_num_rows($rezultat31)!=0||mysqli_num_rows($rezultat32)!=0)
         {echo "<div id='regstudent1'>Postoji preklapanje u vremenskim slotovima<br></div>";
           while($niz3=mysqli_fetch_array($rezultat3))
                {
             echo "<div id='regstudent1'>Vreme radionice sa kojom postoji preklapanje:".$niz3['vremepocetka']." do ".$niz3['vremekraja']."<br></div>";
         }
         while($niz31=mysqli_fetch_array($rezultat31))
                {
             echo "<div id='regstudent1'>Vreme predavanja sa kojim postoji preklapanje:".$niz31['vremepocetka']." do ".$niz31['vremekraja']."<br></div>";
         }
         while($niz32=mysqli_fetch_array($rezultat32))
                {
             echo "<div id='regstudent1'>Vreme prezentacija sa kojom postoji preklapanje:".$niz32['vremepocetka']." do ".$niz32['vremekraja']."<br></div>";
         }
         }
         else {$upit="UPDATE predavanja SET vremepocetka='".$_POST['nvremepoc']."',vremekraja='".$_POST['nvremekraj']."',datum='".$_POST['ndatum']."',prostorija='".$_POST['nprostorija']."' WHERE id='".$_POST['id']."'";
         $rezultat=mysqli_query($konekcija,$upit) or die("Greska u upitu1");
           echo "<div id='regstudent1'>Uspesno ste promenili termin predavanja</div>";
         }
  }   
   if(array_key_exists('promeniprezsubmit', $_POST)) {
      $upit3="SELECT * FROM radionice WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))AND id!='".$_POST['id']."'";
         $rezultat3=mysqli_query($konekcija, $upit3) or die("Greska u upitu3");
         $upit31="SELECT * FROM predavanja WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))AND id!='".$_POST['id']."'";
         $rezultat31=mysqli_query($konekcija, $upit31) or die("Greska u upitu31");
         $upit32="SELECT * FROM prezentacije WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))AND id!='".$_POST['id']."'";
         $rezultat32=mysqli_query($konekcija, $upit32) or die("Greska u upitu32");
         if(mysqli_num_rows($rezultat3)!=0||mysqli_num_rows($rezultat31)!=0||mysqli_num_rows($rezultat32)!=0)
         {echo "<div id='regstudent1'>Postoji preklapanje u vremenskim slotovima<br></div>";
           while($niz3=mysqli_fetch_array($rezultat3))
                {
             echo "<div id='regstudent1'>Vreme radionice sa kojom postoji preklapanje:".$niz3['vremepocetka']." do ".$niz3['vremekraja']."<br></div>";
         }
         while($niz31=mysqli_fetch_array($rezultat31))
                {
             echo "<div id='regstudent1'>Vreme predavanja sa kojim postoji preklapanje:".$niz31['vremepocetka']." do ".$niz31['vremekraja']."<br></div>";
         }
         while($niz32=mysqli_fetch_array($rezultat32))
                {
             echo "<div id='regstudent1'>Vreme prezentacija sa kojom postoji preklapanje:".$niz32['vremepocetka']." do ".$niz32['vremekraja']."<br></div>";
         }
         }
         else {$upit="UPDATE prezentacije SET vremepocetka='".$_POST['nvremepoc']."',vremekraja='".$_POST['nvremekraj']."',datum='".$_POST['ndatum']."',prostorija='".$_POST['nprostorija']."' WHERE id='".$_POST['id']."'";
         $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu1");
           echo "<div id='regstudent1'>Uspesno ste promenili termin prezentacije</div>";
         }
  } 
   if(array_key_exists('promeniradsubmit', $_POST)) {
      $upit3="SELECT * FROM radionice WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))AND id!='".$_POST['id']."'";
         $rezultat3=mysqli_query($konekcija, $upit3) or die("Greska u upitu3");
         $upit31="SELECT * FROM predavanja WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))AND id!='".$_POST['id']."'";
         $rezultat31=mysqli_query($konekcija, $upit31) or die("Greska u upitu31");
         $upit32="SELECT * FROM prezentacije WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))AND id!='".$_POST['id']."'";
         $rezultat32=mysqli_query($konekcija, $upit32) or die("Greska u upitu32");
         if(mysqli_num_rows($rezultat3)!=0||mysqli_num_rows($rezultat31)!=0||mysqli_num_rows($rezultat32)!=0)
         {echo "<div id='regstudent1'>Postoji preklapanje u vremenskim slotovima<br></div>";
           while($niz3=mysqli_fetch_array($rezultat3))
                {
             echo "<div id='regstudent1'>Vreme radionice sa kojom postoji preklapanje:".$niz3['vremepocetka']." do ".$niz3['vremekraja']."<br></div>";
         }
         while($niz31=mysqli_fetch_array($rezultat31))
                {
             echo "<div id='regstudent1'>Vreme predavanja sa kojim postoji preklapanje:".$niz31['vremepocetka']." do ".$niz31['vremekraja']."<br></div>";
         }
         while($niz32=mysqli_fetch_array($rezultat32))
                {
             echo "<div id='regstudent1'>Vreme prezentacija sa kojom postoji preklapanje:".$niz32['vremepocetka']." do ".$niz32['vremekraja']."<br></div>";
         }
         }
         else {$upit="UPDATE radionice SET vremepocetka='".$_POST['nvremepoc']."',vremekraja='".$_POST['nvremekraj']."',datum='".$_POST['ndatum']."',prostorija='".$_POST['nprostorija']."' WHERE id='".$_POST['id']."'";
         $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu1");
         echo "<div id='regstudent1'>Uspesno ste promenili termin radionice</div>";
         }
  } 
  if(array_key_exists('potvrdinovi', $_POST)){
      $upit3="SELECT * FROM radionice WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))";
         $rezultat3=mysqli_query($konekcija, $upit3) or die("Greska u upitu3");
         $upit31="SELECT * FROM predavanja WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))";
         $rezultat31=mysqli_query($konekcija, $upit31) or die("Greska u upitu31");
         $upit32="SELECT * FROM prezentacije WHERE datum='".$_POST['ndatum']."' AND prostorija='".$_POST['nprostorija']."'AND ((vremepocetka<='".$_POST['nvremepoc']."' AND vremekraja>'".$_POST['nvremepoc']."') OR(vremepocetka<'".$_POST['nvremekraj']."' AND vremekraja>='".$_POST['nvremekraj']."')OR(vremepocetka>'".$_POST['nvremepoc']."' AND vremekraja<'".$_POST['nvremekraj']."'))";
         $rezultat32=mysqli_query($konekcija, $upit32) or die("Greska u upitu32");
         if(mysqli_num_rows($rezultat3)!=0||mysqli_num_rows($rezultat31)!=0||mysqli_num_rows($rezultat32)!=0)
         {echo "<div id='regstudent1'>Postoji preklapanje u vremenskim slotovima<br></div>";
           while($niz3=mysqli_fetch_array($rezultat3))
                {
             echo "<div id='regstudent1'>Vreme radionice sa kojom postoji preklapanje:".$niz3['vremepocetka']." do ".$niz3['vremekraja']."<br></div>";
         }
         while($niz31=mysqli_fetch_array($rezultat31))
                {
             echo "<div id='regstudent1'>Vreme predavanja sa kojim postoji preklapanje:".$niz31['vremepocetka']." do ".$niz31['vremekraja']."<br></div>";
         }
         while($niz32=mysqli_fetch_array($rezultat32))
                {
             echo "<div id='regstudent1'>Vreme prezentacija sa kojom postoji preklapanje:".$niz32['vremepocetka']." do ".$niz32['vremekraja']."<br></div>";
         }
         }
         else {
         if($_POST['PPR']=="predavanje"){
             $upit52="INSERT INTO predavanja(datum,vremepocetka,vremekraja,dodeljena,prostorija,sajam,kompanija)
                     VALUES('".$_POST['ndatum']."','".$_POST['nvremepoc']."','".$_POST['nvremekraj']."','0','".$_POST['nprostorija']."','".$_SESSION['sajam']."','')";
              $rezultat52=mysqli_query($konekcija, $upit52) or die("Greska u upitu52");  
             echo "<div id='regstudent1'>Uspesno ste uneli predavanje!</div>";
         }
         if($_POST['PPR']=="prezentacija"){
             $upit52="INSERT INTO prezentacije(datum,vremepocetka,vremekraja,dodeljena,prostorija,sajam,kompanija)
                     VALUES('".$_POST['ndatum']."','".$_POST['nvremepoc']."','".$_POST['nvremekraj']."','0','".$_POST['nprostorija']."','".$_SESSION['sajam']."','')";
              $rezultat52=mysqli_query($konekcija, $upit52) or die("Greska u upitu52");  
             echo "<div id='regstudent1'>Uspesno ste uneli prezentaciju!</div>";
         }
         if($_POST['PPR']=="radionica"){
             $upit52="INSERT INTO radionice(datum,vremepocetka,vremekraja,dodeljena,prostorija,sajam,kompanija)
                     VALUES('".$_POST['ndatum']."','".$_POST['nvremepoc']."','".$_POST['nvremekraj']."','0','".$_POST['nprostorija']."','".$_SESSION['sajam']."','')";
              $rezultat52=mysqli_query($konekcija, $upit52) or die("Greska u upitu52");  
             echo "<div id='regstudent1'>Uspesno ste uneli radionicu!</div>";
         }}
  }
?></body>
</html>
<?php include('footer.php');?>

