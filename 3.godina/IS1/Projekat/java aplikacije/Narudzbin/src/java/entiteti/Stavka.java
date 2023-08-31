/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ana Zoric
 */
@Entity
@Table(name = "stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavka.findAll", query = "SELECT s FROM Stavka s"),
    @NamedQuery(name = "Stavka.findByIdN", query = "SELECT s FROM Stavka s WHERE s.stavkaPK.idN = :idN"),
    @NamedQuery(name = "Stavka.findByIdA", query = "SELECT s FROM Stavka s WHERE s.stavkaPK.idA = :idA"),
    @NamedQuery(name = "Stavka.findByKolicina", query = "SELECT s FROM Stavka s WHERE s.kolicina = :kolicina"),
    @NamedQuery(name = "Stavka.findByCena", query = "SELECT s FROM Stavka s WHERE s.cena = :cena")})
public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StavkaPK stavkaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private int cena;
    @JoinColumn(name = "idN", referencedColumnName = "idN", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private Narudzbina narudzbina;
    @JoinColumn(name = "idA", referencedColumnName = "idA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private Artikal artikal;

    public Stavka() {
    }

    public Stavka(StavkaPK stavkaPK) {
        this.stavkaPK = stavkaPK;
    }

    public Stavka(StavkaPK stavkaPK, int kolicina, int cena) {
        this.stavkaPK = stavkaPK;
        this.kolicina = kolicina;
        this.cena = cena;
    }

    public Stavka(int idN, int idA) {
        this.stavkaPK = new StavkaPK(idN, idA);
    }

    public StavkaPK getStavkaPK() {
        return stavkaPK;
    }

    public void setStavkaPK(StavkaPK stavkaPK) {
        this.stavkaPK = stavkaPK;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Narudzbina getNarudzbina() {
        return narudzbina;
    }

    public void setNarudzbina(Narudzbina narudzbina) {
        this.narudzbina = narudzbina;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stavkaPK != null ? stavkaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.stavkaPK == null && other.stavkaPK != null) || (this.stavkaPK != null && !this.stavkaPK.equals(other.stavkaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Stavka[ stavkaPK=" + stavkaPK + " ]";
    }
    
}
