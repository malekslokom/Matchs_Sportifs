package com.matchSportifs.model;


import java.time.LocalTime;

import javax.persistence.*;

@Entity
@Table(name = "butrencontre", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "idButRencontre")
   
})
public class ButRencontre {

	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idButRencontre")
	private int idButRencontre;
	
	@Column(name = "heure")
	private LocalTime heure;
	
	@Column(name = "typeAction")
	private String typeAction;
	
	
	@ManyToOne
	@JoinColumn(name="idJoueur") 
	private Joueur joueur;
	
	@ManyToOne
	@JoinColumn(name="idRencontre") 
	private Rencontre rencontre;

	public int getIdButRencontre() {
		return idButRencontre;
	}

	public void setIdButRencontre(int idButRencontre) {
		this.idButRencontre = idButRencontre;
	}

	public LocalTime getHeure() {
		return heure;
	}

	public void setHeure(LocalTime heure) {
		this.heure = heure;
	}

	public String getTypeAction() {
		return typeAction;
	}

	public void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
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

//	@Override
//	public String toString() {
//		return "ButRencontre [idButRencontre=" + idButRencontre + ", heure=" + heure + ", typeAction=" + typeAction
//				+ ", joueur=" + joueur + ", rencontre=" + rencontre + "]";
//	}
	
}
