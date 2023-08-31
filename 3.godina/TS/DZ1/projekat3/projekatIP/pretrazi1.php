<?php

    $niz=array(0,0,0,0,0);
    $nizdelatnosti=array('IT','Telekomunikacije','Energetika','Gradjevina i arhitektura','Masinstvo');
    $k=0;
    $fleg=0;
    $ime=$_GET['ime'];
    $grad=$_GET['grad'];
    //for ($i=1;$i<=5;$i++)
   
    if($_GET['IT']==1)
    {$niz[0]=1;$k++;}
    if($_GET['Telekomunikacije']==1)
    {$niz[1]=1;$k++;}
    if($_GET['Energetika']==1)
    {$niz[2]=1;$k++;}
    if($_GET['Gradjevina']==1)
    {$niz[3]=1;$k++;}
    if($_GET['Masinstvo']==1)
    {$niz[4]=1;$k++;}

	include_once('db/database.inc.php');
    $konekcija=mysqli_connect(DBLOCATION, DBUSER, DBPASS, DBNAME) or die('Neuspela konekcija');
    
    //moyda treba ajax koristiti
    $upit="SELECT * FROM kompanije WHERE brzaposlenih>(-1)";
    
    if($k>0)
     $fleg=$k;   
    for($i=0;$i<5;$i++)
    { 
        if($niz[$i]==1)
        {    
            if($k==1){$upit.="AND delatnost IN('".$nizdelatnosti[$i]."')";}
           else{ if ($fleg==$k)
            { $upit.="AND delatnost IN('".$nizdelatnosti[$i]."'";  $fleg--;}
            else if($fleg==1)
             { $upit.=",'".$nizdelatnosti[$i]."')";}
              else{$fleg--; $upit.=",'".$nizdelatnosti[$i]."'";}
        }}
    }
    
    if($ime!="")
    {$upit.="AND naziv LIKE '".$ime."%'";}
    if($grad!="")
    $upit.="AND grad LIKE '".$grad."%'";
    
    $rezultat=mysqli_query($konekcija,$upit) or die('Greska u upitu');
    echo "<br>";
    
    
    if(mysqli_num_rows($rezultat)==0)
        echo "<div id='regstudent1'>Ne postoji nijedna kompanija sa takvim parametrima pretrage!</div>";
    
    else {
        echo 'Kompanije: <br>';
        echo '<table border="1" width="100%">';
        echo '<tr>';
        echo '<td>Naziv</td>';
        echo '<td>Grad</td>';
        echo '<td>Adresa</td>';
        echo '<td>Poreski ident broj</td>';
        echo '<td>Broj zaposlenih</td>';
        echo '<td>E-mail</td>';
        echo '<td>Web-adresa</td>';
        echo '<td>Delatnost</td>';
        echo '<td>Uza specijalnost</td>';
        echo '</tr>';
    while($niz1= mysqli_fetch_array($rezultat))
    {
      echo '<tr>';
       echo '<td>'.$niz1['naziv'].'</td>'; 
       echo '<td>'.$niz1['grad'].'</td>';     
       echo '<td>'.$niz1['adresa'].'</td>'; 
       echo '<td>'.$niz1['PIB'].'</td>'; 
       echo '<td>'.$niz1['brzaposlenih'].'</td>'; 
       echo '<td>'.$niz1['email'].'</td>'; 
       echo '<td>'.$niz1['sajt'].'</td>'; 
       echo '<td>'.$niz1['delatnost'].'</td>';   
       echo '<td>'.$niz1['specijalnost'].'</td>'; 
      echo "</tr>";
   
    }
    echo '</table>';
    }
    

