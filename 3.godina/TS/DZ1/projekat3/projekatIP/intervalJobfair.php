
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
            if(document.FormaIntervalJF.datum.value==''||document.FormaIntervalJF.datum1.value==''){alert('Polje datum ne sme biti prazno!'); n++;}
            if(document.FormaIntervalJF.vreme.value==''||document.FormaIntervalJF.vreme1.value==''){alert('Polje vreme ne sme biti prazno!'); n++;}
            if(document.FormaIntervalJF.datum1.value<document.FormaIntervalJF.datum.value) {alert('Datum pocetka intervala mora imati manju vrednost od datuma kraja intervala!');n++;}
         else {
             if(document.FormaIntervalJF.datum.value==document.FormaIntervalJF.datum1.value && document.FormaIntervalJF.vreme1.value<document.FormaIntervalJF.vreme.value)
             {
                 alert("Ako su datumi pocetka i kraja isti, vreme pocetka mora biti manje od vremena kraja!");n++;
             }    
         }    
        
        if(n==0){
                 document.forms["FormaIntervalJF"].submit();
             }
        }
        </script>
    </head>
    <body>
        <table><tr><td width="70%"></td>
                <td width="20%">
        <form name="FormaIntervalJF" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
            Od:<br>
            Datum:<input type="date" name="datum"><br>
            Vreme:<input type="time" name="vreme"><br>
            Do:<br>
            Datum:<input type="date" name="datum1"><br>
            Vreme:<input type="time" name="vreme1"><br>
            <input type="button" name="potvrdi" value="POTVRDI" onClick="mojafja1()">
            
        </form></td>
    <td ></td></tr></table>
   
<?php
session_start();
if($_SESSION['tip']!=3)

   header("Location: index.php");

if(array_key_exists('vreme1', $_POST))
{
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji!");
    $upit="INSERT INTO intervalJF(datumod,datumdo,vremeod,vremedo) VALUES('".$_POST['datum']."','".$_POST['datum1']."','".$_POST['vreme']."','".$_POST['vreme1']."')";
    $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu");
    echo "<div id='regstudent1'>Uspesno ste definisali interval u kom kompanije mogu da se prijavljuju na Jobfair! Vratite se na stranicu administratora <a href='admin.php'>OVDE</a><div>";
    
}?>
</body>
</html>
<?php include('footer.php');?>

