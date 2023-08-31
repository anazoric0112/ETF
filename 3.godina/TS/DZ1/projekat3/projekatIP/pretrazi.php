<?php include('header1.php');?>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="stil.css">
        <script language="Javascript">
         
     function prikazi(str){
             if (str.lenght == 0) {
            document.getElementById("txtIspis").innerHTML = "";

            return;

}
if (window.XMLHttpRequest) {
objekat = new XMLHttpRequest();
} else {
objekat = new ActiveXObject("Microsoft.XMLHTTP");
}
objekat.onreadystatechange = function() {
if (objekat.readyState == 4 && objekat.status == 200) {
document.getElementById("txtIspis").innerHTML =
objekat.responseText;
}
}
IT=0; tel=0; en=0; gr=0;mas=0
grad=document.PretraziForma.grad.value;
ime=document.PretraziForma.ime.value;
if(document.getElementById("IT").checked) IT=1;
if(document.getElementById("Telekomunikacije").checked) tel=1;
if(document.getElementById("Energetika").checked) en=1;
if(document.getElementById("Gradjevina").checked) gr=1;
if(document.getElementById("Masinstvo").checked) mas=1;
objekat.open("GET", "pretrazi1.php?ime=" + ime+"&grad="+grad+"&IT="+IT+"&Telekomunikacije="+tel+"&Energetika="+en+"&Gradjevina="+gr+"&Masinstvo="+mas, true);
objekat.send();

}


         
    </script>
    </head>
    <body>
        <table>
            <tr>
                <td width="40%"></td>
                <td width="40%">
        <form  name="PretraziForma" action="">
            Ime kompanije:<input type="text" name="ime" onkeyup="prikazi(this.value)"><br>
            Grad u kojoj se kompanija nalazi: <input type="text" name="grad" onkeyup="prikazi(this.value)"><br>
            Delatnost kojom se kompanija bavi:<br>
            <input type="checkbox" id="IT" onchange="prikazi(this.value)">IT<br>
            <input type="checkbox" id="Telekomunikacije" onchange="prikazi(this.value)">Telekomunikacije<br>
            <input type="checkbox" id="Energetika" onchange="prikazi(this.value)">Energetika<br>
            <input type="checkbox" id="Gradjevina" onchange="prikazi(this.value)">Gradjevina i arhitektura<br>
            <input type="checkbox" id="Masinstvo" onchange="prikazi(this.value)">Masinstvo<br>
           
        </form></td>
            <td width="20%"></td>
        </tr> </table>  
        <div id="txtIspis"></div>
    </body>
    
</html>

<?php

?>
<?php include('footer.php');?>
