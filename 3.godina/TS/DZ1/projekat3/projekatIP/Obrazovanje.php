<?php include('header.php');
include('meniS.php');
include_once('db/database.inc.php');
?>
<html>
    <head>
    <link rel="stylesheet" type="text/css" href="stil.css">
    <script language="Javascript">
        function mojafja1(){
            var n=0;
            if(document.formaObr.od.value==''){alert('Polje od ne sme biti prazno!'); n++;}
            if(document.formaObr.do.value==''){alert('Polje do ne sme biti prazno!'); n++;}
            if(document.formaObr.do.value<document.formaObr.od.value) {alert('Polje do mora imati vecu vrednost od polja od');n++;}
            if(document.formaObr.titula.value==''){alert('Polje titula ne sme biti prazno!'); n++;}   
            if(document.formaObr.ime.value==''){alert('Polje ime fakulteta ne sme biti prazno!'); n++;}
            if(document.formaObr.grad.value==''){alert('Polje grad ne sme biti prazno!'); n++;}
            if(document.formaObr.drzava.value==''){alert('Polje drzava ne sme biti prazno!'); n++;}
        if(n==0){
                 document.forms["formaObr"].submit();
             }
        }
        </script>
    </head>
    <body>
        <table><tr><td width="50%"></td>
                <td width="25%">
        <form name="formaObr" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
            Obrazovanje:<br>
                 &nbsp;&nbsp;&nbsp;Od*:<input type="date" name="od"><br>
                 &nbsp;&nbsp;&nbsp;Do*:<input type="date" name="do"><br>
                 &nbsp;&nbsp;&nbsp;Titula*:<input type="text" name="titula"><br>
                 &nbsp;&nbsp;&nbsp;Ime fakulteta*:<input type="text" name="ime"><br>
                 &nbsp;&nbsp;&nbsp;Grad*:<input type="text" name="grad"><br>
                 &nbsp;&nbsp;&nbsp;Drzava*:<input type="text" name="drzava"><br>
                  <input type="button" name="potvrdi" value="POTVRDI" onClick="mojafja1()"> 
        </form></td>
    <td></td></tr></table>
<?php
session_start();
if($_SESSION['tip']!=1)

   header("Location: index.php");
if(array_key_exists("titula", $_POST))
{
    
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die("Greska u konekciji");
    $upit="INSERT INTO obrazovanje(korime,od,do,titula,fakultet,grad,drzava) "
            . "VALUES('".$_SESSION['kime']."','".$_POST['od']."','".$_POST['do']."','".$_POST['titula']."','".$_POST['ime']."','".$_POST['grad']."','".$_POST['drzava']."')";
    
    $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu");
    echo "<div id='regstudent1'>Uspesno ste uneli stepen obrazovanja</div>";
}

?>

</body>
</html>
    <?php include('footer.php');?>