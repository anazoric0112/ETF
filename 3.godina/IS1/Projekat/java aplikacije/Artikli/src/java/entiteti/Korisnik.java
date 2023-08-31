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
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIdK", query = "SELECT k FROM Korisnik k WHERE k.idK = :idK"),
    @NamedQuery(name = "Korisnik.findByUsername", query = "SELECT k FROM Korisnik k WHERE k.username = :username"),
    @NamedQuery(name = "Korisnik.findByPassword", query = "SELECT k FROM Korisnik k WHERE k.password = :password"),
    @NamedQuery(name = "Korisnik.findByIme", query = "SELECT k FROM Korisnik k WHERE k.ime = :ime"),
    @NamedQuery(name = "Korisnik.findByPrezime", query = "SELECT k FROM Korisnik k WHERE k.prezime = :prezime"),
    @NamedQuery(name = "Korisnik.findByAdresa", query = "SELECT k FROM Korisnik k WHERE k.adresa = :adresa"),
    @NamedQuery(name = "Korisnik.findByNovac", query = "SELECT k FROM Korisnik k WHERE k.novac = :novac")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idK")
    private Integer idK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ime")
    private String ime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "prezime")
    private String prezime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "adresa")
    private String adresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "novac")
    private int novac;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idK")
    @XmlTransient
    private List<Korpa> korpaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korisnik")
    @XmlTransient
    private List<Prodaje> prodajeList;

    public Korisnik() {
    }

    public Korisnik(Integer idK) {
        this.idK = idK;
    }

    public Korisnik(Integer idK, String username, String password, String ime, String prezime, String adresa, int novac) {
        this.idK = idK;
        this.username = username;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.novac = novac;
    }

    public Integer getIdK() {
        return idK;
    }

    public void setIdK(Integer idK) {
        this.idK = idK;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getNovac() {
        return novac;
    }

    public void setNovac(int novac) {
        this.novac = novac;
    }

    @XmlTransient
    public List<Korpa> getKorpaList() {
        return korpaList;
    }

    public void setKorpaList(List<Korpa> korpaList) {
        this.korpaList = korpaList;
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
        hash += (idK != null ? idK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.idK == null && other.idK != null) || (this.idK != null && !this.idK.equals(other.idK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korisnik[ idK=" + idK + " ]";
    }
    
}
