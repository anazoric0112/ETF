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
   $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
   $upit1="SELECT * FROM biografije WHERE korime='".$_SESSION['kime']."'";
   $rezultat1=mysqli_query($konekcija,$upit1)or die("Greska u upitu1");
    if(mysqli_num_rows($rezultat1)==0){ echo '<div id="regstudent1">Niste uneli biografiju. Mozete je uneti <a href="CV.php">OVDE</a></div>';}
    else{ $niz=  mysqli_fetch_array($rezultat1);
if(!array_key_exists("izjava", $_POST))
    {
?>

    <body>
        <table><tr><td width="40%"></td>
                <td>
        <form name="FormaCV" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
            Licne informacije:<br>

            &nbsp;&nbsp;&nbsp;Ime:<input type="text" name="ime" value="<?php echo $niz['ime']; ?>"><br>
                 &nbsp;&nbsp;&nbsp;Prezime:<input type="text" name="prezime" value="<?php echo $niz['prezime'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Adresa:<input type="text" name="adresa" value="<?php echo $niz['adresa'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Postanski broj:<input type="text" name="pbroj" value="<?php echo $niz['postanskiBroj'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Grad:<input type="text" name="grad" value="<?php echo $niz['grad'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Drzava:<input type="text" name="drzava" value="<?php echo $niz['drzava'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Telefon:<input type="text" name="telefon" value="<?php echo $niz['telefon'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;E-mail:<input type="text" name="mejl" value="<?php echo $niz['mejl'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Sajt/blog:<input type="text" name="sajt" value="<?php echo $niz['sajt'] ?>"><br>
                 <?php if($niz['pol']=='Z'){?>
                 &nbsp;&nbsp;&nbsp;Pol:<select name="pol">
                                       <option name="M" value="M">Muski
                                       <option name="Z" value="Z" selected>Zenski
                                       </select><br>
                 <?php }else{ ?>
                  &nbsp;&nbsp;&nbsp;Pol:<select name="pol">
                                       <option name="M" value="M" selected>Muski
                                       <option name="Z" value="Z">Zenski
                                       </select><br>
                 <?php } ?>
                 &nbsp;&nbsp;&nbsp;Datum rodjenja:<input type="date" name="drodjenja" value="<?php echo $niz['datumrodj'] ?>"><br>  
                 &nbsp;&nbsp;&nbsp;Nacionalnost:<input type="text" name="nacionalnost" value="<?php echo $niz['nacionalnost'] ?>"><br>
            Tip aplikacije:<br>
                 &nbsp;&nbsp;&nbsp;Zeljena pozicija:<input type="text" name="zeljpoz" value="<?php echo $niz['zeljpozicija'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Zavrseni fakultet/smer:<input type="text" name="fax" value="<?php echo $niz['fakultet'] ?>">  <br>           
                  &nbsp;&nbsp;&nbsp;Licna izjava:<input type="text" name="izjava" value="<?php echo $niz['izjava'] ?>"><br>
                  Vestine:<br>
                 &nbsp;&nbsp;&nbsp;Maternji jezik*:<input type="text" name="mjezik" value="<?php echo $niz['mjezik'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Komunikacione vestine:<input type="text" name="kvestine" value="<?php echo $niz['kvestine'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Organizacione vestine:<input type="text" name="ovestine" value="<?php echo $niz['ovestine'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Menadzerske vestine:<input type="text" name="mvestine" value="<?php echo $niz['mvestine'] ?>"><br>
                 &nbsp;&nbsp;&nbsp;Druge vestine vezane za posao:<input type="text" name="vestine" value="<?php echo $niz['dvestine'] ?>"></td>
                 </td><td width="25%"></td> 
            </tr></table>
            <center>Da bi ste uneli eventualno radno iskustvo koje posedujute kliknite <a href="RadnoIskustvo.php">OVDE</a>(potreban je poseban unos za svako iskustvo koje unosite)
            <br>
            Da bi ste uneli obrazovanje koje posedujute kliknite <a href="Obrazovanje.php">OVDE</a>(potreban je poseban unos za svaki stepen obrazovanja koji imate)
            <br>
           <br>
            Da bi ste uneli poznavanje stranih jezika kliknite<a href="Jezici.php">OVDE</a>(potreban je poseban unos za svaki strani jezik)<br>
            
            <input type="button" name="potvrdi" value="POTVRDI" onClick="mojafja1()"></center><br><br><br>
            
        </form>  
    </body>
</html>

                 
   

<?php
    }

    if(array_key_exists("izjava", $_POST))
    {
        
            if($_POST['pol']=="M")
                $pol="M";
            else $pol="Z";
    
        $upit2="UPDATE biografije SET ime='".$_POST['ime']."',prezime='".$_POST['prezime']."',adresa='".$_POST['adresa']."',postanskiBroj='".$_POST['pbroj']."',grad='".$_POST['grad']."',drzava='".$_POST['drzava']."',telefon='".$_POST['telefon']."',mejl='".$_POST['mejl']."',sajt='".$_POST['sajt']."',pol='".$pol."',datumrodj='".$_POST['drodjenja']."',nacionalnost='".$_POST['nacionalnost']."',zeljpozicija='".$_POST['zeljpoz']."',fakultet='".$_POST['fax']."',izjava='".$_POST['izjava']."',mjezik='".$_POST['mjezik']."',kvestine='".$_POST['kvestine']."',ovestine='".$_POST['ovestine']."',mvestine='".$_POST['mvestine']."',dvestine='".$_POST['vestine']."'
                 WHERE korime='".$_SESSION['kime']."'";
       
        $rezultat2=mysqli_query($konekcija,$upit2)or die("Greska u upitu2");
    echo "<div id='regstudent1'>Uspesno ste izmenili svoju biografiju<br>";
    echo 'Ukoliko zelite da se vratite na studentsku strnicu kliknite <a href="student.php">OVDE</a></div><br><br>';
    }}

?>
<?php include('footer.php');?>
