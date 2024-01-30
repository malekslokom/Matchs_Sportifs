package com.matchSportifs.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
@Embeddable
public class ParticipantId implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idRencontre;
    private int idJoueur;
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantId that = (ParticipantId) o;
        return idRencontre == that.idRencontre &&
        		idJoueur == that.idJoueur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRencontre, idJoueur);
    }

	public int getIdRencontre() {
		return idRencontre;
	}

	public int getIdJoueur() {
		return idJoueur;
	}

	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}

	public void setIdRencontre(int idRencontre) {
		this.idRencontre = idRencontre;
	}
}
