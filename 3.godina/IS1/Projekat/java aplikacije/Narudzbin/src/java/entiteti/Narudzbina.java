/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ana Zoric
 */
@Entity
@Table(name = "narudzbina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Narudzbina.findAll", query = "SELECT n FROM Narudzbina n"),
    @NamedQuery(name = "Narudzbina.findByIdN", query = "SELECT n FROM Narudzbina n WHERE n.idN = :idN"),
    @NamedQuery(name = "Narudzbina.findByCena", query = "SELECT n FROM Narudzbina n WHERE n.cena = :cena"),
    @NamedQuery(name = "Narudzbina.findByIdKorpa", query = "SELECT n FROM Narudzbina n WHERE n.idKorpa = :idKorpa"),
    @NamedQuery(name = "Narudzbina.findByVreme", query = "SELECT n FROM Narudzbina n WHERE n.vreme = :vreme"),
    @NamedQuery(name = "Narudzbina.findByAdresa", query = "SELECT n FROM Narudzbina n WHERE n.adresa = :adresa"),
    @NamedQuery(name = "Narudzbina.findByIdK", query = "SELECT n FROM Narudzbina n WHERE n.idK = :idK")})
public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idN")
    private Integer idN;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private int cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idKorpa")
    private int idKorpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "adresa")
    private String adresa;
    @JoinColumn(name = "idG", referencedColumnName = "idG")
    @ManyToOne(optional = false)
    @XmlTransient
    private Grad idG;
    @JoinColumn(name = "idK", referencedColumnName = "idK")
    @ManyToOne(optional = false)
    @XmlTransient
    private Korisnik idK;

    public Narudzbina() {
    }

    public Narudzbina(Integer idN) {
        this.idN = idN;
    }

    public Narudzbina(Integer idN, int cena, int idKorpa, Date vreme, String adresa) {
        this.idN = idN;
        this.cena = cena;
        this.idKorpa = idKorpa;
        this.vreme = vreme;
        this.adresa = adresa;
    }
    public Narudzbina(int cena, int idKorpa, Date vreme, String adresa) {
        this.cena = cena;
        this.idKorpa = idKorpa;
        this.vreme = vreme;
        this.adresa = adresa;
    }

    public Integer getIdN() {
        return idN;
    }

    public void setIdN(Integer idN) {
        this.idN = idN;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getIdKorpa() {
        return idKorpa;
    }

    public void setIdKorpa(int idKorpa) {
        this.idKorpa = idKorpa;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Grad getIdG() {
        return idG;
    }

    public void setIdG(Grad idG) {
        this.idG = idG;
    }

    public Korisnik getIdK() {
        return idK;
    }

    public void setIdK(Korisnik idK) {
        this.idK = idK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idN != null ? idN.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Narudzbina)) {
            return false;
        }
        Narudzbina other = (Narudzbina) object;
        if ((this.idN == null && other.idN != null) || (this.idN != null && !this.idN.equals(other.idN))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Narudzbina[ idN=" + idN + " ]";
    }
    
}
