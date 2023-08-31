<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<?php include('header1.php');?>
<html>
    <head>
        <meta charset="UTF-8">
        <title>JOBFAIR</title>
        <link rel="stylesheet" type="text/css" href="stil.css">
    </head>
    <body>
        <table width='100%'>
            <tr>
                <td width='40%'></td>
                <td width='20%'>
        <form method="POST" action="login.php" name="mojaForma">
            <table>
                <tr>
                <td>Korisnicko ime:</td>
                <td><input type="text" name="user" placeholder="Unesite korisnicko ime"></td>
                </tr>
                <tr>
                    <td>Sifra:</td>
                    <td><input type="password" name="pass" placeholder="Unesite sifru"></td>
            </tr></table>
            <center><input type="submit" name="potvrdi" value="POTVRDI"><br></center>
        </form>
                
        
        <a href="registrujse.php">REGISTRUJ SE:</a><br>
        
        <a href="promenipass.php">PROMENI LOZINKU:</a><BR>
        
        <a href="pretrazi.php">PRETRAZI KOMPANIJE</a>
        </td>
                
        <td width='40%'></td>
            </tr>
         </table>
       
       
    </body>
</html>
<?php include('footer.php');?>