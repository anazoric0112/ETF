<?php
include('header.php');
include('meniS.php');
include_once('db/database.inc.php');
?>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="stil.css">
        <script language="Javascript">
        function mojafja1(){
            var n=0;
            var uzorak6=/.{1,}@.{1,}/
            if(document.FormaCV.ime.value==''){alert('Polje ime ne sme biti prazno!'); n++;}
            if(document.FormaCV.prezime.value==''){alert('Polje prezime ne sme biti prazno!'); n++;}
            if(document.FormaCV.adresa.value==''){alert('Polje adresa ne sme biti prazno!'); n++;}
            
            if(document.FormaCV.grad.value==''){alert('Polje grad ne sme biti prazno!'); n++;}
            if(document.FormaCV.drzava.value==''){alert('Polje drzava ne sme biti prazno!'); n++;}
            if(document.FormaCV.telefon.value==''){alert('Polje telefon ne sme biti prazno!'); n++;}
            if(document.FormaCV.mejl.value==''){alert('Polje mejl ne sme biti prazno!'); n++;}
             if(!uzorak6.test(document.FormaCV.mejl.value)){alert('Polje mejl nije u pravom formatu!');n++;}
             if(document.FormaCV.mjezik.value==''){alert('Polje maternji jezik ne sme biti prazno!'); n++;}
             if(n==0){
                 document.forms["FormaCV"].submit();
             }
        }
        </script>
    </head>

<?php
session_start();
if($_SESSION['tip']!=1)

   header("Location: index.php");
   $datumdanas=date("Y-m-d");
    $vremesad=date("h:i");
   $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
   $upit0="SELECT * FROM intervalcv WHERE (oddatum<'".$datumdanas."' AND dodatum>'".$datumdanas."') OR (oddatum='".$datumdanas."' AND odvreme>'".$vremesad."') OR (dodatum='".$datumdanas."' AND dovreme<'".$vremesad."')";
   
   $rezultat0=mysqli_query($konekcija,$upit0)or die("Greska u upitu0");
    if(mysqli_num_rows($rezultat0)==0)
        { echo '<div id="regstudent1">Morate sacekati interval koji je zadat od nasih administratora da biste ostavili CV. Vratite se na studentsku stranu <a href="student.php">OVDE</a></div>';}
