/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ana Zoric
 */
@Entity
@Table(name = "u_gradu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UGradu.findAll", query = "SELECT u FROM UGradu u"),
    @NamedQuery(name = "UGradu.findByIdK", query = "SELECT u FROM UGradu u WHERE u.idK = :idK")})
public class UGradu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idK")
    private Integer idK;
    @JoinColumn(name = "idG", referencedColumnName = "idG")
    @ManyToOne(optional = false)
    @XmlTransient
    private Grad idG;
    @JoinColumn(name = "idK", referencedColumnName = "idK", insertable = false, updatable = false)
    @OneToOne(optional = false)
    @XmlTransient
    private Korisnik korisnik;

    public UGradu() {
    }

    public UGradu(Integer idK) {
        this.idK = idK;
    }

    public Integer getIdK() {
        return idK;
    }

    public void setIdK(Integer idK) {
        this.idK = idK;
    }

    public Grad getIdG() {
        return idG;
    }

    public void setIdG(Grad idG) {
        this.idG = idG;
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
        hash += (idK != null ? idK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UGradu)) {
            return false;
        }
        UGradu other = (UGradu) object;
        if ((this.idK == null && other.idK != null) || (this.idK != null && !this.idK.equals(other.idK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.UGradu[ idK=" + idK + " ]";
    }
    
}
