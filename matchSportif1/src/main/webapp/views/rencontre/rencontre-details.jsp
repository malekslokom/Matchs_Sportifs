<%@ include file="../shared/header.jspf"%>
<%@ include file="../shared/navigation.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- <div class="container mt-5">
    <h1 class="mb-5 text-center">Détails de la Rencontre:</h1>
    <tr>
    								<c:forEach var="equipeRencontre" items="${rencontre.equipeRencontres}">
									    <td><c:out value="${equipeRencontre.equipe.nom}" /></td>
									</c:forEach>
								</tr>
     <div class="row mb-4">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h3 class="mb-0">Informations de la rencontre</h3>
                </div>
                <div class="card-body">
                    <p class="card-text fs-4">Date de début: <c:out value="${rencontre.dateDebut}" /></p>
					<p class="card-text fs-4">Heure de début: <c:out value="${rencontre.heureDebut}" /></p>
					<p class="card-text fs-4">Date de fin: <c:out value="${rencontre.dateFin}" /></p>
					<p class="card-text fs-4">Heure de fin: <c:out value="${rencontre.heureFin}" /></p>

                    <p class="card-text fs-4">Lieu d'accueil: <c:out value="${rencontre.lieuAcceuil.nom}" /></p>
                    <p class="card-text fs-4">URL de la billetterie: <c:out value="${rencontre.urlBilletrie}" /></p>
                </div>
            </div>
        </div>
    </div> --%>
    <div class="container mt-5">
    <h2 class="mb-5 ">Détails de la Rencontre:</h2>

    <div class="row mb-4">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3>Liste des équipes participant à la rencontre :</h3>
                </div>
                <div class="panel-body">
                    <table class="table table-striped">
                        <tbody>
                            <tr>
                                <c:forEach var="equipeRencontre" items="${rencontre.equipeRencontres}">
                                    <%-- <td><c:out value="${equipeRencontre.equipe.nom}" /></td> --%>
                                     <c:choose>
				                        <c:when test="${equipeRencontre.domicile}">
				                            <td><strong style="color: #990000;">Domicile:</strong><strong> <c:out value="${equipeRencontre.equipe.nom}" /></strong></td>
				                        </c:when>
				                        <c:otherwise>
				                            <td><strong style="color: #990000;">Exterieur: </strong><strong><c:out value="${equipeRencontre.equipe.nom}" /></strong></td>
				                        </c:otherwise>
                    				</c:choose>
                                </c:forEach>
                            </tr>
                        </tbody>
                    </table>
                </div>
                   <div class="row mb-4">
				    <div class="col-lg-12">
				        <div class="card">
				            <div class="card-header bg-dark text-white">
				                <h3 class="mb-0">Informations de la rencontre</h3>
				            </div>
				            <div class="card-body">
				                <table class="table">
				                    <tbody>
				                        <tr>
				                            <th scope="row">Date de début</th>
				                            <td><c:out value="${rencontre.dateDebut}" /></td>
				                            <th scope="row">Heure de début</th>
				                            <td><c:out value="${rencontre.heureDebut}" /></td>
				                        </tr>
				                        <tr>
				                            <th scope="row">Date de fin</th>
				                            <td><c:out value="${rencontre.dateFin}" /></td>
				                            <th scope="row">Heure de fin</th>
				                            <td><c:out value="${rencontre.heureFin}" /></td>
				                        </tr>
				                        <tr>
				                            <th scope="row">Lieu d'accueil</th>
				                            <td><c:out value="${rencontre.lieuAcceuil.nom}" /></td>
				                            <th scope="row">URL de la billetterie</th>
				                            <td><c:out value="${rencontre.urlBilletrie}" /></td>
				                        </tr>
				                    </tbody>
				                </table>
				            </div>
				        </div>
				    </div>
				</div>
            </div>
        </div>
    </div>



