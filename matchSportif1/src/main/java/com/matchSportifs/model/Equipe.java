package com.matchSportifs.model;


import java.time.LocalDate;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "equipe", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "idEquipe")
})

public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEquipe")
    private int idEquipe; 
    

    @Column(name = "nom")
    private String nom;

    @Column(name = "anneeNaissance")
    private LocalDate anneeNaissance;

    @OneToMany(mappedBy = "equipeActuelle", fetch = FetchType.LAZY, 
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
 private Set<Joueur> joueursActuels;

    @OneToMany(mappedBy = "equipe", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EquipeRencontre> equipeRencontres;

    @OneToMany(mappedBy = "equipe", fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Entraineur> entraineurs;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "equipe", cascade = CascadeType.ALL)
    private ClassementEquipes classement;



	public int getIdEquipe() {
		return idEquipe;

	}

	public void setIdEquipe(int idEquipe) {
		this.idEquipe = idEquipe;

	}

	public String getNom() {
		return nom;

	}

	public void setNom(String nom) {
		this.nom = nom;

	}

	public LocalDate getAnneeNaissance() {

		return anneeNaissance;
	}
	public void setAnneeNaissance(LocalDate anneeNaissance) {
		this.anneeNaissance = anneeNaissance;
	}
	public Set<Joueur> getJoueursActuels() {
		return joueursActuels;

	}
	public void setJoueursActuels(Set<Joueur> joueursActuels) {
		this.joueursActuels = joueursActuels;
	}

	public Set<EquipeRencontre> getEquipeRencontres() {
		return equipeRencontres;
	}
	public void setEquipeRencontres(Set<EquipeRencontre> equipeRencontres) {
		this.equipeRencontres = equipeRencontres;
	}
	public Set<Entraineur> getEntraineurs() {
		return entraineurs;
	}
	public void setEntraineurs(Set<Entraineur> entraineurs) {
		this.entraineurs = entraineurs;
	}

	public ClassementEquipes getClassement() {
		return classement;
	}
	public void setClassement(ClassementEquipes classement) {
		this.classement = classement;
	}




    

    

}