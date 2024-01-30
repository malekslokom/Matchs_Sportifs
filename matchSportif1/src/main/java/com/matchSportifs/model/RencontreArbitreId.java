package com.matchSportifs.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
@Embeddable

public class RencontreArbitreId implements Serializable  {
	private static final long serialVersionUID = 1L;
	
    private int idRencontre;
    private int idArbitre;

	public int getIdRencontre() {
		return idRencontre;
	}

	public void setIdRencontre(int idRencontre) {
		this.idRencontre = idRencontre;
	}

	public int getIdArbitre() {
		return idArbitre;
	}

	public void setIdArbitre(int idArbitre) {
		this.idArbitre = idArbitre;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public RencontreArbitreId(int idRencontre, int idArbitre) {
		super();
		this.idRencontre = idRencontre;
		this.idArbitre = idArbitre;
	}

	public RencontreArbitreId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RencontreArbitreId that = (RencontreArbitreId) o;
        return idArbitre == that.idArbitre &&
               idRencontre == that.idRencontre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArbitre, idRencontre);
    }
    
    
    
}
