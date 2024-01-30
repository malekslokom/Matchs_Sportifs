package com.matchSportifs.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
@Embeddable
public class EquipeRencontreId implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idEquipe;
    private int idRencontre;

    public EquipeRencontreId(int idEquipe, int idRencontre) {
		super();
		this.idEquipe = idEquipe;
		this.idRencontre = idRencontre;
	}

	public EquipeRencontreId() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters et setters
    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public int getIdRencontre() {
        return idRencontre;
    }

    public void setIdRencontre(int idRencontre) {
        this.idRencontre = idRencontre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipeRencontreId that = (EquipeRencontreId) o;
        return idEquipe == that.idEquipe &&
               idRencontre == that.idRencontre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEquipe, idRencontre);
    }
}
