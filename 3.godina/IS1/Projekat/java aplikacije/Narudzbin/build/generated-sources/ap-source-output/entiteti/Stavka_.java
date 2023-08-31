package entiteti;

import entiteti.Artikal;
import entiteti.Narudzbina;
import entiteti.StavkaPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-17T23:47:22")
@StaticMetamodel(Stavka.class)
public class Stavka_ { 

    public static volatile SingularAttribute<Stavka, Narudzbina> narudzbina;
    public static volatile SingularAttribute<Stavka, StavkaPK> stavkaPK;
    public static volatile SingularAttribute<Stavka, Artikal> artikal;
    public static volatile SingularAttribute<Stavka, Integer> kolicina;
    public static volatile SingularAttribute<Stavka, Integer> cena;

}