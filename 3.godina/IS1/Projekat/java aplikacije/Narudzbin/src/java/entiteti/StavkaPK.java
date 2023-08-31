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
public class StavkaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idN")
    private int idN;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idA")
    private int idA;

    public StavkaPK() {
    }

    public StavkaPK(int idN, int idA) {
        this.idN = idN;
        this.idA = idA;
    }

    public int getIdN() {
        return idN;
    }

    public void setIdN(int idN) {
        this.idN = idN;
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
        hash += (int) idN;
        hash += (int) idA;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StavkaPK)) {
            return false;
        }
        StavkaPK other = (StavkaPK) object;
        if (this.idN != other.idN) {
            return false;
        }
        if (this.idA != other.idA) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.StavkaPK[ idN=" + idN + ", idA=" + idA + " ]";
    }
    
}
