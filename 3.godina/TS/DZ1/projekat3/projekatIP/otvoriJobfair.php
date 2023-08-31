<?php include('header.php');
include('meniA.php');
include_once('db/database.inc.php');
?>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="stil.css">
        <script language="Javascript">
        function mojafja1(){
            var n=0;
            if(document.formaOtvoriJF1.naziv.value==''){alert('Polje naziv ne sme biti prazno!'); n++;}
            if(document.formaOtvoriJF1.datumO.value==''){alert('Polje datum otvaranja ne sme biti prazno!'); n++;}
            if(document.formaOtvoriJF1.datumZ.value=='') {alert('Polje datum zatvaranja ne sme biti prazno!');n++;}
            if(document.formaOtvoriJF1.datumZ.value<document.formaOtvoriJF1.datumO.value)
            {alert('Datum otvaranja mora biti manji od datuma zatvaranja'); n++;}
        if(n==0){
                 document.forms["formaOtvoriJF1"].submit();
             }
        }
        function mojafja2(){
            var n=0;
            if(document.formaOtvoriJF2.datum.value==''){alert('Polje datum ne sme biti prazno!'); n++;}
            if(document.formaOtvoriJF2.vremeP.value==''){alert('Polje vreme pocetka ne sme biti prazno!'); n++;}
            if(document.formaOtvoriJF2.vremeK.value=='') {alert('Polje vreme kraja ne sme biti prazno!');n++;}
            if(document.formaOtvoriJF2.vremeK.value<document.formaOtvoriJF2.vremeP.value)
            {alert('Vreme pocetka mora biti manje od vremena kraja'); n++;}
            if(document.formaOtvoriJF2.datum.value<document.formaOtvoriJF2.datum1.value||document.formaOtvoriJF2.datum.value>document.formaOtvoriJF2.datum2.value)
            {alert("Polje datum mora biti izmedju vrednosti pocetka i kraja sajma"); n++;}
        if(n==0){
                 document.forms["formaOtvoriJF2"].submit();
             }
        }
        function mojafja3(){
            var n=0;
            if(document.forma.naziv.value==''){alert('Polje naziv ne sme biti prazno!'); n++;}
            if(document.forma.cena.value==''){alert('Polje cena ne sme biti prazno!'); n++;}
            if(document.forma.maxKup.value=='') {alert('Polje maksimalno kupovanja ne sme biti prazno!');n++;}
            if(document.forma.brpredavanja.value==''){alert('Polje broj predavanja ne sme biti prazno!');n++;}
            if(document.forma.logoBrosure.value=='') {alert('Polje logo i brosure ne sme biti prazno!');n++;}
            if(document.forma.brradionica.value=='') {alert('Polje broj radionica ne sme biti prazno!');n++;}
        if(n==0){
                 document.forms["forma"].submit();
             }
        }
         function mojafja4(){
            var n=0;
            if(document.forma1.dodatno.value==''){alert('Polje dodatna usluga ne sme biti prazno!'); n++;}
            if(document.forma1.cena1.value==''){alert('Polje cena ne sme biti prazno!'); n++;}
            
        if(n==0){
                 document.forms["forma1"].submit();
             }
        }
        </script>
    </head>
    <body>

<?php
session_start();
if($_SESSION['tip']!=3)

   header("Location: index.php");
$k=0;
    


