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
public class UKorpiPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idKorpa")
    private int idKorpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idA")
    private int idA;

    public UKorpiPK() {
    }

    public UKorpiPK(int idKorpa, int idA) {
        this.idKorpa = idKorpa;
        this.idA = idA;
    }

    public int getIdKorpa() {
        return idKorpa;
    }

    public void setIdKorpa(int idKorpa) {
        this.idKorpa = idKorpa;
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
        hash += (int) idKorpa;
        hash += (int) idA;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UKorpiPK)) {
            return false;
        }
        UKorpiPK other = (UKorpiPK) object;
        if (this.idKorpa != other.idKorpa) {
            return false;
        }
        if (this.idA != other.idA) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.UKorpiPK[ idKorpa=" + idKorpa + ", idA=" + idA + " ]";
    }
    
}
