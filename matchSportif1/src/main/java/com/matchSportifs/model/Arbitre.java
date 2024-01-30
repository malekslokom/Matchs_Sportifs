package com.matchSportifs.model;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "arbitre", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "id")
   
})
public class Arbitre {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "nom")
	private String nom;
	
	@Column(name = "prenom")
	private String prenom;
	
	@Column(name = "dateNaissance")
	private LocalDate dateNaissance;
	
	@Column(name = "nationalite")
	private String nationalite;
	

	@ManyToMany(mappedBy = "arbitres")
	private Set<Rencontre> rencontres = new HashSet<>();

	
    @OneToMany(mappedBy = "arbitre", fetch = FetchType.LAZY)
    private Set<Penalite> penalites;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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



	public Set<Penalite> getPenalites() {
		return penalites;
	}


	public Set<Rencontre> getRencontres() {
		return rencontres;
	}


	public void setRencontres(Set<Rencontre> rencontres) {
		this.rencontres = rencontres;
	}


	public void setPenalites(Set<Penalite> penalites) {
		this.penalites = penalites;
	}

	 public void addRencontre(Rencontre rencontre) {
	        this.rencontres.add(rencontre);
	        rencontre.getArbitres().add(this);
	    }

	 public void removeRencontre(Rencontre rencontre) {
	        this.rencontres.remove(rencontre);
	        rencontre.getArbitres().remove(this);
	  }

	    @Override
	    public String toString() {
	        return "Arbitre [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance="
	                + dateNaissance + ", nationalite=" + nationalite + ", rencontres=" + rencontres + ", penalites="
	                + penalites + "]";
	    }	
	    
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        Arbitre arbitre = (Arbitre) o;
	        return Objects.equals(id, arbitre.id);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }

//	@Override
//	public String toString() {
//		return "Arbitre [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance="
//				+ dateNaissance + ", nationalite=" + nationalite + ", rencontre=" + rencontre + ", penalites="
//				+ penalites + "]";
//	}
	
}
