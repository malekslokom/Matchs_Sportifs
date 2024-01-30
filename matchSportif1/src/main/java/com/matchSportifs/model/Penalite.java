package com.matchSportifs.model;



import java.time.LocalTime;

import javax.persistence.*;

@Entity
@Table(name = "penalite", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "idPenalite")
   
})
public class Penalite {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPenalite")
	private int idPenalite;
	
	@Column(name = "libelle")
	private String libelle;
	
	@Column(name = "heure")
	private LocalTime heure;
	
	@Column(name = "provisoire")
	private boolean provisoire;
	
	@Column(name = "definitive")
	private boolean definitive;

	
	@ManyToOne
	@JoinColumn(name="idJoueur") 
	private Joueur joueur;
	
	@ManyToOne
	@JoinColumn(name="idRencontre") 
	private Rencontre rencontre;
	
	
	@ManyToOne
	@JoinColumn(name="idArbitre") 
	private Arbitre arbitre;


	public int getIdPenalite() {
		return idPenalite;
	}


	public void setIdPenalite(int idPenalite) {
		this.idPenalite = idPenalite;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public LocalTime getHeure() {
		return heure;
	}


	public void setHeure(LocalTime heure) {
		this.heure = heure;
	}


	public boolean isProvisoire() {
		return provisoire;
	}


	public void setProvisoire(boolean provisoire) {
		this.provisoire = provisoire;
	}


	public boolean isDefinitive() {
		return definitive;
	}


	public void setDefinitive(boolean definitive) {
		this.definitive = definitive;
	}


	public Joueur getJoueur() {
		return joueur;
	}


	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}


	public Rencontre getRencontre() {
		return rencontre;
	}


	public void setRencontre(Rencontre rencontre) {
		this.rencontre = rencontre;
	}


	public Arbitre getArbitre() {
		return arbitre;
	}


	public void setArbitre(Arbitre arbitre) {
		this.arbitre = arbitre;
	}


	@Override
	public String toString() {
		return "Penalite [idPenalite=" + idPenalite + ", libelle=" + libelle + ", heure=" + heure + ", provisoire="
				+ provisoire + ", definitive=" + definitive + ", joueur=" + joueur + ", rencontre=" + rencontre
				+ ", arbitre=" + arbitre + "]";
	}
	
	
}
