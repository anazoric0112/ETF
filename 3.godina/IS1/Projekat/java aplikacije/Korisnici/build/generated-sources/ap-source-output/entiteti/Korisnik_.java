package entiteti;

import entiteti.UGradu;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-17T23:47:18")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile SingularAttribute<Korisnik, String> prezime;
    public static volatile SingularAttribute<Korisnik, Integer> idK;
    public static volatile SingularAttribute<Korisnik, String> password;
    public static volatile SingularAttribute<Korisnik, Integer> novac;
    public static volatile SingularAttribute<Korisnik, UGradu> uGradu;
    public static volatile SingularAttribute<Korisnik, String> adresa;
    public static volatile SingularAttribute<Korisnik, String> username;

}