package entiteti;

import entiteti.Artikal;
import entiteti.Korpa;
import entiteti.UKorpiPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-17T23:47:22")
@StaticMetamodel(UKorpi.class)
public class UKorpi_ { 

    public static volatile SingularAttribute<UKorpi, Korpa> korpa;
    public static volatile SingularAttribute<UKorpi, Artikal> artikal;
    public static volatile SingularAttribute<UKorpi, Integer> kolicina;
    public static volatile SingularAttribute<UKorpi, Integer> cena;
    public static volatile SingularAttribute<UKorpi, UKorpiPK> uKorpiPK;

}