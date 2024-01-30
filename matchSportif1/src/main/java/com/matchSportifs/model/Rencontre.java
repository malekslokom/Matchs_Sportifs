package com.matchSportifs.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "rencontre", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "idRencontre")
   
})
public class Rencontre {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idRencontre")
	private int idRencontre;
	
	@ManyToOne
    @JoinColumn(name="idLieuAcceuil")
	private LieuAcceuil lieuAcceuil;
	
	@Column(name="dateDebut")
	private LocalDate dateDebut;
	
	@Column(name="heureDebut")
	private LocalTime heureDebut;
	
	@Column(name="dateFin")
	private LocalDate dateFin;
	
	@Column(name="heureFin")
	private LocalTime heureFin;
	
	@Column(name="urlBilletrie")
	private String urlBilletrie;
	
	@OneToMany(mappedBy = "rencontre", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<EquipeRencontre> equipeRencontres = new HashSet<>();



	@OneToMany(mappedBy = "rencontre", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participant> participants;
    

    
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	    name = "rencontrearbitre",
	    joinColumns = @JoinColumn(name = "idRencontre"),
	    inverseJoinColumns = @JoinColumn(name = "idArbitre")
	)
	private Set<Arbitre> arbitres = new HashSet<>();
	
	
	
	@OneToMany(mappedBy = "rencontre", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Penalite> penalites;
    
	@OneToMany(mappedBy = "rencontre", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<ButRencontre> butRencontres;
    
    
	@OneToMany(mappedBy = "rencontre", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TempsDeJeux> tempsDeJeux;


	public int getIdRencontre() {
		return idRencontre;
	}


	public void setIdRencontre(int idRencontre) {
		this.idRencontre = idRencontre;
	}


	public LieuAcceuil getLieuAcceuil() {
		return lieuAcceuil;
	}


	public void setLieuAcceuil(LieuAcceuil lieuAcceuil) {
		this.lieuAcceuil = lieuAcceuil;
	}


	public LocalDate getDateDebut() {
		return dateDebut;
	}


	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}


	public LocalTime getHeureDebut() {
		return heureDebut;
	}


	public void setHeureDebut(LocalTime heureDebut) {
		this.heureDebut = heureDebut;
	}


	public LocalDate getDateFin() {
		return dateFin;
	}


	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}


	public LocalTime getHeureFin() {
		return heureFin;
	}


	public void setHeureFin(LocalTime heureFin) {
		this.heureFin = heureFin;
	}


	public String getUrlBilletrie() {
		return urlBilletrie;
	}


	public void setUrlBilletrie(String urlBilletrie) {
		this.urlBilletrie = urlBilletrie;
	}


	public Set<EquipeRencontre> getEquipeRencontres() {
		return equipeRencontres;
	}


	public void setEquipeRencontres(Set<EquipeRencontre> equipeRencontres) {
		this.equipeRencontres = equipeRencontres;
	}


	public Set<Participant> getParticipants() {
		return participants;
	}


	public void setParticipants(Set<Participant> participants) {
		this.participants = participants;
	}





	public Set<Arbitre> getArbitres() {
		return arbitres;
	}


	public void setArbitres(Set<Arbitre> arbitres) {
		this.arbitres = arbitres;
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


	public Set<TempsDeJeux> getTempsDeJeux() {
		return tempsDeJeux;
	}


	public void setTempsDeJeux(Set<TempsDeJeux> tempsDeJeux) {
		this.tempsDeJeux = tempsDeJeux;
	}


//	@Override
//	public String toString() {
//		return "Rencontre [idRencontre=" + idRencontre + ", lieuAcceuil=" + lieuAcceuil + ", dateDebut=" + dateDebut
//				+ ", heureDebut=" + heureDebut + ", dateFin=" + dateFin + ", heureFin=" + heureFin + ", urlBilletrie="
//				+ urlBilletrie + ", equipeRencontres=" + equipeRencontres + ", participants=" + participants
//				+ ", arbitre=" + arbitre + ", penalites=" + penalites + ", butRencontres=" + butRencontres
//				+ ", tempsDeJeux=" + tempsDeJeux + "]";
//	}
    
}
