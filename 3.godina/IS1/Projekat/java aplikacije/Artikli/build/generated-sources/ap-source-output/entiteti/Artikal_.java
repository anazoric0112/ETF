package entiteti;

import entiteti.Kategorija;
import entiteti.Prodaje;
import entiteti.UKorpi;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-17T23:29:21")
@StaticMetamodel(Artikal.class)
public class Artikal_ { 

    public static volatile ListAttribute<Artikal, UKorpi> uKorpiList;
    public static volatile SingularAttribute<Artikal, Kategorija> idKat;
    public static volatile ListAttribute<Artikal, Prodaje> prodajeList;
    public static volatile SingularAttribute<Artikal, Integer> idA;
    public static volatile SingularAttribute<Artikal, String> naziv;
    public static volatile SingularAttribute<Artikal, Integer> popust;
    public static volatile SingularAttribute<Artikal, Integer> cena;
    public static volatile SingularAttribute<Artikal, String> opis;

}