if(!array_key_exists('datumO', $_POST)&&!array_key_exists('dalje2', $_POST)&&!array_key_exists('prostorija', $_POST)&&!array_key_exists('maxKup', $_POST)&&!array_key_exists('dodatno', $_POST)&&!array_key_exists('dalje3', $_POST))
{
    ?>
<table><tr><td width="45%"></td>
                <td width="25%" >
        
        <form name="formaOtvoriJF1" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
            Naziv*:<input type="text" name="naziv"><br>
            Datum otvaranja*:<input type="date" name="datumO"><br>
            Datum Zatvaranja*:<input type="date" name="datumZ"><br>
            <center> <input type="button" name="dalje1" value="DALJE" onClick="mojafja1()"><br></center>
            
        </form>
   </td>
    <td width="30%"></td></tr></table>
<?php

}
else{
    if(array_key_exists('datumO', $_POST)&&!array_key_exists('prostorija', $_POST))
            
    {  
        $datumdanas=date("Y-m-d");
      $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
      $upit="SELECT * FROM sajam WHERE datumdo>='".$datumdanas."'";
      $rezultat=mysqli_query($konekcija, $upit) or die("Greska u upitu");
      if(mysqli_num_rows($rezultat)!=0)
    {echo "<div id='regstudent1'>Ne mozete kreirati novi sajam dok nije zavrsen prethodni Vratite se na stranicu administratora <a href='admin.php'>OVDE</a></div>"; $k=1;}
      else {$konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
        $upit2="INSERT INTO sajam(naziv,datumod,datumdo,logo) VALUES('".$_POST['naziv']."','".$_POST['datumO']."','".$_POST['datumZ']."','')";
        $rezultat2=mysqli_query($konekcija, $upit2) or die("Greska u upitu");
        $datumdanas=date("Y-m-d");
      $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
      $upit4="SELECT * FROM sajam WHERE datumdo>='".$datumdanas."'";
      $rezultat4=mysqli_query($konekcija, $upit4) or die("Greska u upitu4");
      $niz=  mysqli_fetch_array($rezultat4);
      $_SESSION['sajam']=$niz['id'];
      $_SESSION['datumo']=$_POST['datumO'];
      $_SESSION['datumz']=$_POST['datumZ'];
      }
    }
    
        
        if((array_key_exists('datumO', $_POST)||array_key_exists('prostorija', $_POST))&&$k!=1&&!array_key_exists('dalje2', $_POST)){
        ?>
         <table><tr><td width="40%"></td>
                <td width="30%">
         <form name="formaOtvoriJF2" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
             Unesi logo sajma:
             <input type="file" name="logo"><br>
             Vremenski slotovi:<br>
             Datum odrzavanja*:
             <input type="date" name="datum"><br>
             Vreme pocetka*:
             <input type="time" name="vremeP"><br>
             Vreme kraja*:
             <input type="time" name="vremeK"><br>
             Da li je u pitanju predavanje, prezentacija ili radionica*?
             <select name="PPR">
                 <option value="predavanje">Predavanje:</option>
                 <option value="prezentacija">Prezentacija:</option>
                 <option value="radionica">Radionica</option>
             </select><br>
             U kojoj prostoriji se odrzava*?
             <input type="text" name="prostorija"><br>
             <input type="hidden" name="datum1" value="<?php echo $_SESSION['datumo'];?>"><br>
             <input type="hidden" name="datum2" value="<?php echo $_SESSION['datumz'];?>"><br>
             <input type="button" name="potvrdi" value="POTVRDI" onClick="mojafja2()"><br>
             
             Ako ste uneli sve slotove za radionice,prezentacije i predavanja nastavite dalje:
             <input type="submit" name="dalje2" value="DALJE"><br>
         </form>
         </td>
    <td width="30%"></td></tr></table>
    <?php
    
     if(array_key_exists('prostorija', $_POST))
     {
         $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
         
         $upit3="SELECT * FROM radionice WHERE datum='".$_POST['datum']."' AND prostorija='".$_POST['prostorija']."'AND ((vremepocetka<='".$_POST['vremeP']."' AND vremekraja>'".$_POST['vremeP']."') OR(vremepocetka<'".$_POST['vremeK']."' AND vremekraja>='".$_POST['vremeK']."')OR(vremepocetka>'".$_POST['vremeP']."' AND vremekraja<'".$_POST['vremeK']."'))";
         $rezultat3=mysqli_query($konekcija, $upit3) or die("Greska u upitu3");
         $upit31="SELECT * FROM predavanja WHERE datum='".$_POST['datum']."' AND prostorija='".$_POST['prostorija']."'AND ((vremepocetka<='".$_POST['vremeP']."' AND vremekraja>'".$_POST['vremeP']."') OR(vremepocetka<'".$_POST['vremeK']."' AND vremekraja>='".$_POST['vremeK']."')OR(vremepocetka>'".$_POST['vremeP']."' AND vremekraja<'".$_POST['vremeK']."'))";
         $rezultat31=mysqli_query($konekcija, $upit31) or die("Greska u upitu31");
         $upit32="SELECT * FROM prezentacije WHERE datum='".$_POST['datum']."' AND prostorija='".$_POST['prostorija']."'AND ((vremepocetka<='".$_POST['vremeP']."' AND vremekraja>'".$_POST['vremeP']."') OR(vremepocetka<'".$_POST['vremeK']."' AND vremekraja>='".$_POST['vremeK']."')OR(vremepocetka>'".$_POST['vremeP']."' AND vremekraja<'".$_POST['vremeK']."'))";
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
             if($_POST['PPR']=='radionica')
             {$upit51="INSERT INTO radionice(datum,vremepocetka,vremekraja,dodeljena,prostorija,sajam,kompanija)
                     VALUES('".$_POST['datum']."','".$_POST['vremeP']."','".$_POST['vremeK']."','0','".$_POST['prostorija']."','".$_SESSION['sajam']."','') ";
            
              $rezultat51=mysqli_query($konekcija, $upit51) or die("Greska u upitu51");  
             echo "<div id='regstudent1'>Uspesno ste uneli radionicu!</div>";}
               if($_POST['PPR']=='predavanje')
             {$upit52="INSERT INTO predavanja(datum,vremepocetka,vremekraja,dodeljena,prostorija,sajam,kompanija)
                     VALUES('".$_POST['datum']."','".$_POST['vremeP']."','".$_POST['vremeK']."','0','".$_POST['prostorija']."','".$_SESSION['sajam']."','') ";
            
              $rezultat52=mysqli_query($konekcija, $upit52) or die("Greska u upitu52");  
             echo "<div id='regstudent1'>Uspesno ste uneli predavanje!</div>";}
             if($_POST['PPR']=='prezentacija')
             {$upit53="INSERT INTO prezentacije(datum,vremepocetka,vremekraja,dodeljena,prostorija,sajam,kompanija)
                     VALUES('".$_POST['datum']."','".$_POST['vremeP']."','".$_POST['vremeK']."','0','".$_POST['prostorija']."','".$_SESSION['sajam']."','') ";
            
              $rezultat53=mysqli_query($konekcija, $upit53) or die("Greska u upitu53");  
             echo "<div id='regstudent1'>Uspesno ste uneli prezentaciju!</div>";}
               $upit6="SELECT * FROM sajam WHERE id='".$_SESSION['sajam']."'AND logo=''";
               $rezultat6=mysqli_query($konekcija, $upit6) or die("Greska u upitu"); 
               if(mysqli_num_rows($rezultat6)!=0){
                   $upit7="UPDATE sajam SET logo='".$_POST['logo']."'WHERE id='".$_SESSION['sajam']."'";
                   $rezultat7=mysqli_query($konekcija, $upit7) or die("Greska u upitu"); 
               }
                   
         }
     }
}
    if(array_key_exists('dalje2', $_POST)||array_key_exists('maxKup', $_POST)||array_key_exists('dodatno', $_POST)){
        echo '<table><tr><td width="40%"></td><td width="30%">';
        echo "<font size='4' color='orange'>Unos paketa:<br></font>";
        echo "<form name='forma' method='POST' action='".$_SERVER['PHP_SELF']."'>";
        echo "Naziv paketa*:<input type='text' name='naziv'><br>";
        echo "Stand";
        echo "<select name='velicinastand'>";
        echo "<option value='cetvorostruke'>cetvorostruke";
        echo "<option value='trostruke'>trostruke";
        echo "<option value='dvostruke'>dvostruke";
        echo "<option value='jednostruke'>jednostruke";
        echo "</select>velicine<br>";
        echo "Logo i brosure*:";
        echo "<input type='text' name='logoBrosure'><br>";
        echo "Logo na promo majicama ";
        echo "<select name='velicinalogo'>";
       
        echo "<option value='trostruke'>trostruke";
        echo "<option value='dvostruke'>dvostruke";
        echo "<option value='standardne'>standardne";
        echo "<option value=''>";
        echo "</select>velicine<br>";
        echo "Video promocija od <input type='number' name='videoprom'> minuta<br>";
        echo "<input type='number' name='brpredavanja'>Predavanja<br>"; 
        echo "<input type='number' name='brradionica'>Radionica<br>"; 
        echo "Cena*: <input type='text' name='cena'><br>";
        echo "Maksimalni broj kupovanja ovog paketa*: <input type='number' name='maxKup'><br>";
        echo "<input type='button' name='potvrdi2' value='POTVRDI' onClick='mojafja3()'><br>";
        
        echo "</form><br>";
        echo '</td><td width="30%"></td></tr></table>';
        echo '<table><tr><td width="40%"></td><td width="35%">';
        echo "<font size='4' color='orange'>Unos dodatnih stavki/usluga<br></font>";
        echo "<form name='forma1' method='POST' action='".$_SERVER['PHP_SELF']."'>";
        echo "Usluga(ako zelite da dodate prezentaciju kao uslugu samo napisite Prezentacija):<br><input type='text' name='dodatno'><br>";
         
        echo "Cena: <input type='text' name='cena1'><br>";
        echo "<input type='button' name='potvrdi3' value='DODAJ' onClick='mojafja4()'><br>";
        
        echo "<input type='submit' name='dalje3' value='ZAVRSI'><br>";
        echo "</form>";
        echo '</td><td width="25%"></td></tr></table>';
        if(array_key_exists('maxKup', $_POST)){
            
               $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
               $upit80="SELECT * FROM paketi WHERE naziv='".$_POST['naziv']."' AND sajam='".$_SESSION['sajam']."'";
               $rezultat80=mysqli_query($konekcija, $upit80) or die("Greska u upitu8");
               if(mysqli_num_rows($rezultat80)!=0){ echo "<div id='regstudent1'>Vec ste uneli paket sa ovim imenom</div>";}
              else{
               $upit8="INSERT INTO paketi(naziv,velicinastanda,logoBrosure,velicinalogoa,minpromocije,brpredavanja,brradionica,cena,maxkupovanja,sajam,brkupovanja)
                   VALUES('".$_POST['naziv']."','".$_POST['velicinastand']."','".$_POST['logoBrosure']."','".$_POST['velicinalogo']."','".$_POST['videoprom']."','".$_POST['brpredavanja']."','".$_POST['brradionica']."','".$_POST['cena']."','".$_POST['maxKup']."','".$_SESSION['sajam']."','0')  ";
            
            $rezultat8=mysqli_query($konekcija, $upit8) or die("Greska u upitu8"); 
        echo "<div id='regstudent1'>Uspesno ste ubacili paket</div>";}}
        if(array_key_exists('dodatno', $_POST)){
            $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
            $upit90="SELECT * FROM dodatneusluge WHERE naziv='".$_POST['dodatno']."'AND sajam='".$_SESSION['sajam']."'";
               $rezultat90=mysqli_query($konekcija, $upit90) or die("Greska u upitu8");
               if(mysqli_num_rows($rezultat90)!=0){ echo "<div id='regstudent1'>Vec ste uneli dodatnu uslugu sa ovim imenom</div>";}
              else{
            if($_POST['dodatno']=='Prezentacija')
            {
            $upit9="INSERT INTO dodatneusluge(naziv,cena,prezentacija,sajam) VALUES('".$_POST['dodatno']."','".$_POST['cena1']."','1','".$_SESSION['sajam']."')";
            
            $rezultat9=mysqli_query($konekcija, $upit9) or die("Greska u upitu9"); }
            else{$konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji!");
            $upit9="INSERT INTO dodatneusluge(naziv,cena,prezentacija,sajam) VALUES('".$_POST['dodatno']."','".$_POST['cena1']."','0','".$_SESSION['sajam']."')";
            
            $rezultat9=mysqli_query($konekcija, $upit9) or die("Greska u upitu9"); }
        echo "<div id='regstudent1'>Uspesno ste ubacili dodatnu uslugu!</div>";}}
      }  if(array_key_exists('dalje3', $_POST)){ echo "<div id='regstudent1'>Uspesno ste zapoceli sajam! Vratite se na administratorsku stranicu klikom <a href='admin.php'>OVDE</a></div>";}
        
    


 }?>
 </body>
</html>
<?php include('footer.php');?>