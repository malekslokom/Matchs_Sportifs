package com.matchSportifs.model;

import javax.persistence.*;
@Entity
@Table(name = "rencontrearbitre")
public class RencontreArbitre {
    
    
    
    @EmbeddedId
    private RencontreArbitreId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idArbitre") 
    @JoinColumn(name = "idArbitre")
    private Arbitre arbitre;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idRencontre")
    @JoinColumn(name = "idRencontre")
    private Rencontre rencontre;

	public RencontreArbitre(RencontreArbitreId id, Arbitre arbitre, Rencontre rencontre) {
		super();
		this.id = id;
		this.arbitre = arbitre;
		this.rencontre = rencontre;
	}

	public RencontreArbitre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RencontreArbitreId getId() {
		return id;
	}

	public void setId(RencontreArbitreId id) {
		this.id = id;
	}

	public Arbitre getArbitre() {
		return arbitre;
	}

	public void setArbitre(Arbitre arbitre) {
		this.arbitre = arbitre;
	}

	public Rencontre getRencontre() {
		return rencontre;
	}

	public void setRencontre(Rencontre rencontre) {
		this.rencontre = rencontre;
	}


	
    
    
}
