<?php
include('header.php');
include('meniK.php');
include_once('db/database.inc.php');
?>
<html>
    <head>
       
        <link rel="stylesheet" type="text/css" href="stil.css">
         
    
<?php
session_start();
if($_SESSION['tip']!=2)

   header("Location: index.php");
$konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
  $upit00="SELECT * FROM prijavajf WHERE kompanija='".$_SESSION['kime']."'";
  $rezultat00=mysqli_query($konekcija,$upit00)or die("Greska u upitu00");
  if(mysqli_num_rows($rezultat00)!=0){ 
        echo '<div id="regstudent1">Vec ste se prijavili na ovaj sajam. Vratite se na stranicu kompanije <a href="kompanija.php">OVDE</a></div>';}
else
{
   $datumdanas=date("Y-m-d");
    $vremesad=date("h:i");
   $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
   $upit0="SELECT * FROM intervaljf WHERE (datumod<'".$datumdanas."' AND datumdo>'".$datumdanas."') OR (datumod='".$datumdanas."' AND vremeod>'".$vremesad."') OR (datumdo='".$datumdanas."' AND vremedo<'".$vremesad."')";
   
   $rezultat0=mysqli_query($konekcija,$upit0)or die("Greska u upitu0");
    if(mysqli_num_rows($rezultat0)==0){ 
        echo '<div id="regstudent1">Morate sacekati vremenski interval koji je zadat od nasih administratora da biste se prijavili na sajam. Vratite se na stranicu kompanije <a href="kompanija.php">OVDE</a></div>';}
else
{
    $upit1="SELECT * FROM sajam WHERE datumdo>='".$datumdanas."'";
    $rezultat1=mysqli_query($konekcija,$upit1)or die("Greska u upitu1");
    if(mysqli_num_rows($rezultat1)==0){ 
    echo '<div id="regstudent1">Ne postoji nijedan otvoren sajam.Vratite se na stranicu Vase kompanije <a href="kompanija.php">OVDE</a></div>';}
        else{
            $niz= mysqli_fetch_array($rezultat1);
            $_SESSION['sajam']=$niz['id'];
            echo "<center><font size='4' color='orange'>Postoji otvoren sajam. Trajace od ".$niz['datumod']." do ".$niz['datumdo']."<br></font></center>";
            
            $upit2= "SELECT * FROM paketi WHERE sajam='".$niz['id']."'AND brkupovanja!=maxkupovanja";
            $rezultat2=mysqli_query($konekcija,$upit2)or die("Greska u upitu2");
            if(mysqli_num_rows($rezultat2)!=0)
            {   echo "<center><font size='4' color='orange'>Moguci paketi:</font></center><br>";echo "<table width='100%'><tr>";
                while($niz2=mysqli_fetch_array($rezultat2)){
                    echo "<td align=center>";
                    echo "Naziv: ".$niz2['naziv']."<br>";
                    echo "   -Stand ".$niz2['velicinastanda']." velicine<br>";
                    echo "   -".$niz2['logoBrosure']."<br>";
                    echo "   -Logo na promo majicama ".$niz2['velicinalogoa']." velicine<br>";
                    echo "   -Video promocija od".$niz2['minpromocije']."<br>";
                    echo "   -".$niz2['brpredavanja']." predavanja<br>";
                    echo "   -".$niz2['brradionica']." radionica<br>";
                    echo "Cena: ".$niz2['cena']."<br><br><br><br>";
                    echo "</td>";
                }
                echo "</tr><table>";
            }
            $upit3= "SELECT * FROM dodatneusluge WHERE sajam='".$niz['id']."'";
            $rezultat3=mysqli_query($konekcija,$upit3)or die("Greska u upitu3");
            if(mysqli_num_rows($rezultat3)!=0)
            {   echo "<center><font size='4' color='orange' align='center'>Moguce dodatne usluge:</font></center><br>";
              echo "<table width='100%'><tr>";
                while($niz3=mysqli_fetch_array($rezultat3))
                { echo "<td align='center'>";
                    echo "Usluga: ".$niz3['naziv']."<br>";
                  echo "Cena: ".$niz3['cena']."<br>";
                   echo "</td>";
                  
                }
                 echo "</tr><table>";
            }
           echo "<center><font size='4' color='orange'>FORMA ZA PRIJAVU:</font><center><br>";
            $upit2= "SELECT * FROM paketi WHERE sajam='".$niz['id']."'AND brkupovanja!=maxkupovanja";
            $rezultat2=mysqli_query($konekcija,$upit2)or die("Greska u upitu2");
            if(mysqli_num_rows($rezultat2)!=0)
                {
                echo "<form name='formakompanija' action='".$_SERVER['PHP_SELF']."' method='POST'>";
                echo "Paket:<br>";
                echo "<select name='paket'>";
                while($niz2=mysqli_fetch_array($rezultat2))
                {echo "<option value='".$niz2['id']."'>'".$niz2['naziv']."'";}       
                echo "</select>";
                echo "<input type='submit' name='potvrdipaket' value='POTVRDI'>";
                echo "</form><br>";
                }
                if(array_key_exists('potvrdipaket', $_POST)){
             $fleg=0;
                $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
             
                $upit40="SELECT * FROM paketi WHERE sajam='".$_SESSION['sajam']."'";
                
                $rezultat40=mysqli_query($konekcija,$upit40)or die("Greska u upitu40");
                while($niz40=mysqli_fetch_array($rezultat40)){
                 $upit4="SELECT * FROM kompanijahocepaket WHERE kompanija='".$_SESSION['kime']."'AND paket='".$niz40['id']."'";
                 
                 $rezultat4=mysqli_query($konekcija,$upit4)or die("Greska u upitu4");
                   if(mysqli_num_rows($rezultat4)!=0)
                {echo "<div id='regstudent1'>Vec ste odabrali paket, ne mozete odabrati dva!</div>";$fleg=1;}}
                   if($fleg==0) {
                    $upit5="INSERT INTO kompanijahocepaket(kompanija,paket)VALUES('".$_SESSION['kime']."','".$_POST['paket']."') ";
                    $rezultat5=mysqli_query($konekcija,$upit5)or die("Greska u upitu4");
                    $upit501="UPDATE paketi SET brkupovanja=brkupovanja+1 WHERE id='".$_POST['paket']."'";
                    $rezultat501=mysqli_query($konekcija,$upit501)or die("Greska u upitu501");
                    echo "<div id='regstudent1'>Uspesno ste prijavili paket!</div>"; 
                    $_SESSION['racun']=0;
             $upit11="SELECT * FROM kompanijahocepaket WHERE kompanija='".$_SESSION['kime']."'";
             $rezultat11=mysqli_query($konekcija,$upit11) or die("Greska u upitu11!");
             while($niz11=mysqli_fetch_array($rezultat11))
             {
                 $upit111="SELECT * FROM paketi WHERE id='".$niz11['paket']."' AND sajam='".$_SESSION['sajam']."'";
                 $rezultat111=mysqli_query($konekcija,$upit111) or die("Greska u upitu111!");
                 $niz111=mysqli_fetch_array($rezultat111);
                 $_SESSION['racun']+=$niz111['cena'];
             }
             $upit12="SELECT * FROM kompanijahocedodatneusluge WHERE kompanija='".$_SESSION['kime']."'";
             $rezultat12=mysqli_query($konekcija,$upit12) or die("Greska u upitu12!");
             while($niz12=mysqli_fetch_array($rezultat12))
             {
                 $upit121="SELECT * FROM dodatneusluge WHERE id='".$niz12['usluga']."' AND sajam='".$_SESSION['sajam']."'";
                 $rezultat121=mysqli_query($konekcija,$upit121) or die("Greska u upitu112!");
                 $niz121=mysqli_fetch_array($rezultat121);
                 $_SESSION['racun']+=$niz121['cena'];
                 // echo $racun;
             }
             }   
         }  
         $upit3= "SELECT * FROM dodatneusluge WHERE sajam='".$niz['id']."'";
          $rezultat3=mysqli_query($konekcija,$upit3)or die("Greska u upitu3");
          if(mysqli_num_rows($rezultat3)!=0){
              
              echo "Dodatne usluge:<br>";
                while($niz3=mysqli_fetch_array($rezultat3))
                {   echo "<form name='formakompanija2' action='".$_SERVER['PHP_SELF']."' method='POST'>";
                    echo "<input type='text' readonly name='usluga' value='".$niz3['naziv']."'>";
                    echo "<input type='hidden' readonly name='id' value='".$niz3['id']."'>";
                    echo "<input type='submit' name='potvrdiuslugu' value='POTVRDI'><br>";
                echo "</form>";
                }
          }
          if(array_key_exists('potvrdiuslugu', $_POST)){
          $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
          
          $upit5="SELECT * FROM kompanijahocedodatneusluge WHERE kompanija='".$_SESSION['kime']."'AND usluga='".$_POST['id']."'";
          $rezultat5=mysqli_query($konekcija,$upit5)or die("Greska u upitu3");
           if(mysqli_num_rows($rezultat5)!=0){echo "<div id='regstudent1'>Vec ste izabrali ovu uslugu!<div>";}
           else{
            $upit6="INSERT INTO kompanijahocedodatneusluge(kompanija,usluga)VALUES('".$_SESSION['kime']."','".$_POST['id']."') ";
              
             $rezultat6=mysqli_query($konekcija,$upit6)or die("Greska u upitu6");
               echo "<div id='regstudent1'>Uspesno ste izabrali dodatnu uslugu</div>";
               
               
               $_SESSION['racun']=0;
               
             $upit11="SELECT * FROM kompanijahocepaket WHERE kompanija='".$_SESSION['kime']."'";
             $rezultat11=mysqli_query($konekcija,$upit11) or die("Greska u upitu11!");
             while($niz11=mysqli_fetch_array($rezultat11))
             {
                 $upit111="SELECT * FROM paketi WHERE id='".$niz11['paket']."'AND sajam='".$_SESSION['sajam']."'";
                 $rezultat111=mysqli_query($konekcija,$upit111) or die("Greska u upitu111!");
                 while($niz111=mysqli_fetch_array($rezultat111))
                 $_SESSION['racun']+=$niz111['cena'];
                  
             }
             $upit12="SELECT * FROM kompanijahocedodatneusluge WHERE kompanija='".$_SESSION['kime']."'";
             $rezultat12=mysqli_query($konekcija,$upit12) or die("Greska u upitu12!");
             while($niz12=mysqli_fetch_array($rezultat12))
             {
                 $upit121="SELECT * FROM dodatneusluge WHERE id='".$niz12['usluga']."'AND sajam='".$_SESSION['sajam']."'";
                 $rezultat121=mysqli_query($konekcija,$upit121) or die("Greska u upitu112!");
                 while($niz121=mysqli_fetch_array($rezultat121))
                 $_SESSION['racun']+=$niz121['cena'];
                 // echo $racun;
             }
              }
          }
        echo "Zavrsi prijavu: ";
        echo "<form name='Formazavrsi' method='POST' action='".$_SERVER['PHP_SELF']."'>";
         echo "<input type='submit' name='zavrsi' value='ZAVRSI' onClick='mojafjaracun()'>";
         echo "</form><br><br><br>";
         if(array_key_exists('zavrsi', $_POST))
         {   $fleg1=0;
             $upit40="SELECT * FROM paketi WHERE sajam='".$_SESSION['sajam']."'";
                
                $rezultat40=mysqli_query($konekcija,$upit40)or die("Greska u upitu40");
                while($niz40=mysqli_fetch_array($rezultat40)){
             
             $upit1001="SELECT * FROM kompanijahocepaket WHERE kompanija='".$_SESSION['kime']."' AND paket='".$niz40['id']."'";
                 $rezultat1001=mysqli_query($konekcija, $upit1001) or die("Greska u upitu");
                 if(mysqli_num_rows($rezultat1001)!=0){$fleg1=1;}
                }
                if($fleg1==1){
                 $upit10="INSERT INTO prijavaJF(kompanija,sajam,odobreno) VALUES('".$_SESSION['kime']."','".$_SESSION['sajam']."','0')";
                 $rezultat10=mysqli_query($konekcija,$upit10) or die("Greska u upitu10!");
                 echo "<div id='regstudent1'>Uspesno ste se prijavili!</div><br><br>";
                 }
                 else echo "<div id='regstudent1'>Morate izabrati bar jedan paket!</div><br><br>";
             
             
         }
           }
        
}

}
?>
         <script language="JavaScript">
            function mojafjaracun(){
                
                var racun=<?php echo $_SESSION['racun'];?>;
                alert("Vas racun je"+racun);
            }
        </script>
    </head>
    </body>
</html>
<?php include('footer.php');?>