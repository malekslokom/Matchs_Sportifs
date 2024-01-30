package com.matchSportifs.model;


import javax.persistence.*;


@Entity
@Table(name = "classementequipes", uniqueConstraints = {

    @UniqueConstraint(columnNames = "idClassementEquipes")

})

public class ClassementEquipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClassementEquipes;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEquipe", referencedColumnName = "idEquipe")
    private Equipe equipe;
	
    @Column(name = "points")
    private int points;

    @Column(name = "pointsDom")
    private int pointsDom;

    @Column(name = "pointsExt")
    private int pointsExt;

    @Column(name = "matchesPlayed")
    private int matchesPlayed;

	public Long getId() {
		return idClassementEquipes;

	}

	public void setId(Long id) {
		this.idClassementEquipes = id;

	}

	public Equipe getEquipe() {
        return equipe;
    }

	public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

	public int getPoints() {
		return points;

	}


	public void setPoints(int points) {

		this.points = points;

	}


	public int getPointsDom() {

		return pointsDom;

	}


	public void setPointsDom(int pointsDom) {

		this.pointsDom = pointsDom;

	}


	public int getPointsExt() {

		return pointsExt;

	}


	public void setPointsExt(int pointsExt) {

		this.pointsExt = pointsExt;

	}


	public int getMatchesPlayed() {

		return matchesPlayed;

	}


	public void setMatchesPlayed(int matchesPlayed) {

		this.matchesPlayed = matchesPlayed;

	}


//	@Override
//
//	public String toString() {
//
//		return "ClassementEquipes [id=" + idClassementEquipes + ", equipes=" + equipes + ", points=" + points + ", pointsDom="
//
//				+ pointsDom + ", pointsExt=" + pointsExt + ", matchesPlayed=" + matchesPlayed + "]";
//
//	}



}