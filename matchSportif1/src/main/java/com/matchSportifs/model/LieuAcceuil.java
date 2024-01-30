package com.matchSportifs.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "lieuacceuil", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "idlieuAcceuil")
   
})
public class LieuAcceuil {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idlieuAcceuil")
	private int idlieuAcceuil;
	
	@Column(name="type")
	private String type;
	
	@Column(name="nom")
	private String nom;
	
	@ManyToOne
    @JoinColumn(name="idAdresse")
	private Adresse adresse;
	
	@Column(name="courriel")
	private String courriel;
	
	@Column(name="infosPMR")
	private String infosPMR;
	
	@Column(name="commentaires")
	private String commentaires;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="siteInternet")
	private String siteInternet;
	
	@Column(name="capacite")
	private int capacite;
	
	@OneToMany(mappedBy = "lieuAcceuil", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rencontre> rencontres;

	public int getIdlieuAcceuil() {
		return idlieuAcceuil;
	}

	public void setIdlieuAcceuil(int idlieuAcceuil) {
		this.idlieuAcceuil = idlieuAcceuil;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getCourriel() {
		return courriel;
	}

	public void setCourriel(String courriel) {
		this.courriel = courriel;
	}

	public String getInfosPMR() {
		return infosPMR;
	}

	public void setInfosPMR(String infosPMR) {
		this.infosPMR = infosPMR;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSiteInternet() {
		return siteInternet;
	}

	public void setSiteInternet(String siteInternet) {
		this.siteInternet = siteInternet;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public Set<Rencontre> getRencontres() {
		return rencontres;
	}

	public void setRencontres(Set<Rencontre> rencontres) {
		this.rencontres = rencontres;
	}

//	@Override
//	public String toString() {
//		return "LieuAcceuil [idlieuAcceuil=" + idlieuAcceuil + ", type=" + type + ", nom=" + nom + ", adresse="
//				+ adresse + ", courriel=" + courriel + ", infosPMR=" + infosPMR + ", commentaires=" + commentaires
//				+ ", telephone=" + telephone + ", siteInternet=" + siteInternet + ", capacite=" + capacite
//				+ ", rencontres=" + rencontres + "]";
//	}
//	

}
