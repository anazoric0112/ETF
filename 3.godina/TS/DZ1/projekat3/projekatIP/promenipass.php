<?php include('header1.php');?>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="stil.css">
        <script language="Javascript">
        function mojafja1()
             {
                 var k=0
                 var greska=""
             var uzorak1=/.{8,12}/
             var uzorak2=/[A-Z]{1,}/
             var uzorak3=/[a-z]{1,}/
             var uzorak4=/[0-9]{1,}/
             var uzorak5=/[!,.,@,#,?,*]{1,}/
             var sifra=document.registrujForma2.sifra.value
             for(i=0;i<sifra.length-1;i++)
             {if(sifra.charAt(i)==sifra.charAt(i+1))
             {greska+="Sifra ne moze imati dva ista uzastopna znaka!\n";break;k++}}
             if(!uzorak1.test(document.registrujForma2.sifra.value))
             {greska+="Sifra mora imati od 8 do 12 karaktera!\n";k++}
             if(!uzorak2.test(document.registrujForma2.sifra.value))
             { greska+="Sifra mora imati bar jedno veliko slovo!\n";k++}
             if(!uzorak3.test(document.registrujForma2.sifra.value))
             {  greska+="Sifra mora imati bar jedno malo slovo!\n"; k++}
             if(!uzorak4.test(document.registrujForma2.sifra.value))
                 {greska+="Sifra mora imati bar jedan broj!\n";k++}
             if(!uzorak5.test(document.registrujForma2.sifra.value))
             {greska+="Sifra mora imati bar jedan specijalan karakter!\n";k++}
             if(k!=0)
             alert(greska);
         else document.forms["registrujForma2"].submit();
              }
        </script>
    </head>
    <body>
<?php
include_once('db/database.inc.php');
if(!array_key_exists('user', $_POST))
{
?>
        <table>
            <tr>
                <td width="45%"></td>
                <td width="20%">
        <form name="registrujForma2" action="<?php echo $_SERVER['PHP_SELF'];?>" method="POST">
            Korisnicko ime:
            <input type="text" name="user" placeholder="Unesite korisnicko ime"><br>
            Sifra:
            <input type="password" name="passstari" placeholder="Unesite staru sifru"><br>
            Nova sifra:
            <input type="password" name="sifra" placeholder="Unesite novu sifru"><br>
            <center>
                <input type="button" name="potvrdi" value="POTVRDI" onClick="mojafja1()"></center><br>
        </form>
                </td>
            <td width="35%"></td>
         </tr></table>
    </body>
    
</html>

<?php
}
else

{
    $kime=$_POST['user'];
    $sifra1=$_POST['passstari'];
    $sifranova1=$_POST['sifra'];
    $sifra=md5($sifra1);
    $sifranova=md5($sifranova1);
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die('Neuspela konekcija');
    $upit= "SELECT * FROM korisnici WHERE korisnickoime='".$kime."' AND password='".$sifra."'";
    $rezultat=mysqli_query($konekcija,$upit) or die('Greska u upitu');
    if(mysqli_num_rows($rezultat)==0)
    {echo "<div id='regstudent1'>Pogresno korisnicko ime ili sifra</div>";}
    else
    {
        $upit1="UPDATE korisnici SET password='".$sifranova."'
                WHERE korisnickoime='".$kime."' AND password='".$sifra."'";
        $rezultat=mysqli_query($konekcija,$upit1) or die('Greska u upitu');
        echo "<div id='regstudent1'>Uspesno ste promenili sifru!<br> Vratite se na stranicu za logovanje <a href='index.php'>OVDE</a>";
     
    }
    
    }
   
?>
<?php include('footer.php');?>
