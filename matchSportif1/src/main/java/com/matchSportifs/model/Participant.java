package com.matchSportifs.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.*;


@Entity
@Table(name = "participant")
public class Participant {

	@EmbeddedId
	private ParticipantId id;

	@ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idRencontre") 
    @JoinColumn(name = "idRencontre")
    private Rencontre rencontre;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idJoueur") 
    @JoinColumn(name = "idJoueur")
    private Joueur joueur;
    
    @Column(name="heureEntree")
    private LocalTime heureEntree;
    
    @Column(name="heureSortie")
    private LocalTime heureSortie;

	public ParticipantId getId() {
		return id;
	}

	public void setId(ParticipantId id) {
		this.id = id;
	}

	public Rencontre getRencontre() {
		return rencontre;
	}

	public void setRencontre(Rencontre rencontre) {
		this.rencontre = rencontre;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public LocalTime getHeureEntree() {
		return heureEntree;
	}

	public void setHeureEntree(LocalTime heureEntree) {
		this.heureEntree = heureEntree;
	}

	public LocalTime getHeureSortie() {
		return heureSortie;
	}

	public void setHeureSortie(LocalTime heureSortie) {
		this.heureSortie = heureSortie;
	}
	
	public Equipe getEquipe() {
        if (joueur != null) {
            return joueur.getEquipeActuelle();
        }
        return null;
    }

//	@Override
//	public String toString() {
//		return "Participant [id=" + id + ", rencontre=" + rencontre + ", joueur=" + joueur + ", heureEntree="
//				+ heureEntree + ", heureSortie=" + heureSortie + "]";
//	}

}