<%--     <div class="row mb-4">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header bg-success text-white">
                    <h3 class="mb-0">Liste des arbitres</h3>
                </div>
                <div class="card-body">
                    <ul class="list-group fs-4">
                        <c:forEach var="arbitre" items="${rencontre.arbitres}">
                            <li class="list-group-item"><c:out value="${arbitre.nom}" /></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div> --%>
    
    
      <div class="container">
     
        <br>
        <div class="panel panel panel-default">
            <div class="panel-heading">
                <h3>Liste des arbitres</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th width="20%">ID</th>
					       <th width="20%">Nom</th>
					       <th width="20%">Prénom</th>
					       <th width="20%">Nationalité</th>
					       <th width="20%">Date De Naissance</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach  var="arbitre" items="${rencontre.arbitres}">
                            <tr>
                                <td><c:out value="${arbitre.id}" /></td>
			                    <td><c:out value="${arbitre.nom}" /></td>
			                    <td><c:out value="${arbitre.prenom}" /></td>
			                    <td><c:out value="${arbitre.nationalite}" /></td>
			                    <td><c:out value="${arbitre.dateNaissance}" /></td>
                                
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
    
     <%-- <jsp:include page="/views/rencontre/temps/temps-list.jspf">
        <jsp:param name="idRencontre" value="${rencontre.idRencontre}" />
    </jsp:include>
 --%>
 <c:if test="${showParticipants}">
 <c:if test="${showlistTemps}">
    <div class="container">
     <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
    
        <div class="text-right">
        
            <a type="button" class="btn btn-primary btn-md" href="/matchSportif/api/rencontres/${rencontre.idRencontre}/details/tempsdejeux/new">Ajouter Temps De Jeux</a>
        </div>
        </c:if>
        <br>
        <div class="panel panel panel-default">
            <div class="panel-heading">
                <h3>Liste des Temps De Jeux</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th width="20%">ID</th>
                            <th width="30%">Heure Début</th>
                            <th width="30%">Heure Fin</th>
                            <th width="30%">Numéro</th>                        
                            <th width="20%">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tempsDeJeux" items="${tempsDeJeux}">
                            <tr>
                                <td><c:out value="${tempsDeJeux.idtempsDeJeux}" /></td> 
                                <td><c:out value="${tempsDeJeux.heureDebut}" /></td>
                                <td><c:out value="${tempsDeJeux.heureFin}" /></td>
                                <td><c:out value="${tempsDeJeux.numero}" /></td>
                                <td>
                                 <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                                
                                    <div style="white-space: nowrap;">
                                        <a type="button" class="btn btn-success" href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/tempsdejeux/edit'/>?id=${tempsDeJeux.idtempsDeJeux}">Modifier</a>
                                        <a type="button" class="btn btn-warning" href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/tempsdejeux/delete'/>?id=${tempsDeJeux.idtempsDeJeux}">Supprimer</a>
                                    </div>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</c:if>

 <c:if test="${!showlistTemps}">
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel panel-default">
                    <c:if test="${tempsDeJeux != null}">
                        <div class="panel-heading">Modifier Temps De Jeux</div>
                    </c:if>
                    <c:if test="${tempsDeJeux == null}">
                        <div class="panel-heading">Ajouter Temps De Jeux</div>
                    </c:if>

                    <div class="panel-body">
                        <c:if test="${tempsDeJeux != null}">
                            <form action="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/tempsdejeux/update'/>" method="post">
                                <input type="hidden" name="idtempsDeJeux" value="${tempsDeJeux.idtempsDeJeux}" />
                        </c:if>
                        <c:if test="${tempsDeJeux == null}">
                            <form action="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/tempsdejeux/insert'/>" method="post">
                        </c:if>

                        <div class="form-group row">
                            <label for="heureDebut" class="col-sm-2 col-form-label">Heure Début</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="heureDebut" name="heureDebut" placeholder="HH:mm" value="<c:out value='${tempsDeJeux.heureDebut}' />" />
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="heureFin" class="col-sm-2 col-form-label">Heure Fin</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="heureFin" name="heureFin" placeholder="HH:mm" value="<c:out value='${tempsDeJeux.heureFin}' />" />
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="numero" class="col-sm-2 col-form-label">Numéro</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="numero" name="numero" placeholder="Numero" value="<c:out value='${tempsDeJeux.numero}' />" />
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-10 col-sm-offset-2 text-right">
                                <button type="submit" class="btn btn-primary btn-md mr-2">Enregistrer</button>
                                <button type="reset" class="btn btn-secondary btn-md">Annuler</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</c:if>

