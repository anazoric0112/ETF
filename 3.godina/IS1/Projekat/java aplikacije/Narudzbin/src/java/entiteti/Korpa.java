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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ana Zoric
 */
@Entity
@Table(name = "korpa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korpa.findAll", query = "SELECT k FROM Korpa k"),
    @NamedQuery(name = "Korpa.findByIdKorpa", query = "SELECT k FROM Korpa k WHERE k.idKorpa = :idKorpa"),
    @NamedQuery(name = "Korpa.findByCena", query = "SELECT k FROM Korpa k WHERE k.cena = :cena"),
    @NamedQuery(name = "Korpa.findByIdK", query = "SELECT k FROM Korpa k WHERE k.idK = :idK")})
public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKorpa")
    private Integer idKorpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private int cena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korpa")
    @XmlTransient
    private List<UKorpi> uKorpiList;
    @JoinColumn(name = "idK", referencedColumnName = "idK")
    @ManyToOne(optional = false)
    @XmlTransient
    private Korisnik idK;

    public Korpa() {
    }

    public Korpa(Integer idKorpa) {
        this.idKorpa = idKorpa;
    }

    public Korpa(Integer idKorpa, int cena) {
        this.idKorpa = idKorpa;
        this.cena = cena;
    }

    public Integer getIdKorpa() {
        return idKorpa;
    }

    public void setIdKorpa(Integer idKorpa) {
        this.idKorpa = idKorpa;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    @XmlTransient
    public List<UKorpi> getUKorpiList() {
        return uKorpiList;
    }

    public void setUKorpiList(List<UKorpi> uKorpiList) {
        this.uKorpiList = uKorpiList;
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
        hash += (idKorpa != null ? idKorpa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korpa)) {
            return false;
        }
        Korpa other = (Korpa) object;
        if ((this.idKorpa == null && other.idKorpa != null) || (this.idKorpa != null && !this.idKorpa.equals(other.idKorpa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korpa[ idKorpa=" + idKorpa + " ]";
    }
    
}
