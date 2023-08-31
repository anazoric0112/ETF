<?php include('header.php');
include('meniA.php');
include_once('db/database.inc.php');
?>
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
      $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
      $upit="SELECT * FROM sajam WHERE datumdo>='".$datumdanas."'";
      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu0");
      if(mysqli_num_rows($rezultat)==0)
          echo "<div id='regstudent1'>Ne postoji aktivan sajam. Vratite se na stranicu administratora <a href='admin.php'>OVDE</a></div>";
      else{
          $niz=  mysqli_fetch_array($rezultat);
          $_SESSION['sajam']=$niz['id'];
          $upit1= "SELECT * FROM prijavajf WHERE sajam='".$niz['id']."'";
          $rezultat1=mysqli_query($konekcija, $upit1) or die("Greska u upitu1");
          if(mysqli_num_rows($rezultat1)==0)
              echo "<div id='regstudent1'>Ne postoji ni jedna prijava za sajam!>/div>";
         
              else{
                  if(!array_key_exists('prihvati', $_POST)&&!array_key_exists('dodeli', $_POST)&&!array_key_exists('dodelip2', $_POST)&&!array_key_exists('dodeliR', $_POST)&&!array_key_exists('pogledaj', $_POST)&&!array_key_exists('odbi', $_POST))
                {
                  $_SESSION['brpredavanja']='a';
                  
                      echo "<table border='1' width='100%'>";
              echo "<tr>";   
              echo "<td>Kompanija</td>";
              echo "<td>Status</td>";
              echo "<td>Pogledaj ponudu</td>";
              echo "<td>Prihvati</td>";
              echo "<td>Odbi</td>";
              echo "</tr>";
              while($niz1=mysqli_fetch_array($rezultat1))
              {
                  $upit2="SELECT * FROM kompanije WHERE korime='".$niz1['kompanija']."'";
                  $rezultat2=mysqli_query($konekcija, $upit2) or die("Greska u upitu2");
                  $niz2=mysqli_fetch_array($rezultat2);
                  echo "<tr>";
                  echo "<form name='Formaprijavajf' method='POST' action='".$_SERVER['PHP_SELF']."'>";
                  echo "<td><input type='text' readonly name='nazivK' value='".$niz2['naziv']."'></td>";
                  echo "<td><input type='text' name='dodeljena' value='".$niz1['odobreno']."'></td>";
                  if($niz1['odobreno']==0)
                  {echo "<td><input type='submit' name='pogledaj' value='POGLEDAJ'></td>";
                  echo "<td><input type='submit' name='prihvati' value='PRIHVATI'></td>";
                  echo "<td><input type='submit' name='odbi' value='ODBIJ'></td>";}
                  if($niz1['odobreno']==1){
                      echo "<td colspan='3'><center>Odobrena prijava</center></td>";
                  }
                  if($niz1['odobreno']==-1){
                      echo "<td colspan='3'><center>Odbijena prijava</center></td>";
                  }
                  echo "<td><input type='hidden' name='korime' value='".$niz1['kompanija']."'></td>";
                  echo "</tr>";
                  echo "</form>";
              }
              
              }
              if(array_key_exists('pogledaj', $_POST)){
                 echo "<br>";
                 $upit3="SELECT * FROM kompanijahocepaket WHERE kompanija='".$_POST['korime']."'";
                 $rezultat3=mysqli_query($konekcija, $upit3) or die("Greska u upitu3");
                 $niz3=mysqli_fetch_array($rezultat3);
                 $upit31="SELECT * FROM paketi WHERE id='".$niz3['paket']."'";
                 $rezultat31=mysqli_query($konekcija, $upit31) or die("Greska u upitu31");
                 $niz31=mysqli_fetch_array($rezultat31);
                 echo "<font size='5' color='orange'>Ova kompanija zeli ";
                 echo $niz31['naziv']." koji sadrzi:<br></font><font size='4' color='orange' >";
                    echo "   -Stand ".$niz31['velicinastanda']." velicine<br>";
                    echo "   -".$niz31['logoBrosure']."<br>";
                    echo "   -Logo na promo majicama ".$niz31['velicinalogoa']." velicine<br>";
                    echo "   -Video promocija od ".$niz31['minpromocije']." minuta<br>";
                    echo "   -".$niz31['brpredavanja']." predavanja<br>";
                    echo "   -".$niz31['brradionica']." radionica<br>";
                    echo "Cena: ".$niz31['cena']."<br></font>";
                 $upit4="SELECT * FROM kompanijahocedodatneusluge WHERE kompanija='".$_POST['korime']."'";
                 $rezultat4=mysqli_query($konekcija, $upit4) or die("Greska u upitu");
                 if(mysqli_num_rows($rezultat4)!=0)
                 {  echo "<font size='5' color='orange'>Kao i dodatne usluge:<br></font><font size='4' color='orange' >";
                    while($niz4=mysqli_fetch_array($rezultat4))
                 {$upit41="SELECT * FROM dodatneusluge WHERE id='".$niz4['usluga']."'";
                 $rezultat41=mysqli_query($konekcija, $upit41) or die("Greska u upitu");
                 $niz41=mysqli_fetch_array($rezultat41);
                 echo "   - ".$niz41['naziv']."<br>";
                  echo "   -Cena: ".$niz41['cena']."<br>";
                 }
                     echo "</font>";
                 }
                 echo "<a href='pogledajprijavejf.php'>Vrati se</a>";
              }
              if(array_key_exists('prihvati', $_POST)||array_key_exists('dodeli', $_POST)||array_key_exists('dodeliR', $_POST)||array_key_exists('dodelip2', $_POST))
                {
                 $upit3="SELECT * FROM kompanijahocepaket WHERE kompanija='".$_POST['korime']."'";
                 $rezultat3=mysqli_query($konekcija, $upit3) or die("Greska u upitu3");
                 $niz3=mysqli_fetch_array($rezultat3);
                 $upit31="SELECT * FROM paketi WHERE id='".$niz3['paket']."'";
                 $paket=$niz3['paket'];
                 $rezultat31=mysqli_query($konekcija, $upit31) or die("Greska u upitu31");
                 $niz31=mysqli_fetch_array($rezultat31);
                
                
                   
                $_SESSION['brpredavanja']=$niz31['brpredavanja'];
                
                 $upit32="SELECT * FROM predavanja WHERE dodeljena='0' AND sajam='".$_SESSION['sajam']."'";
                 $rezultat32=mysqli_query($konekcija, $upit32) or die("Greska u upitu32");
                 echo "<table><tr><td width='25%' align='center'></td><td width='50%'>";
                 echo "PREDAVANJA(potreban broj ".$_SESSION['brpredavanja'].")<br>";
                 if(mysqli_num_rows($rezultat32)!=0)
                 {
                 echo "<table>";
                 echo "<tr>";
                 echo "<td>Vreme pocetka</td>";
                 echo "<td>Vreme kraja</td>";
                 echo "<td>Prostorija</td>";
                 echo "<td>Datum</td>";
                 echo "<td>Dodeli</td>";
                 echo "</tr>";
                 while($niz32=mysqli_fetch_array($rezultat32)){
                 echo "<tr>";
                 echo "<form name='Formapredavanja' action='".$_SERVER['PHP_SELF']."' method='POST'>";
                 echo "<td><input type='text' readonly name='vremeod' value='".$niz32['vremepocetka']."'></td>"; 
                 echo "<td><input type='text' readonly name='vremedo' value='".$niz32['vremekraja']."'></td>"; 
                 echo "<td><input type='text' readonly name='prostorija' value='".$niz32['prostorija']."'></td>"; 
                 echo "<td><input type='text' readonly name='datum' value='".$niz32['datum']."'></td>"; 
                 echo "<td><input type='submit' readonly name='dodeli' value='DODELI'></td>"; 
                 echo "<td><input type='hidden' readonly name='id' value='".$niz32['id']."'></td>"; 
                 echo "<td><input type='hidden' readonly name='korime' value='".$_POST['korime']."'></td>";
                 echo "</form>";
                 echo "</tr>";
                 }
                echo "</table>"; 
              }
              else {echo "<font size='4' color='orange'>Ne postoji slobodan slot za predavanje! Ako zelite da ubacite predavanje morate redefinisati satnicu <a href='redefinisisatnicu.php'>OVDE</a><br></font>";}
              $upit3="SELECT * FROM kompanijahocepaket WHERE kompanija='".$_POST['korime']."'";
                 $rezultat3=mysqli_query($konekcija, $upit3) or die("Greska u upitu3");
                 $niz3=mysqli_fetch_array($rezultat3);
                 $upit31="SELECT * FROM paketi WHERE id='".$niz3['paket']."'";
                 $rezultat31=mysqli_query($konekcija, $upit31) or die("Greska u upitu31");
                 $niz31=mysqli_fetch_array($rezultat31);
                 //echo "Potrebno predavanja: ".$niz31['brradionica'];
                 $_SESSION['brradionica']=$niz31['brradionica'];
                 $upit32="SELECT * FROM radionice WHERE dodeljena='0' AND sajam='".$_SESSION['sajam']."'";
                 $rezultat32=mysqli_query($konekcija, $upit32) or die("Greska u upitu32");
                 echo "RADIONICE(potreban broj ".$_SESSION['brradionica'].")<br>";
                 if(mysqli_num_rows($rezultat32)!=0)
                 {
                 echo "<table>";
                 echo "<tr>";
                 echo "<td>Vreme pocetka</td>";
                 echo "<td>Vreme kraja</td>";
                 echo "<td>Prostorija</td>";
                 echo "<td>Datum</td>";
                 echo "<td>Dodeli</td>";
                 echo "</tr>";
                 while($niz32=mysqli_fetch_array($rezultat32)){
                 echo "<tr>";
                 echo "<form name='Formaradionice' action='".$_SERVER['PHP_SELF']."' method='POST'>";
                 echo "<td><input type='text' readonly name='vremeod' value='".$niz32['vremepocetka']."'></td>"; 
                 echo "<td><input type='text' readonly name='vremedo' value='".$niz32['vremekraja']."'></td>"; 
                 echo "<td><input type='text' readonly name='prostorija' value='".$niz32['prostorija']."'></td>"; 
                 echo "<td><input type='text' readonly name='datum' value='".$niz32['datum']."'></td>"; 
                 echo "<td><input type='submit' readonly name='dodeliR' value='DODELI'></td>"; 
                 echo "<td><input type='hidden' readonly name='id' value='".$niz32['id']."'></td>"; 
                 echo "<td><input type='hidden' readonly name='korime' value='".$_POST['korime']."'></td>";
                 echo "</form>";
                 echo "</tr>";
                 }
                echo "</table>"; 
              }
              else {echo "<font size='4' color='orange'>Ne postoji slobodan slot za radionicu! Ako zelite da ubacite radionicu morate redefinisati satnicu <a href='redefinisisatnicu.php'>OVDE</a><br></font>";}
              $upit3="SELECT * FROM kompanijahocedodatneusluge WHERE kompanija='".$_POST['korime']."'";
                 $rezultat3=mysqli_query($konekcija, $upit3) or die("Greska u upitu3");
                 $_SESSION['brprezentacija']=0;
                 while($niz3=mysqli_fetch_array($rezultat3))
                 { $upit31="SELECT * FROM dodatneusluge WHERE id='".$niz3['usluga']."'";
                       $rezultat31=mysqli_query($konekcija, $upit31) or die("Greska u upitu31");
                         $niz31=mysqli_fetch_array($rezultat31);
                       if($niz31['naziv']=='Prezentacija') $_SESSION['brprezentacija']=1;
                 }
                 $upit32="SELECT * FROM prezentacije WHERE dodeljena='0' AND sajam='".$_SESSION['sajam']."'";
                 $rezultat32=mysqli_query($konekcija, $upit32) or die("Greska u upitu32");
                 echo "PREZENTACIJE(potreban broj ".$_SESSION['brprezentacija'].")<br>";
                 if(mysqli_num_rows($rezultat32)!=0)
                 {
                 echo "<table>";
                 echo "<tr>";
                 echo "<td>Vreme pocetka</td>";
                 echo "<td>Vreme kraja</td>";
                 echo "<td>Prostorija</td>";
                 echo "<td>Datum</td>";
                 echo "<td>Dodeli</td>";
                 echo "</tr>";
                 while($niz32=mysqli_fetch_array($rezultat32)){
                 echo "<tr>";
                 echo "<form name='Formaprezentacije' action='".$_SERVER['PHP_SELF']."' method='POST'>";
                 echo "<td><input type='text' readonly name='vremeod' value='".$niz32['vremepocetka']."'></td>"; 
                 echo "<td><input type='text' readonly name='vremedo' value='".$niz32['vremekraja']."'></td>"; 
                 echo "<td><input type='text' readonly name='prostorija' value='".$niz32['prostorija']."'></td>"; 
                 echo "<td><input type='text' readonly name='datum' value='".$niz32['datum']."'></td>"; 
                 echo "<td><input type='submit' readonly name='dodelip2' value='DODELI'></td>"; 
                 echo "<td><input type='hidden' readonly name='id' value='".$niz32['id']."'></td>"; 
                 echo "<td><input type='hidden' readonly name='korime' value='".$_POST['korime']."'></td>";
                 echo "</form>";
                 echo "</tr>";
                 }
                echo "</table>"; 
                
               
                echo "</td><td width='25%'></td></tr></table>";
                 echo "<center><font color='orange' size='4'>Ukoliko zelite redefinisati satnicu to mozete uciniti <a href='redefinisisatnicu.php'>OVDE</a><br></font>";
                echo "<a href='pogledajprijavejf.php'>Vrati se</a></center>";
              } else {echo "<font size='4' color='orange'>Ne postoji slobodan slot za prezentaciju! Ako zelite da ubacite prezentaciju morate redefinisati satnicu <a href='redefinisisatnicu.php'>OVDE</a><br></font>";}
              
              if(array_key_exists('dodeli', $_POST)){
                   $upit="SELECT * FROM predavanja WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                   $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                   $i=0;
                   while($niz=  mysqli_fetch_array($rezultat)){$i++;}
                  if($i==$_SESSION['brpredavanja']) echo "<div id='regstudent1'>Nije potrebno dodeljivanja vise slotova za predavanja</div>"; 
                  else 
                  {
                      $upit6="UPDATE predavanja SET dodeljena=1, kompanija='".$_POST['korime']."'WHERE id='".$_POST['id']."'";
                      $rezultat6=mysqli_query($konekcija, $upit6) or die("Greska u upitu6");
                      echo "<div id='regstudent1'>Uspesno ste zauzeli slot za predavanje!</div>";
                      $upit="SELECT * FROM predavanja WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                      $i=0;
                      while($niz=  mysqli_fetch_array($rezultat)){$i++;}
                      $upit="SELECT * FROM prezentacije WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                      $j=0;
                      while($niz=  mysqli_fetch_array($rezultat)){$j++;}
                      $upit="SELECT * FROM radionice WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                      $l=0;
                      while($niz=  mysqli_fetch_array($rezultat)){$l++;}
                      if($_SESSION['brradionica']==$l&&$_SESSION['brpredavanja']==$i&&$_SESSION['brprezentacija']==$j)
                      {
                          $upit61="UPDATE prijavajf SET odobreno=1 WHERE kompanija='".$_POST['korime']."'AND sajam='".$_SESSION['sajam']."'";
                          $rezultat61=mysqli_query($konekcija, $upit61) or die("Greska u upitu6");
                          $upit62="UPDATE paketi SET brkupovanja=brkupovanja+1 WHERE id='".$paket."'";
                           $rezultat62=mysqli_query($konekcija, $upit62) or die("Greska u upitu62");
                          echo "<div id='regstudent1'>Uspesno ste prihvatili prijavu!</div>";
                      }
                  }
              }
              if(array_key_exists('dodeliR', $_POST)){
                  $upit="SELECT * FROM radionice WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                   $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                   $i=0;
                   while($niz=  mysqli_fetch_array($rezultat)){$i++;}
                  if($_SESSION['brradionica']==$i) echo "<div id='regstudent1'>Nije potrebno dodeljivanja vise slotova za radionicu</div>";
                  else 
                  {
                      $upit6="UPDATE radionice SET dodeljena=1, kompanija='".$_POST['korime']."'WHERE id='".$_POST['id']."'";
                      $rezultat6=mysqli_query($konekcija, $upit6) or die("Greska u upitu6");
                      echo "<div id='regstudent1'>Uspesno ste zauzeli slot za radionicu!</div>";
                      
                     $upit="SELECT * FROM predavanja WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                      $i=0;
                      while($niz=  mysqli_fetch_array($rezultat)){$i++;}
                      $upit="SELECT * FROM prezentacije WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                      $j=0;
                      while($niz=  mysqli_fetch_array($rezultat)){$j++;}
                      $upit="SELECT * FROM radionice WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                      $l=0;
                      while($niz=  mysqli_fetch_array($rezultat)){$l++;}
                      if($_SESSION['brradionica']==$l&&$_SESSION['brpredavanja']==$i&&$_SESSION['brprezentacija']==$j)
                      {
                          $upit61="UPDATE prijavajf SET odobreno=1 WHERE kompanija='".$_POST['korime']."'AND sajam='".$_SESSION['sajam']."'";
                          $rezultat61=mysqli_query($konekcija, $upit61) or die("Greska u upitu6");
                          $upit62="UPDATE paketi SET brkupovanja=brkupovanja+1 WHERE id='".$paket."'";
                           $rezultat62=mysqli_query($konekcija, $upit62) or die("Greska u upitu62");
                          echo "<div id='regstudent1'>Uspesno ste prihvatili prijavu!</div>";
                      }
                  }
              }
                if(array_key_exists('dodelip2', $_POST)){
                  $upit="SELECT * FROM prezentacije WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                   $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu1");
                   $i=0;
                   while($niz=  mysqli_fetch_array($rezultat)){$i++;}
                  if($_SESSION['brprezentacija']==$i) echo "<div id='regstudent1'>Nije potrebno dodeljivanja vise slotova za prezentacije</div>";
                  else 
                  {
                      $upit6="UPDATE prezentacije SET dodeljena=1, kompanija='".$_POST['korime']."'WHERE id='".$_POST['id']."'";
                      $rezultat6=mysqli_query($konekcija, $upit6) or die("Greska u upitu6");
                      echo "<div id='regstudent1'>Uspesno ste zauzeli slot za prezentaciju!</div>";
                      
                      $upit="SELECT * FROM predavanja WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                      $i=0;
                      while($niz=  mysqli_fetch_array($rezultat)){$i++;}
                      $upit="SELECT * FROM prezentacije WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                      $j=0;
                      while($niz=  mysqli_fetch_array($rezultat)){$j++;}
                      $upit="SELECT * FROM radionice WHERE dodeljena='1'AND kompanija='".$_POST['korime']."'";
                      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
                      $l=0;
                      while($niz=  mysqli_fetch_array($rezultat)){$l++;}
                      if($_SESSION['brradionica']==$l&&$_SESSION['brpredavanja']==$i&&$_SESSION['brprezentacija']==$j)
                      {
                          $upit61="UPDATE prijavajf SET odobreno=1 WHERE kompanija='".$_POST['korime']."'AND sajam='".$_SESSION['sajam']."'";
                          $rezultat61=mysqli_query($konekcija, $upit61) or die("Greska u upitu6");
                          $upit62="UPDATE paketi SET brkupovanja=brkupovanja+1 WHERE id='".$paket."'";
                           $rezultat62=mysqli_query($konekcija, $upit62) or die("Greska u upitu62");
                        //  header("Location: pogledajprijavejf.php");
                          echo "<div id='regstudent1'>Uspesno ste prihvatili prijavu!</div>";
                      }
                  }
              }
              }
              if(array_key_exists('odbi', $_POST)){
                  $upit61="UPDATE prijavajf SET odobreno=-1 WHERE kompanija='".$_POST['korime']."'AND sajam='".$_SESSION['sajam']."'";
                          $rezultat61=mysqli_query($konekcija, $upit61) or die("Greska u upitu6");
                          header("Location: pogledajprijavejf.php");
                          echo "<div id='regstudent1'>Uspesno ste odbili prijavu!</div>";
              }
              }
      }
      ?>
</body>
</html>
<?php include('footer.php');?>