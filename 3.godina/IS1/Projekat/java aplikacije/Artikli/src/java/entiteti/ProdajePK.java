/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ana Zoric
 */
@Embeddable
public class ProdajePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idK")
    private int idK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idA")
    private int idA;

    public ProdajePK() {
    }

    public ProdajePK(int idK, int idA) {
        this.idK = idK;
        this.idA = idA;
    }

    public int getIdK() {
        return idK;
    }

    public void setIdK(int idK) {
        this.idK = idK;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idK;
        hash += (int) idA;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProdajePK)) {
            return false;
        }
        ProdajePK other = (ProdajePK) object;
        if (this.idK != other.idK) {
            return false;
        }
        if (this.idA != other.idA) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.ProdajePK[ idK=" + idK + ", idA=" + idA + " ]";
    }
    
}
