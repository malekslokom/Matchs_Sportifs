package com.matchSportifs.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "joueur", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "id")
   
})
public class Joueur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nom")
	private String nom;
	
	@Column(name = "prenom")
	private String prenom;
	
	@Column(name = "dateNaissance")
	private LocalDate dateNaissance;
	
	@Column(name = "nationalite")
	private String nationalite;
	
	@Column(name = "maillot")
	private int maillot;
	
	@Column(name = "post")
	private String post;
	
    @ManyToOne
    @JoinColumn(name="idEquipeActuelle") 
    private Equipe equipeActuelle;
    
    @ManyToOne
    @JoinColumn(name="idEquipePrecedente", nullable = true)
    private Equipe equipePrecedente;
    
    @OneToMany(mappedBy = "joueur", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participant> participants;
    
    @OneToMany(mappedBy = "joueur", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Penalite> penalites;
    
  
    @OneToMany(mappedBy = "joueur", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<ButRencontre> butRencontres;


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


	public int getMaillot() {
		return maillot;
	}


	public void setMaillot(int maillot) {
		this.maillot = maillot;
	}


	public String getPost() {
		return post;
	}


	public void setPost(String post) {
		this.post = post;
	}


	public Equipe getEquipeActuelle() {
		return equipeActuelle;
	}


	public void setEquipeActuelle(Equipe equipeActuelle) {
		this.equipeActuelle = equipeActuelle;
	}


	public Equipe getEquipePrecedente() {
		return equipePrecedente;
	}


	public void setEquipePrecedente(Equipe equipePrecedente) {
		this.equipePrecedente = equipePrecedente;
	}


	public Set<Participant> getParticipants() {
		return participants;
	}


	public void setParticipants(Set<Participant> participants) {
		this.participants = participants;
	}


	public Set<Penalite> getPenalites() {
		return penalites;
	}


	public void setPenalites(Set<Penalite> penalites) {
		this.penalites = penalites;
	}


	public Set<ButRencontre> getButRencontres() {
		return butRencontres;
	}


	public void setButRencontres(Set<ButRencontre> butRencontres) {
		this.butRencontres = butRencontres;
	}
	public Equipe getEquipe() {
            return getEquipeActuelle();
        
    }

//	@Override
//	public String toString() {
//		return "Joueur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance="
//				+ dateNaissance + ", nationalite=" + nationalite + ", maillot=" + maillot + ", post=" + post
//				+ ", equipeActuelle=" + equipeActuelle + ", equipePrecedente=" + equipePrecedente + ", participants="
//				+ participants + ", penalites=" + penalites + ", butRencontres=" + butRencontres + "]";
//	}
    
    
}
