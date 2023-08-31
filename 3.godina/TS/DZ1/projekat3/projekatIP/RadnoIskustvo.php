<?php include('header.php');
include('meniS.php');
include_once('db/database.inc.php');
?>
<head>
    <link rel="stylesheet" type="text/css" href="stil.css">
    <script language="Javascript">
        function mojafja1(){
            var n=0;
            if(document.formaRadIsk.od.value==''){alert('Polje od ne sme biti prazno!'); n++;}
            if(document.formaRadIsk.do.value==''){alert('Polje do ne sme biti prazno!'); n++;}
            if(document.formaRadIsk.do.value<document.formaRadIsk.od.value) {alert('Polje do mora imati vecu vrednost od polja od');n++;}
            if(document.formaRadIsk.pozicija.value==''){alert('Polje pozicija ne sme biti prazno!'); n++;}   
            if(document.formaRadIsk.kompanija.value==''){alert('Polje kompanija ne sme biti prazno!'); n++;}
            if(document.formaRadIsk.gradkom.value==''){alert('Polje grad ne sme biti prazno!'); n++;}
            if(document.formaRadIsk.drzavakom.value==''){alert('Polje drzava ne sme biti prazno!'); n++;}
        if(n==0){
                 document.forms["formaRadIsk"].submit();
             }
        }
        </script>
    </head>

<html>
    <body>
        <table><tr><td width="50%"></td>
                <td width="25%">
        <form name="formaRadIsk" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
            Radno iskustvo:<br>
                 &nbsp;&nbsp;&nbsp;Od:<input type="date" name="od"><br>
                 &nbsp;&nbsp;&nbsp;Do:<input type="date" name="do"><br>
                 &nbsp;&nbsp;&nbsp;Pozicija:<input type="text" name="pozicija"><br>
                 &nbsp;&nbsp;&nbsp;Kompanija:<input type="text" name="kompanija"><br>
                 &nbsp;&nbsp;&nbsp;Grad:<input type="text" name="gradkom"><br>
                 &nbsp;&nbsp;&nbsp;Drzava:<input type="text" name="drzavakom"><br>
                 <input type="button" name="potvrdi" value="POTVRDI" onClick="mojafja1()">
            
        </form></td>
    <td></td></tr></table>
    </body>
</html>

<?php
session_start();
if($_SESSION['tip']!=1)

   header("Location: index.php");

if(array_key_exists("pozicija", $_POST))
{
    
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
    $upit="INSERT INTO radnoiskustvo(korime,od,do,pozicija,kompanija,grad,drzava) "
            . "VALUES('".$_SESSION['kime']."','".$_POST['od']."','".$_POST['do']."','".$_POST['pozicija']."','".$_POST['kompanija']."','".$_POST['gradkom']."','".$_POST['drzavakom']."')";
    
    $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu");
    echo "<div id='regstudent1'>Uspesno ste uneli radno iskustvo</div>";
  
}

?>
</body>
</html>
    <?php include('footer.php');?>
