package com.matchSportifs.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "entraineur", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "idEntraineur")
   
})
public class Entraineur {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idEntraineur")
	private int idEntraineur;
	
	@Column(name = "nom")
	private String nom;
	
	@Column(name = "prenom")
	private String prenom;
	
	@Column(name = "dateNaissance")
	private LocalDate dateNaissance;
	
	@Column(name = "nationalite")
	private String nationalite;
	
	@ManyToOne
    @JoinColumn(name="idEquipe")
	private Equipe equipe;

	public int getIdEntraineur() {
		return idEntraineur;
	}

	public void setIdEntraineur(int idEntraineur) {
		this.idEntraineur = idEntraineur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	@Override
	public String toString() {
		return "Entraineur [idEntraineur=" + idEntraineur + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance="
				+ dateNaissance + ", nationalite=" + nationalite + ", equipe=" + equipe + "]";
	}
	
}
