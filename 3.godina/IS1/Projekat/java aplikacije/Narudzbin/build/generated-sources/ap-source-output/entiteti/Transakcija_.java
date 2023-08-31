package entiteti;

import entiteti.Narudzbina;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-17T23:47:22")
@StaticMetamodel(Transakcija.class)
public class Transakcija_ { 

    public static volatile SingularAttribute<Transakcija, Narudzbina> idN;
    public static volatile SingularAttribute<Transakcija, Date> vreme;
    public static volatile SingularAttribute<Transakcija, Integer> idT;
    public static volatile SingularAttribute<Transakcija, Integer> cena;

}