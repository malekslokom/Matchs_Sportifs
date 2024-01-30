package com.matchSportifs.model;

import javax.persistence.*;

@Entity
@Table(name = "equiperencontre")
public class EquipeRencontre {	
    @EmbeddedId
    private EquipeRencontreId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idEquipe") 
    @JoinColumn(name = "idEquipe")
    private Equipe equipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idRencontre")
    @JoinColumn(name = "idRencontre")
    private Rencontre rencontre;

    @Column(name = "domicile")
    private boolean domicile;
    
    

	public EquipeRencontre() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EquipeRencontre(EquipeRencontreId id, Equipe equipe, boolean domicile) {
		super();
		this.id = id;
		this.equipe = equipe;
		this.domicile = domicile;
	}

	
	public EquipeRencontre(EquipeRencontreId id, Equipe equipe, Rencontre rencontre, boolean domicile) {
		super();
		this.id = id;
		this.equipe = equipe;
		this.rencontre = rencontre;
		this.domicile = domicile;
	}


	public EquipeRencontreId getId() {
		return id;
	}

	public void setId(EquipeRencontreId id) {
		this.id = id;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Rencontre getRencontre() {
		return rencontre;
	}

	public void setRencontre(Rencontre rencontre) {
		this.rencontre = rencontre;
	}

	public boolean isDomicile() {
		return domicile;
	}

	public void setDomicile(boolean domicile) {
		this.domicile = domicile;
	}
	



//	@Override
//	public String toString() {
//		return "EquipeRencontre [id=" + id + ", equipe=" + equipe + ", rencontre=" + rencontre + ", domicile="
//				+ domicile + "]";
//	}

    
}