else{
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
    $upit1="SELECT * FROM biografije WHERE korime='".$_SESSION['kime']."'";
    $rezultat1=mysqli_query($konekcija,$upit1)or die("Greska u upitu1");
    if(mysqli_num_rows($rezultat1)!=0){ echo '<div id="regstudent1">Vec ste uneli biografiju. Mozete promeniti neki od podataka <a href="CVazuriraj.php">OVDE</a><div>';}
    else{
        if(!array_key_exists("izjava", $_POST))
    {
        
    ?>


    <body>
        <table><tr>
                <td width="40%"></td>
                <td width="35%">
        <form name="FormaCV" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
            Licne informacije:<br>

                 &nbsp;&nbsp;&nbsp;Ime*:<input type="text" name="ime"><br>
                 &nbsp;&nbsp;&nbsp;Prezime*:<input type="text" name="prezime"><br>
                 &nbsp;&nbsp;&nbsp;Adresa*:<input type="text" name="adresa"><br>
                 &nbsp;&nbsp;&nbsp;Postanski broj:<input type="text" name="pbroj"><br>
                 &nbsp;&nbsp;&nbsp;Grad*:<input type="text" name="grad"><br>
                 &nbsp;&nbsp;&nbsp;Drzava*:<input type="text" name="drzava"><br>
                 &nbsp;&nbsp;&nbsp;Telefon*:<input type="text" name="telefon"><br>
                 &nbsp;&nbsp;&nbsp;E-mail*:<input type="text" name="mejl"><br>
                 &nbsp;&nbsp;&nbsp;Sajt/blog:<input type="text" name="sajt"><br>
                 &nbsp;&nbsp;&nbsp;Pol:<select name="pol">
                                       <option value="M">Muski
                                       <option value="Z">Zenski
                                       </select><br>
                 &nbsp;&nbsp;&nbsp;Datum rodjenja:<input type="date" name="drodjenja"><br>  
                 &nbsp;&nbsp;&nbsp;Nacionalnost:<input type="text" name="nacionalnost"><br>
                 
    Tip aplikacije:<br>
    &nbsp;&nbsp;&nbsp;Zeljena pozicija:<input type="text" name="zeljpoz"><br>
    &nbsp;&nbsp;&nbsp;Zavrseni fakultet/smer:<input type="text" name="fax">  <br>           
    &nbsp;&nbsp;&nbsp;Licna izjava:<input type="text" name="izjava"><br>
                 Vestine:<br>
                 &nbsp;&nbsp;&nbsp;Maternji jezik*:<input type="text" name="mjezik"><br>
                 &nbsp;&nbsp;&nbsp;Komunikacione vestine:<input type="text" name="kvestine"><br>
                 &nbsp;&nbsp;&nbsp;Organizacione vestine:<input type="text" name="ovestine"><br>
                 &nbsp;&nbsp;&nbsp;Menadzerske vestine:<input type="text" name="mvestine"><br>
                 &nbsp;&nbsp;&nbsp;Druge vestine vezane za posao:<input type="text" name="vestine"></td>
                 
        <td width="25%"></td>
         </tr></table> 
            <center>Da bi ste uneli eventualno radno iskustvo koje posedujute kliknite <a href="RadnoIskustvo.php" target="_blank">OVDE</a>(potreban je poseban unos za svako iskustvo koje unosite)
            <br>
            Da bi ste uneli obrazovanje koje posedujute kliknite <a href="Obrazovanje.php" target="_blank">OVDE</a>(potreban je poseban unos za svaki stepen obrazovanja koji imate)
            <br>
            Da bi ste uneli poznavanje stranih jezika kliknite<a href="Jezici.php" target="_blank">OVDE</a>(potreban je poseban unos za svaki strani jezik)<br>
            
            <input type="button" name="potvrdi" value="POTVRDI" onClick="mojafja1()"></center><br><br><br>
                 
            
        </form>  
    </body>
</html>

<?php

    }if(array_key_exists("izjava", $_POST))
    {


    
        
            if($_POST['pol']=="M")
                $pol="M";
            else $pol="Z";
    
      $upit2="INSERT INTO biografije(korime,ime,prezime,adresa,postanskiBroj,grad,drzava,telefon,mejl,sajt,pol,datumrodj,nacionalnost,zeljpozicija,fakultet,izjava,mjezik,kvestine,ovestine,mvestine,dvestine) 
    VALUES('".$_SESSION['kime']."','".$_POST['ime']."','".$_POST['prezime']."','".$_POST['adresa']."','".$_POST['pbroj']."','".$_POST['grad']."','".$_POST['drzava']."','".$_POST['telefon']."','".$_POST['mejl']."','".$_POST['sajt']."','".$pol."','".$_POST['drodjenja']."','".$_POST['nacionalnost']."','".$_POST['zeljpoz']."','".$_POST['fax']."','".$_POST['izjava']."','".$_POST['mjezik']."','".$_POST['kvestine']."','".$_POST['ovestine']."','".$_POST['mvestine']."','".$_POST['vestine']."')";
   
    $rezultat2=mysqli_query($konekcija,$upit2)or die("Greska u upitu2");
    echo "<div id='regstudent1'>Uspesno ste uneli svoju biografiju<br>";
    echo 'Ukoliko zelite nesto da izmenite u njoj kliknite <a href="CVazuriraj.php" >OVDE</a><br>';
    echo 'Ukoliko zelite da se vratite na studentsku stranicu kliknite <a href="student.php">OVDE</a><div><br><br>';}}

}?>
<?php include('footer.php');?>


