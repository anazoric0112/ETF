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
            if(document.FormaJezici.jezik.value==''){alert('Polje jezik ne sme biti prazno!'); n++;}
            if(n==0){
                 document.forms["FormaJezici"].submit();
             }
        }
        </script>
    </head>
    <?php session_start();
    if($_SESSION['tip']!=1)
        header("Location: index.php");?>
    <body> 
        <table>
            <tr>
                <td width="55%"></td>
                <td width="20%">
                    <form name="FormaJezici" method="POST" action="<?php echo $_SERVER['PHP_SELF'];?>">
                        Jezik*:<input type="text" name="jezik"><br>
                        Razumevanje:<select name="raz">
                            <option value="A1">A1</option>
                            <option value="A2">A2</option>
                            <option value="B1">B1</option>
                            <option value="B2">B2</option>
                            <option value="C1">C1</option>
                            <option value="C2">C2</option>
                        </select><br>
                        Citanje:<select name="cit">
                            <option value="A1">A1</option>
                            <option value="A2">A2</option>
                            <option value="B1">B1</option>
                            <option value="B2">B2</option>
                            <option value="C1">C1</option>
                            <option value="C2">C2</option>
                        </select><br>
                        Pricanje:<select name="pric">
                            <option value="A1">A1</option>
                            <option value="A2">A2</option>
                            <option value="B1">B1</option>
                            <option value="B2">B2</option>
                            <option value="C1">C1</option>
                            <option value="C2">C2</option>
                        </select><br>
                        Pisanje:<select name="pis">
                            <option value="A1">A1</option>
                            <option value="A2">A2</option>
                            <option value="B1">B1</option>
                            <option value="B2">B2</option>
                            <option value="C1">C1</option>
                            <option value="C2">C2</option>
                        </select><br>
                        <center> <input type="button" value="POTVRDI" onClick="mojafja1()"></center>
                    </form>
                </td>
                <td width="25%"></td>
            </tr>
        </table>
<?php
if(array_key_exists('jezik', $_POST)){
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME)or die("Greska u konekciji");
    $upit="INSERT INTO jezici(korime,jezik,razumevanje,citanje,pricanje,pisanje) VALUES('".$_SESSION['kime']."','".$_POST['jezik']."','".$_POST['raz']."','".$_POST['cit']."','".$_POST['pric']."','".$_POST['pis']."')";
    $rezultat=mysqli_query($konekcija,$upit)or die("Greska u upitu");
    echo "<div id='regstudent1'>Uspesno ste uneli poznavanje stranog jezika!</div>";
}

?>
</body></html>
<?php include('footer.php');?>