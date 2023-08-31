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
@Table(name = "grad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grad.findAll", query = "SELECT g FROM Grad g"),
    @NamedQuery(name = "Grad.findByIdG", query = "SELECT g FROM Grad g WHERE g.idG = :idG"),
    @NamedQuery(name = "Grad.findByNaziv", query = "SELECT g FROM Grad g WHERE g.naziv = :naziv")})
public class Grad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idG")
    private Integer idG;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idG")
    @XmlTransient
    private List<UGradu> uGraduList;

    public Grad() {
    }
    public Grad(String naziv) {
        this.naziv=naziv;
    }

    public Grad(Integer idG) {
        this.idG = idG;
    }

    public Grad(Integer idG, String naziv) {
        this.idG = idG;
        this.naziv = naziv;
    }

    public Integer getIdG() {
        return idG;
    }

    public void setIdG(Integer idG) {
        this.idG = idG;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public List<UGradu> getUGraduList() {
        return uGraduList;
    }

    public void setUGraduList(List<UGradu> uGraduList) {
        this.uGraduList = uGraduList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idG != null ? idG.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grad)) {
            return false;
        }
        Grad other = (Grad) object;
        if ((this.idG == null && other.idG != null) || (this.idG != null && !this.idG.equals(other.idG))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Grad[ idG=" + idG + " ]";
    }
    
}
