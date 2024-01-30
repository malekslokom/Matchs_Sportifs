package com.matchSportifs.model;
import java.time.LocalTime;

import javax.persistence.*;

@Entity
@Table(name = "tempsdejeux", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "idTempsDeJeux")
   
})
public class TempsDeJeux {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTempsDeJeux")
	private int idtempsDeJeux;
	
	@Column(name="heureDebut")
	private LocalTime heureDebut;
	
	@Column(name="heureFin")
	private LocalTime heureFin;
	
	@Column(name="numero")
	private String numero;
	
	@ManyToOne
	@JoinColumn(name="idRencontre") 
	private Rencontre rencontre;

	public int getIdtempsDeJeux() {
		return idtempsDeJeux;
	}

	public void setIdtempsDeJeux(int idempsDeJeux) {
		this.idtempsDeJeux = idempsDeJeux;
	}

	public LocalTime getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(LocalTime heureDebut) {
		this.heureDebut = heureDebut;
	}

	public LocalTime getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(LocalTime heureFin) {
		this.heureFin = heureFin;
	}



	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Rencontre getRencontre() {
		return rencontre;
	}

	public void setRencontre(Rencontre rencontre) {
		this.rencontre = rencontre;
	}

	@Override
	public String toString() {
		return "TempsDeJeux [idempsDeJeux=" + idtempsDeJeux + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin
				+ ", numero=" + numero + ", rencontre=" + rencontre + "]";
	}
	
}
