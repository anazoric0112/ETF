package entiteti;

import entiteti.Grad;
import entiteti.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-17T23:47:22")
@StaticMetamodel(Narudzbina.class)
public class Narudzbina_ { 

    public static volatile SingularAttribute<Narudzbina, Korisnik> idK;
    public static volatile SingularAttribute<Narudzbina, Integer> idN;
    public static volatile SingularAttribute<Narudzbina, Integer> idKorpa;
    public static volatile SingularAttribute<Narudzbina, Date> vreme;
    public static volatile SingularAttribute<Narudzbina, String> adresa;
    public static volatile SingularAttribute<Narudzbina, Integer> cena;
    public static volatile SingularAttribute<Narudzbina, Grad> idG;

}