</c:if>
 <c:if test="${showParticipants}">
  <%-- <div class="container">
    <c:forEach var="equipeRencontre" items="${rencontre.equipeRencontres}">
        <div class="text-right">
            <a type="button" class="btn btn-primary btn-md" href="/matchSportif/api/rencontres/${rencontre.idRencontre}/details/equipe/${equipeRencontre.equipe.idEquipe}/participants/new">Ajouter Participant</a>
        </div>
        <br>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>Liste des Participants pour ${equipeRencontre.equipe.nom}</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID Joueur</th>
                          <!--  <th>Nom du Joueur</th> -->
                            <th>Heure d'Entrée</th>
                            <th>Heure de Sortie</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="participant" items="${equipeRencontre.rencontre.participants}">
                            <tr>
                                <td><c:out value="${participant.joueur.id}" /></td> 
                               <td><c:out value="${participant.joueur.nom}" /></td> 
                                <td><c:out value="${participant.heureEntree}" /></td>
                                <td><c:out value="${participant.heureSortie}" /></td>
                                <td>
                                    <div style="white-space: nowrap;">
                                        <a type="button" class="btn btn-success" href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/equipe/${equipeRencontre.equipe.idEquipe}/participants/edit'/>?id=${participant.id}">Modifier</a>
                                        <a type="button" class="btn btn-warning" href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/equipe/${equipeRencontre.equipe.idEquipe}/participants/delete'/>?id=${participant.id}">Supprimer</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:forEach> --%>
    
    <div class="container">
    <c:forEach var="equipeRencontre" items="${rencontre.equipeRencontres}">
     <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
    
        <div class="text-right">
            <a type="button" class="btn btn-primary btn-md" href="/matchSportif/api/rencontres/${rencontre.idRencontre}/details/equipe/${equipeRencontre.equipe.idEquipe}/participants/new">Ajouter Participant</a>
        </div>
        </c:if>
        <br>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>Liste des Participants pour ${equipeRencontre.equipe.nom}</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th width="20%">ID Joueur</th>
                            <th width="40%">Heure d'Entrée</th>
                            <th width="40%">Heure de Sortie</th>
                            <th width="20%">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="participant" items="${rencontre.participants}">
                            <c:choose>
                                <c:when test="${participant.joueur.equipeActuelle ne null and participant.joueur.equipeActuelle.idEquipe eq equipeRencontre.equipe.idEquipe}">
                                    <tr>
                                       <td><%-- <c:out value="${participant.joueur.id}" /> --%>
                                        <a href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/equipe/${participant.joueur.equipeActuelle.idEquipe}/participants/${participant.joueur.id}/details'/>">
		                                    <c:out value="${participant.joueur.id}" />
		                                </a>
		                                </td> 
                                        <td><c:out value="${participant.heureEntree}" /></td>
                                        <td><c:out value="${participant.heureSortie}" /></td>
                                        <td>
                                         <c:if  test="${sessionScope.user != null && 'ADMIN'.equals(sessionScope.role)}">
                                        
                                            <div style="white-space: nowrap;">
                                                <a type="button" class="btn btn-success" href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/equipe/${equipeRencontre.equipe.idEquipe}/participants/edit'/>?idJoueur=${participant.joueur.id}">Modifier</a>
                                                <a type="button" class="btn btn-warning" href="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/equipe/${equipeRencontre.equipe.idEquipe}/participants/delete'/>?idJoueur=${participant.joueur.id}">Supprimer</a>
                                            </div>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:when>
                               
                            </c:choose>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:forEach>
</div>

    
</div>  

</c:if>
 <c:if test="${!showParticipants}">
    
    <div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel panel-default">
                <c:if test="${participant != null}">
                    <div class="panel-heading">Modifier Participant</div>
                </c:if>
                <c:if test="${participant == null}">
                    <div class="panel-heading">Ajouter Participant</div>
                </c:if>

                <div class="panel-body">
                    <c:if test="${participant != null}">
                        <form action="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/equipe/${idEquipe}/participants/${idJoueur}/update'/>" method="post">
                           <%--  <input type="hidden" name="idRencontre" value="${participant.id.rencontre.idRencontre}" />
                            <input type="hidden" name="idJoueur" value="${participant.id.joueur.idJoueur}" /> --%>
                    </c:if>
                    <c:if test="${participant == null}">
                        <form action="<c:url value='/api/rencontres/${rencontre.idRencontre}/details/equipe/${idEquipe}/participants/insert'/>" method="post">
                    </c:if>
                    <div class="form-group row">
					    <label for="selectedPlayer" class="col-sm-2 col-form-label">Select Player</label>
					    <div class="col-sm-10">
					        <select class="form-control" id="selectedPlayer" name="selectedPlayer">
					            <c:forEach var="player" items="${teamPlayers}">
					                <option value="${player.id}">
					                    ${player.nom} 
					                </option>
					            </c:forEach>
					        </select>
					    </div>
					</div>
                    <div class="form-group row">
                        <label for="heureEntree" class="col-sm-2 col-form-label">Heure d'entrée</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="heureEntree" name="heureEntree" placeholder="Heure d'entrée" value="<c:out value='${participant.heureEntree}' />" />
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="heureSortie" class="col-sm-2 col-form-label">Heure de sortie</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="heureSortie" name="heureSortie" placeholder="Heure de sortie" value="<c:out value='${participant.heureSortie}' />" />
                        </div>
                    </div>

                    <!-- Add more fields as needed -->

                    <div class="form-group row">
                        <div class="col-sm-10 col-sm-offset-2 text-right">
                            <button type="submit" class="btn btn-primary btn-md mr-2">Enregistrer</button>
                            <button type="reset" class="btn btn-secondary btn-md">Annuler</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</div>

    </c:if> 
</div>

<%@ include file="../shared/footer.jspf"%>
