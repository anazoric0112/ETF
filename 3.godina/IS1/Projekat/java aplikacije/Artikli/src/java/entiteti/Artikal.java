/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ana Zoric
 */
@Entity
@Table(name = "artikal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikal.findAll", query = "SELECT a FROM Artikal a"),
    @NamedQuery(name = "Artikal.findByIdA", query = "SELECT a FROM Artikal a WHERE a.idA = :idA"),
    @NamedQuery(name = "Artikal.findByNaziv", query = "SELECT a FROM Artikal a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "Artikal.findByOpis", query = "SELECT a FROM Artikal a WHERE a.opis = :opis"),
    @NamedQuery(name = "Artikal.findByCena", query = "SELECT a FROM Artikal a WHERE a.cena = :cena"),
    @NamedQuery(name = "Artikal.findByPopust", query = "SELECT a FROM Artikal a WHERE a.popust = :popust")})
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idA")
    private Integer idA;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "opis")
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private int cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "popust")
    private int popust;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikal")
    @XmlTransient
    private List<UKorpi> uKorpiList;
    @JoinColumn(name = "idKat", referencedColumnName = "idKat")
    @ManyToOne(optional = false)
    @XmlTransient
    private Kategorija idKat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikal")
    @XmlTransient
    private List<Prodaje> prodajeList;

    public Artikal() {
    }

    public Artikal(Integer idA) {
        this.idA = idA;
    }

    public Artikal(Integer idA, String naziv, String opis, int cena, int popust) {
        this.idA = idA;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
        this.popust = popust;
    }
    public Artikal(String naziv, String opis, int cena, int popust, Kategorija k) {
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
        this.popust = popust;
        this.idKat = k;
    }

    public Integer getIdA() {
        return idA;
    }

    public void setIdA(Integer idA) {
        this.idA = idA;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    @XmlTransient
    public List<UKorpi> getUKorpiList() {
        return uKorpiList;
    }

    public void setUKorpiList(List<UKorpi> uKorpiList) {
        this.uKorpiList = uKorpiList;
    }

    public Kategorija getIdKat() {
        return idKat;
    }

    public void setIdKat(Kategorija idKat) {
        this.idKat = idKat;
    }

    @XmlTransient
    public List<Prodaje> getProdajeList() {
        return prodajeList;
    }

    public void setProdajeList(List<Prodaje> prodajeList) {
        this.prodajeList = prodajeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idA != null ? idA.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikal)) {
            return false;
        }
        Artikal other = (Artikal) object;
        if ((this.idA == null && other.idA != null) || (this.idA != null && !this.idA.equals(other.idA))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Artikal[ idA=" + idA + " ]";
    }
    
}
