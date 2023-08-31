/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ana Zoric
 */
@Entity
@Table(name = "prodaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prodaje.findAll", query = "SELECT p FROM Prodaje p"),
    @NamedQuery(name = "Prodaje.findByIdK", query = "SELECT p FROM Prodaje p WHERE p.prodajePK.idK = :idK"),
    @NamedQuery(name = "Prodaje.findByIdA", query = "SELECT p FROM Prodaje p WHERE p.prodajePK.idA = :idA"),
    @NamedQuery(name = "Prodaje.findByOcena", query = "SELECT p FROM Prodaje p WHERE p.ocena = :ocena"),
    @NamedQuery(name = "Prodaje.findByOpis", query = "SELECT p FROM Prodaje p WHERE p.opis = :opis")})
public class Prodaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProdajePK prodajePK;
    @Column(name = "ocena")
    private Integer ocena;
    @Size(max = 45)
    @Column(name = "opis")
    private String opis;
    @JoinColumn(name = "idA", referencedColumnName = "idA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private Artikal artikal;
    @JoinColumn(name = "idK", referencedColumnName = "idK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private Korisnik korisnik;

    public Prodaje() {
    }

    public Prodaje(ProdajePK prodajePK) {
        this.prodajePK = prodajePK;
    }

    public Prodaje(int idK, int idA) {
        this.prodajePK = new ProdajePK(idK, idA);
    }

    public ProdajePK getProdajePK() {
        return prodajePK;
    }

    public void setProdajePK(ProdajePK prodajePK) {
        this.prodajePK = prodajePK;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodajePK != null ? prodajePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prodaje)) {
            return false;
        }
        Prodaje other = (Prodaje) object;
        if ((this.prodajePK == null && other.prodajePK != null) || (this.prodajePK != null && !this.prodajePK.equals(other.prodajePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Prodaje[ prodajePK=" + prodajePK + " ]";
    }
    
}
