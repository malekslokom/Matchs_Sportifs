package com.matchSportifs.model;


import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "adresse", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "idAdresse")
   
})
public class Adresse {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idAdresse")
	private int idAdresse;
	
	@Column(name="rue")
	private String rue;
	
	@Column(name="codePostal")
	private String codePostal;

	@Column(name="ville")
	private String ville;

    @OneToMany(mappedBy="adresse") 
    private Set<LieuAcceuil> lieuxAcceuil;

	public int getIdAdresse() {
		return idAdresse;
	}

	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Set<LieuAcceuil> getLieuxAcceuil() {
		return lieuxAcceuil;
	}

	public void setLieuxAcceuil(Set<LieuAcceuil> lieuxAcceuil) {
		this.lieuxAcceuil = lieuxAcceuil;
	}

	@Override
	public String toString() {
		return "Adresse [idAdresse=" + idAdresse + ", rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville
				+ ", lieuxAcceuil=" + lieuxAcceuil + "]";
	}
    

}
