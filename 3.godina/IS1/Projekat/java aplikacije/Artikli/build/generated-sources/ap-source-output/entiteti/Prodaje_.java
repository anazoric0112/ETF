package entiteti;

import entiteti.Artikal;
import entiteti.Korisnik;
import entiteti.ProdajePK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-17T23:29:21")
@StaticMetamodel(Prodaje.class)
public class Prodaje_ { 

    public static volatile SingularAttribute<Prodaje, ProdajePK> prodajePK;
    public static volatile SingularAttribute<Prodaje, Artikal> artikal;
    public static volatile SingularAttribute<Prodaje, Integer> ocena;
    public static volatile SingularAttribute<Prodaje, String> opis;
    public static volatile SingularAttribute<Prodaje, Korisnik> korisnik;

}