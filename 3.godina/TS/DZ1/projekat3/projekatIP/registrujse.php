<?php include('header1.php');?>
<html>
    <head>
        <script language="JavaScript">
        
             function mojafja1()
             {
                 var k=0
                 var n=0
                 var greska=""
             var uzorak1=/.{8,12}/
             var uzorak2=/[A-Z]{1,}/
             var uzorak3=/[a-z]{1,}/
             var uzorak4=/[0-9]{1,}/
             var uzorak5=/[!,.,@,#,?,*]{1,}/
             var uzorak6=/.{1,}@.{1,}/
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
             if(document.registrujForma2.kime.value==''){alert('Korisnicko ime ne sme biti prazno!'); n++;}
             if(document.registrujForma2.ime.value==''){alert('Polje ime ne sme biti prazno!'); n++;}
             if(document.registrujForma2.prezime.value==''){alert('Polje prezime ne sme biti prazno!'); n++;}
             if(document.registrujForma2.telefon.value==''){alert('Polje telefon ne sme biti prazno!'); n++;}
             if(document.registrujForma2.mejl.value==''){alert('Polje mejl ne sme biti prazno!'); n++;}
             if(!uzorak6.test(document.registrujForma2.mejl.value)){alert('Polje mejl nije u pravom formatu!');n++;}
             if(document.registrujForma2.godstudija.value==''){alert('Polje godina studija ne sme biti prazno!'); n++;}
             if(k!=0)
             alert(greska);
             if(k==0&&n==0)
             document.forms["registrujForma2"].submit();
              }
              function mojafja2()
             {
                 var k=0
                 var n=0
                 var greska=""
             var uzorak1=/.{8,12}/
             var uzorak2=/[A-Z]{1,}/
             var uzorak3=/[a-z]{1,}/
             var uzorak4=/[0-9]{1,}/
             var uzorak5=/[!,.,@,#,?,*]{1,}/
             var uzorak6=/.{1,}@.{1,}/
             var sifra=document.registrujForma3.sifra.value
             for(i=0;i<sifra.length-1;i++)
             {if(sifra.charAt(i)==sifra.charAt(i+1))
             {greska+="Sifra ne moze imati dva ista uzastopna znaka!\n";break;k++}}
             if(!uzorak1.test(document.registrujForma3.sifra.value))
             {greska+="Sifra mora imati od 8 do 12 karaktera!\n";k++}
             if(!uzorak2.test(document.registrujForma3.sifra.value))
             { greska+="Sifra mora imati bar jedno veliko slovo!\n";k++}
             if(!uzorak3.test(document.registrujForma3.sifra.value))
             {  greska+="Sifra mora imati bar jedno malo slovo!\n"; k++}
             if(!uzorak4.test(document.registrujForma3.sifra.value))
                 {greska+="Sifra mora imati bar jedan broj!\n";k++}
             if(!uzorak5.test(document.registrujForma3.sifra.value))
             {greska+="Sifra mora imati bar jedan specijalan karakter!\n";k++}
             if(document.registrujForma3.kime.value==''){alert('Korisnicko ime ne sme biti prazno!'); n++;}
             if(document.registrujForma3.naziv.value==''){alert('Polje naziv kompanije ne sme biti prazno!'); n++;}
             if(document.registrujForma3.grad.value==''){alert('Polje grad ne sme biti prazno!'); n++;}
             if(document.registrujForma3.adresa.value==''){alert('Polje adresa ne sme biti prazno!'); n++;}
             if(document.registrujForma3.imedir.value==''){alert('Polje ime direktora ne sme biti prazno!'); n++;}
             if(document.registrujForma3.pib.value==''){alert('Polje PIB ne sme biti prazno!'); n++;}
             if(document.registrujForma3.brzaposlenih.value==''){alert('Polje broj zaposlenih ne sme biti prazno!'); n++;}
             if(document.registrujForma3.mejl.value==''){alert('Polje mejl ne sme biti prazno!'); n++;}
             if(!uzorak6.test(document.registrujForma3.mejl.value)){alert('Mejl nije u pravom formatu!');n++;}
             if(document.registrujForma3.sajt.value==''){alert('Polje sajt ne sme biti prazno!'); n++;}
             if(k!=0)
             alert(greska);
             if(k==0&&n==0)
             
             document.forms["registrujForma3"].submit();
              }
        </script>
         <link rel="stylesheet" type="text/css" href="stil.css">
    </head>
    <?php
if(!array_key_exists('Potvrdi', $_POST))
{
    
?>
    <body>
        <div id="registruj">
            <table>
                <tr>
                    <td width="37%"></td>
                    <td width="33%">
        <form name="registrujForma1" method="POST" action="<?php echo $_SERVER['PHP_SELF']?>">  
            Da li se registrujete kao student/diplomac ili kao kompanija?<br>
            <center> <select name="reg">
            <option value="Student">Student/diplomac</option><br>
            <option value="Kompanija">Kompanija</option></center>
            
        </select><br>
        <center> <input type="submit" name="Potvrdi" value="POTVRDI"></center<br>
         
        </form>
                <center>
                    <a href="index.php">NAZAD</a></center>
                        </td>
                        <td width="30%"></td>
        </tr></table></div>
    <?php
}
else{
if($_POST['reg']=="Student")
{
     echo "<table>";
     echo "<tr>";
     echo "<td width='35%'></td>";
     echo "<td width='30%'>";
     echo '<form name="registrujForma2" method="POST" action="regStudent.php">';
     echo "Unesite korisnicko ime:";
     echo '<input type="text" name="kime"><br>';
     echo 'Unesite sifru:';
     echo '<input type="password" name="sifra"><br>';
     echo 'Unesite svoje ime:';
     echo '<input type="text" name="ime"><br>';
     echo 'Unesite svoje prezime:';
     echo '<input type="text" name="prezime"><br>';
     echo 'Unesite broj telefona:';
     echo '<input type="text" name="telefon"><br>';
     echo 'Unesite e-mail adresu:';
     echo '<input type="text" name="mejl"><br>';
     echo 'Unesite godinu studija:';
     echo '<input type="number" name="godstudija"><br>';
     echo 'Da li ste diplomirali?';
     
     echo '<input type="radio" name="diploma" value="da">Da';
     echo '<input type="radio" name="diploma" value="ne">Ne<br>';
     echo 'Dodajte svoju sliku';
     echo '<input type="file" name="slika"><br>';
     echo '<center><input type="button" name="Registruj" value="REGISTRUJ SE" onClick="mojafja1()"></center><br>';  
     echo '</form>';
     echo '<center><a href="index.php">NAZAD</a></center>';
     echo "</td>";
     echo "<td width='25%'></td>";
     
     echo "</tr>";echo "</table>";
     
//slika!!

}
?>

<?php
if($_POST['reg']=="Kompanija")
{    
     echo "<table>";
     echo "<tr>";
     echo "<td width='35%'></td>";
     echo "<td width='30%'>";
     echo '<form name="registrujForma3" method="POST" action="regKompanija.php">';
     echo "Unesite korisnicko ime:";
     echo '<input type="text" name="kime"><br>';
     echo 'Unesite sifru:';
     echo '<input type="password" name="sifra"><br>';
     echo 'Unesite naziv kompanije:';
     echo '<input type="text" name="naziv"><br>';
     echo 'Unesite grad u kom se kompanija nalazi:';
     echo '<input type="text" name="grad"><br>';
     echo 'Unesite adresu kompanije:';
     echo '<input type="text" name="adresa"><br>';
     echo 'Unesite ime i prezime direktora:';
     echo '<input type="text" name="imedir"><br>';
     echo 'Unesite PIB:';
     echo '<input type="text" name="pib"><br>';
     echo 'Unesite trenutni broj zaposlenih:';
     echo '<input type="number" name="brzaposlenih"><br>';
     echo 'Unesite e-mail adresu:';
     echo '<input type="text" name="mejl"><br>';
     echo 'Unesite web adresu:';
     echo '<input type="text" name="sajt"><br>';
     echo 'Kojom delatnoscu se bavite?';
     echo '<select name="delatnost">';
     echo '<option selected value="IT">IT</option>';
     echo '<option value="Telekomunikacije">Telekomunikacije</option>';
     echo '<option value="Energentika">Energetika</option>';
     echo '<option value="Gradjevina">Gradjevina i arhitektura</option>';
     echo '<option value="Masinstvo">Masinstvo</option>';
     echo '</select><br>';
     echo 'Koja Vam je uza specijalnost?';
     echo '<textarea name="specijalnost" rows="3" cols="20" maxlenght="200"></textarea><br>';
     echo 'Unesite logo kompanije:';
     echo '<input type="file" name="slika"><br>';
     echo '<center><input type="button" name="Registruj" value="REGISTRUJ SE" onClick="mojafja2()"></center><br>';
     echo '</form>';
     echo '<center><a href="index.php">NAZAD</a></center>';
     echo "</td>";
     echo "<td width='25%'></td>";
     
     echo "</tr>";echo "</table>";
     
//slika!!!
}
}

?>

    </body>
    
</html>
<?php include('footer.php');?>